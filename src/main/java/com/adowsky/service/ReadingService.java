package com.adowsky.service;

import com.adowsky.model.Reading;
import com.adowsky.service.entities.ReadingEntity;
import com.adowsky.service.exception.ReadingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ReadingService {
    private final ReadingRepository readingRepository;
    private final UserService userService;

    public void addReading(Reading reading) {
        long userId = userService.getUserId(reading.getReaderUsername());
        ReadingEntity readingEntity = readingRepository.getByBookIdAndAndReaderId(reading.getBookId(), userId)
                .orElse(new ReadingEntity(null, reading.getBookId(), userId, reading.getStartDate(), reading.getEndDate()));

        if(readingEntity.getStartDate() == null) {
            throw ReadingException.noStartDate();
        }

        readingRepository.save(readingEntity);

        log.info("Reading status of book={} changed on {} by {}",
                readingEntity.getBookId(), readingEntity.getEndDate() == null, userId);
    }
}
