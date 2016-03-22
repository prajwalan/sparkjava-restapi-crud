package com.crm.api.integrationtest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import spark.utils.IOUtils;

public class BaseIntegrationTest {

    protected TestHttpResponse request(String method, String path, String payload) throws IOException {

        HttpURLConnection connection = null;

        try {

            URL url = new URL("http://localhost:4567" + path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            if (payload != null) {
                OutputStream os = connection.getOutputStream();
                os.write(payload.getBytes("UTF-8"));
                os.flush();
            }
            connection.connect();

            String body = IOUtils.toString(connection.getInputStream());
            TestHttpResponse response = new TestHttpResponse(connection.getResponseCode(), body);

            return response;
        }
        finally {

            if (connection != null) {
                if (connection.getInputStream() != null) {
                    connection.getInputStream().close();
                }
                connection.disconnect();
            }
        }
    }

}
