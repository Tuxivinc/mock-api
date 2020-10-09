package org.api.mock.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Cache
 */
@Service
@CacheConfig(cacheNames = "keyValueCache")
public class CacheService {

    private static final Logger LOG = LoggerFactory.getLogger(CacheService.class);

    @Cacheable(key = "#key", condition = "#key != null ")
    public String getValue(String key, String valueSetIfEmpty) {
        LOG.debug("get value for key \"{}\", insert \"{}\" if not present", key, valueSetIfEmpty);
        return valueSetIfEmpty;
    }

}
