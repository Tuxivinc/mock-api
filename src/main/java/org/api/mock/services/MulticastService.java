package org.api.mock.services;

import org.api.mock.model.ExchangeSession;
import org.api.mock.services.helper.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Multicast Service
 */
@Service
public class MulticastService {

    private static final Logger LOG = LoggerFactory.getLogger(MulticastService.class);

    private static final Map<String, String> SESSIONS = new HashMap<>();

    @Resource
    private MulticastPublisher multicastPublisher;

    public String getValue(String token) {
        return SESSIONS.get(token);
    }

    public void setValue(ExchangeSession exchangeSession) {
        LOG.info("Add session value : {}", exchangeSession);
        SESSIONS.put(exchangeSession.getToken(), exchangeSession.getValue());
    }

    public void sendValue(String token, String valSession) {
        multicastPublisher.multicast(JsonHelper.toJson(new ExchangeSession(valSession, token)));
    }
}
