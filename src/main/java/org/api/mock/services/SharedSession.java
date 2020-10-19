package org.api.mock.services;

import org.api.mock.model.SessionResponse;
import org.api.mock.services.multicast.MulticastService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * Get session in cache
 */
@Service
public class SharedSession {

    @Resource
    private MulticastService multicastService;

    private static final Logger LOG = LoggerFactory.getLogger(SharedSession.class);

    public SessionResponse getSession(String token) {
        LOG.debug("Get session (with multicast service) for token {}", token);
        return Optional.ofNullable(multicastService.getValue(token))
                .map(v -> new SessionResponse(Boolean.TRUE, v))
                .orElseGet(() -> {
                    String valSession = SimulLocalSession.getValSession();
                    multicastService.setValue(token, valSession);
                    return new SessionResponse(Boolean.FALSE, valSession);
                });
    }

}
