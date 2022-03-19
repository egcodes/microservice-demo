package com.egcodes.advertisement.service.lock;

import com.egcodes.advertisement.service.lock.impl.DistributedLockProviderImpl.DistLock;

public interface DistributedLockProvider {

    DistLock lock(String key, int lockAtLeastFor, int lockAtMostFor);

}
