package org.api.mock.services.multicast;

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

    @Resource
    private MulticastPublisher multicastPublisher;

    private static final Logger LOG = LoggerFactory.getLogger(MulticastService.class);
    private static final Map<String, String> SESSIONS = new HashMap<>();

    public String getValue(String token) {
        LOG.debug("Get value for Token {}", token);
        return SESSIONS.get(token);
    }

    protected void setValueInSession(ExchangeSession exchangeSession) {
        LOG.debug("Add session value: {}", exchangeSession);
        SESSIONS.put(exchangeSession.getToken(), exchangeSession.getValue());
    }

    public void setValue(String token, String valSession) {
        LOG.debug("Send new value on Multicast group");
        this.send(token, valSession);
    }

    public void sendAllOnMulticast() {
        LOG.debug("Send all local session on Multicast Group");
        SESSIONS.entrySet().stream().forEach(entry -> this.send(entry.getKey(), entry.getValue()));
    }

    private void send(String token, String valSession) {
        LOG.debug("Send Token (id: \"{}\" / Value: \"{}\") in multicast group", token, valSession);
        multicastPublisher.multicast(JsonHelper.toJson(new ExchangeSession(valSession, token)));
    }

    public void sendSyncRequest() {
        LOG.info("Send sync request");
        multicastPublisher.multicast(MulticastReceiver.KEY_SYNC);
    }
}
