package org.api.mock.services;

import org.api.mock.model.SessionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Get session in cache
 */
@Service
public class CacheSession {

    @Resource
    private CacheService cacheService;

    private static final Logger LOG = LoggerFactory.getLogger(CacheSession.class);

    public SessionResponse getSession(String token) {
        LOG.debug("Get session (with cache service) for token {}", token);
        String defaultValue = SimulLocalSession.getValSession();
        String value = cacheService.getValue(token, defaultValue);
        return new SessionResponse(defaultValue.equals(value) ? Boolean.FALSE : Boolean.TRUE, value);
    }

}
