package org.foodhub.common.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import org.foodhub.common.exception.JacksonDataConversionException;

import java.io.IOException;

/**
 * <p>
 * Wraps the jackson library and the methods of jackson can be used from this class.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class JsonFactory {

    private static ObjectMapper objectMapper = getObjectMapper();

    private JsonFactory() {
    }

    public JsonElement asJsonObject(byte[] result) {
        return null;
    }

    /**
     * <p>
     * Creates the instance of the class
     * </p>
     */
    private static class InstanceHolder {

        private static final JsonFactory JSON_FACTORY = new JsonFactory();
    }

    /**
     * <p>
     * Gets the instance of the json factory class.
     * </p>
     *
     * @return The json factory instance
     */
    public static JsonFactory getInstance() {
        return InstanceHolder.JSON_FACTORY;
    }

    /**
     * <p>
     * Gets the object mapper object.
     * </p>
     *
     * @return The object of object mapper
     */
    private static ObjectMapper getObjectMapper() {
        if (null == objectMapper) {
            objectMapper = new ObjectMapper();

            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        }

        return objectMapper;
    }

    /**
     * <p>
     * Gets the object and converts into json node.
     * </p>
     *
     * @return The json node
     */
    public JsonNode toJson(final Object object) {
        return objectMapper.valueToTree(object);
    }

    /**
     * <p>
     * converts the json node into byte array.
     * </p>
     *
     * @return The byte array of json node
     */
    public byte[] asBytes(final JsonNode jsonNode) {
        try {
            return objectMapper.writeValueAsBytes(jsonNode);
        } catch (JsonProcessingException exception) {
            throw new JacksonDataConversionException(exception.getMessage());
        }
    }

    /**
     * <p>
     * creates the json object.
     * </p>
     *
     * @return The json object
     */
    public JsonObject createObjectNode() {
        return new JsonObject(objectMapper.createObjectNode());
    }

    /**
     * <p>
     * creates the json array.
     * </p>
     *
     * @return The json array
     */
    public JsonArray createArrayNode() {
        return new JsonArray(objectMapper.createArrayNode());
    }

    /**
     * <p>
     * Gets the jackson json provider.
     * </p>
     *
     * @return Jackson json provider
     */
    public JacksonJsonProvider getJsonProvider() {
        return new JacksonJsonProvider();
    }

    /**
     * <p>
     *  Converts the byte array into array node.
     * </p>
     *
     * @param result Represents the result byte array
     * @return The json array instance
     */
    public JsonElement asJsonArray(final byte[] result) {
        try {
            return new JsonElement(objectMapper.readTree(result));
        } catch (IOException exception) {
            throw new JacksonDataConversionException(exception.getMessage());
        }
    }
}