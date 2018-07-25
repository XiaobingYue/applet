package com.xdja.syncThird.util;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by wzy on 2018/5/24.
 */
public class JsonUtil {

    private static ObjectMapper mapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);


    public static String toJsonStr(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonGenerationException e) {
            log.error(e.getMessage());
        } catch (JsonMappingException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return "";
    }
}
