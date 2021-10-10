package com.egcodes.advertisement.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Util {

    public static <T> T deepClone(T object) throws IOException {
        var mapper = new ObjectMapper();
        return (T) mapper.readValue(mapper.writeValueAsString(object), object.getClass());
    }

}
