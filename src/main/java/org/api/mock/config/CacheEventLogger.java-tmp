package org.api.mock.config;

import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.*;
import org.infinispan.notifications.cachelistener.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Listener
public class CacheEventLogger {

    private static final Logger LOG = LoggerFactory.getLogger(CacheEventLogger.class);

    @CacheEntryCreated
    public void entryCreated(CacheEntryCreatedEvent<String, String> event) {
        this.printLog("Adding key '" + event.getKey()
                + "' to cache", event);
    }

    @CacheEntryExpired
    public void entryExpired(CacheEntryExpiredEvent<String, String> event) {
        this.printLog("Expiring key '" + event.getKey()
                + "' from cache", event);
    }

    @CacheEntryVisited
    public void entryVisited(CacheEntryVisitedEvent<String, String> event) {
        this.printLog("Key '" + event.getKey() + "' was visited", event);
    }

    @CacheEntryActivated
    public void entryActivated(CacheEntryActivatedEvent<String, String> event) {
        this.printLog("Activating key '" + event.getKey()
                + "' on cache", event);
    }

    @CacheEntryPassivated
    public void entryPassivated(CacheEntryPassivatedEvent<String, String> event) {
        this.printLog("Passivating key '" + event.getKey()
                + "' from cache", event);
    }

    @CacheEntryLoaded
    public void entryLoaded(CacheEntryLoadedEvent<String, String> event) {
        this.printLog("Loading key '" + event.getKey()
                + "' to cache", event);
    }

    @CacheEntriesEvicted
    public void entriesEvicted(CacheEntriesEvictedEvent<String, String> event) {
        StringBuilder builder = new StringBuilder();
        event.getEntries().forEach(
                (key, value) -> builder.append(key).append(", "));
        LOG.info("Evicting following entries from cache: "
                + builder.toString());
    }

    private void printLog(String log, CacheEntryEvent event) {
        LOG.info("Test Log");
        if (!event.isPre()) {
            LOG.info(log);
        }
    }
}