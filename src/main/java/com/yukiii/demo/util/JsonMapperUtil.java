package com.yukiii.demo.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

@Component
public class JsonMapperUtil {
    private static final ObjectMapper mapper = new ObjectMapper();
    public JsonMapperUtil () {
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
    }

    public static void write(OutputStream out, Object value) throws IOException {
        mapper.writeValue(out, value);
    }

    public static void write(Writer writer, Object value) throws IOException{
        mapper.writeValue(writer, value);
    }

    public static Object read(InputStream in, Class c) throws IOException{
        return mapper.readValue( in, c);
    }

    public static Object read( String in, Class c) throws IOException{
        return mapper.readValue( in, c);
    }
}
