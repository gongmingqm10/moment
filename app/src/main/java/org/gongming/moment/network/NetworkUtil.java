package org.gongming.moment.network;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.gongming.moment.api.ApiCallResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class NetworkUtil {

    private static ObjectMapper objectMapper;

    public static synchronized Object call(String urlString, Class<?> clazz) {
        InputStream is = null;
        Object object = null;
        String errorMessage = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
            object = getObjectMapper().readValue(parseStringFromInputStream(is), clazz);
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage = e.getMessage().toString();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return errorMessage == null ? object : errorMessage;
        }
    }

    private static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return objectMapper;
    }

    private static String parseStringFromInputStream(InputStream is) throws IOException {
        byte[] buffer = new byte[1024];
        StringBuilder builder = new StringBuilder();
        int numberRead;
        while ((numberRead = is.read(buffer)) > 0) {
            builder.append(new String(buffer, 0, numberRead));
        }
        return builder.toString();
    }

}
