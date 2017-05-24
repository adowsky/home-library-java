package com.adowsky.service;

import com.adowsky.model.Book;
import com.adowsky.model.Reading;
import com.adowsky.service.entities.LibraryEntity;
import com.adowsky.service.entities.ReadingEntity;
import com.adowsky.service.entities.UserEntity;
import com.adowsky.service.exception.ReadingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ReadingService {
    private final ReadingRepository readingRepository;
    private final LibraryRepository libraryRepository;

    public void addReading(Reading reading, long authorId) {
        Timestamp startDate = (reading.getStartDate() == null) ? null : new Timestamp(reading.getStartDate().getTime());
        Timestamp endDate = (reading.getEndDate() == null) ? null : new Timestamp(reading.getEndDate().getTime());
        ReadingEntity readingEntity = readingRepository.getByBookIdAndAndReaderIdAndEndDateIsNull(reading.getBookId(), authorId)
                .orElse(new ReadingEntity(null, new LibraryEntity(reading.getBookId()), new UserEntity(authorId),
                        startDate, endDate));

        if (readingEntity.getStartDate() == null) {
            throw ReadingException.noStartDate();
        }

        readingEntity.setEndDate(endDate);
        readingRepository.save(readingEntity);

        log.info("Reading status of book={} changed on {} by {}",
                readingEntity.getBook(), readingEntity.getEndDate() == null, authorId);
    }

    public List<Book> getUsersReadings(long userId) {
        List<ReadingEntity> readings = readingRepository.getByReaderId(userId);
        return readings.stream().filter(reading -> reading.getEndDate() == null)
                .map(reading -> new Book(reading.getBook().getId().toString(),
                        reading.getBook().getTitle(), reading.getBook().getAuthor()))
                .collect(Collectors.toList());
    }

    Book getLastBookForUser(long userId) {
        return readingRepository.getFirstByReaderIdAndEndDateNotNullOrderByEndDateDesc(userId)
                .map(reading -> libraryRepository.findOne(reading.getBook().getId()))
                .map(book -> new Book(book.getId().toString(), book.getTitle(), book.getAuthor()))
                .orElse(null);
    }

    double getAverageBooksReadByUser(long userId) {
        List<ReadingEntity> readings = readingRepository.getAllByReaderIdAndEndDateNotNullOrderByEndDate(userId);
        double allBooks = readings.stream()
                .collect(Collectors.groupingBy(entity -> toYearMonth(entity.getEndDate()))).entrySet().stream()
                .mapToInt(entry -> entry.getValue().size())
                .sum();

        int months = monthsBetween(readings).orElse(1);
        return new BigDecimal(allBooks / months).setScale(2, BigDecimal.ROUND_UP).doubleValue();
    }

    private static OptionalInt monthsBetween(List<ReadingEntity> readings) {
        if (readings.size() == 0) {
            return OptionalInt.empty();
        } else if (readings.size() == 1) {
            ReadingEntity entity = readings.get(0);
            int months = (int) entity.getStartDate().toLocalDateTime()
                    .until(entity.getEndDate().toLocalDateTime(), ChronoUnit.MONTHS) + 1;
            return OptionalInt.of(months);
        }

        Timestamp beginnigDate = readings.get(0).getReader().getCreationDate();
        Timestamp endingDate = readings.stream().map(ReadingEntity::getEndDate).max(Timestamp::compareTo).get();
        long months = toYearMonth(beginnigDate).until(toYearMonth(endingDate), ChronoUnit.MONTHS) + 1;
        return OptionalInt.of((int) months);


    }

    private static YearMonth toYearMonth(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return YearMonth.from(localDate);
    }
}
