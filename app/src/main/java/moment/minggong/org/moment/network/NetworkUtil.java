package moment.minggong.org.moment.network;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class NetworkUtil {

    private static ObjectMapper objectMapper;

    public static Object call(String urlString, Class<?> clazz) throws IOException {
        InputStream is = null;
        Object object = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
            object = getObjectMapper().readValue(parseStringFromInputStream(is), clazz);
        } finally {
            if (is != null) is.close();
            return object;
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
