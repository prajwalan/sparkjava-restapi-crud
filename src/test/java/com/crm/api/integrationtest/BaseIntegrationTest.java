package com.crm.api.integrationtest;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

import org.eclipse.jetty.http.HttpStatus;
import org.junit.BeforeClass;

import com.crm.api.DemoStorage;
import com.crm.api.response.EntityResponse;
import com.crm.model.UserLogin;
import com.crm.util.CommonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import spark.utils.IOUtils;

public class BaseIntegrationTest {

    protected static Gson jsonConverter;
    protected Type stringEntityType = new TypeToken<EntityResponse<String>>() {
    }.getType();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        jsonConverter = CommonUtil.getJsonConvertor();
    }

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

            String body = null;
            if (connection.getResponseCode() == HttpStatus.OK_200 || connection.getResponseCode() == HttpStatus.CREATED_201) {
                body = IOUtils.toString(connection.getInputStream());
            }
            TestHttpResponse response = new TestHttpResponse(connection.getResponseCode(), body);

            return response;
        }
        finally {
            if (connection != null) {
                try {
                    if (connection.getInputStream() != null) {
                        connection.getInputStream().close();
                    }
                }
                catch (Exception ex) {
                    // Ignore
                }
                connection.disconnect();
            }
        }
    }

    protected String login() throws IOException {

        String payload = jsonConverter.toJson(new UserLogin(DemoStorage.username, DemoStorage.password));
        TestHttpResponse response = request("POST", "/login", payload);

        EntityResponse<String> loginResponse = jsonConverter.fromJson(response.body, stringEntityType);
        return loginResponse.getResult();
    }
}
