package org.api.mock.services.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class JsonHelper {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Logger LOG = LoggerFactory.getLogger(JsonHelper.class);

    private JsonHelper() {
        // Helper don't have constructor
    }

    public static <T extends Serializable> T getValue(T objet, @NotNull String value) {
        try {
            return MAPPER.readerForUpdating(objet).readValue(value);
        } catch (JsonProcessingException e) {
            LOG.error("Error when trying to convert to {} this value: {}", objet.getClass().getSimpleName(), value, e);
            return objet;
        }
    }

    public static <T extends Serializable> String toJson(@NotNull T object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOG.error("Error when trying to read object class {} : {}", object.getClass().getSimpleName(), object);
            return object.toString();
        }
    }

}
