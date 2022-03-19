package com.egcodes.advertisement.job;

import com.egcodes.advertisement.service.lock.DistributedLockProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class SchedulerJob {

    private final DistributedLockProvider distributedLockProvider;

    @Scheduled(fixedDelayString = "3000")
    public void checkModerationResultsForUtopia() {
        try (var distLock = distributedLockProvider.lock("some-job", 1000, 10000)) {
            if (distLock.isEmpty())
                return;

        } catch (Exception e) {
        }

    }

}