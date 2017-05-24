package com.adowsky.scheduling;

import com.adowsky.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ScheduledTasks {
    private static final long MS_IN_SECOND = 1000;
    private static final long MS_IN_HOUR = MS_IN_SECOND * 3600;

    private final UserService userService;

    @Scheduled(fixedDelay = 24 * MS_IN_HOUR)
    public void removeNotConfirmedUsers() {
        userService.removeOutdatedNotConfirmedUsers();
    }
}
