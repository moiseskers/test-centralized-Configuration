package com.gingapay.splitcoreapplicationapi.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerHelper {

    public static String json(Object o) {
        String jsonInString = "";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.findAndRegisterModules();

        try {
            jsonInString = mapper.writeValueAsString(o);
        } catch (Exception e) {
            log.info("Failed to parse log to json {1}, trying to return the toString() method instead that might be null", e);
            return o.toString();
        }
        return jsonInString;
    }
}
