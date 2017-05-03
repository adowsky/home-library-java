package com.adowsky.service;

import com.adowsky.model.Reading;
import com.adowsky.service.entities.ReadingEntity;
import com.adowsky.service.exception.ReadingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ReadingService {
    private final ReadingRepository readingRepository;

    public void addReading(Reading reading, long authorId) {
        ReadingEntity readingEntity = readingRepository.getByBookIdAndAndReaderId(reading.getBookId(), authorId)
                .orElse(new ReadingEntity(null, reading.getBookId(), authorId, reading.getStartDate(), reading.getEndDate()));

        if(readingEntity.getStartDate() == null) {
            throw ReadingException.noStartDate();
        }

        readingRepository.save(readingEntity);

        log.info("Reading status of book={} changed on {} by {}",
                readingEntity.getBookId(), readingEntity.getEndDate() == null, authorId);
    }

    public List<String> getUsersReadings(long userId) {
        List<ReadingEntity> readings = readingRepository.getByReaderId(userId);
        return readings.stream().filter(reading -> reading.getEndDate() == null)
                .map(reading -> String.valueOf(reading.getBookId())).collect(Collectors.toList());
    }
}
