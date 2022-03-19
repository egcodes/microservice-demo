package com.egcodes.advertisement.service.lock.impl;

import com.egcodes.advertisement.service.lock.DistributedLockProvider;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.core.SimpleLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class DistributedLockProviderImpl implements DistributedLockProvider {

    private final LockProvider lockProvider;

    @Override
    public DistLock lock(String key, int lockAtLeastFor, int lockAtMostFor) {
        var lockConfiguration = new LockConfiguration(Instant.now(), key,
                Duration.ofMillis(lockAtMostFor),
                Duration.ofMillis(lockAtLeastFor)
        );

        return new DistLock(lockProvider.lock(lockConfiguration));
    }

    public class DistLock implements AutoCloseable {

        private Optional<SimpleLock> lock;

        public DistLock(Optional<SimpleLock> lock) {
            this.lock = lock;
        }

        public boolean isPresent() {
            return lock.isPresent();
        }

        public boolean isEmpty() {
            return lock.isEmpty();
        }

        @Override
        public void close() {
            lock.ifPresent(SimpleLock::unlock);
        }

    }
}
