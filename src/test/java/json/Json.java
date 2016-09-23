package json;

import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static com.hengyi.japp.common.Constant.MAPPER;

/**
 * Created by jzb on 16-5-8.
 */
public class Json {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String baseUrl = "http://jzb.tunnel.qydev.com/execution/api/v1";
    private static List<Cookie> cookies;
    private static final OkHttpClient httpClient = new OkHttpClient.Builder().cookieJar(new CookieJar() {
        @Override
        public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
            if (httpUrl.encodedPath().endsWith("login"))
                cookies = list;
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl httpUrl) {
            return cookies == null ? Collections.EMPTY_LIST : cookies;
        }
    }).build();

    public static void login(final String username, final String password) throws IOException {
        ObjectNode node = MAPPER.createObjectNode();
        node.put("principal", username);
        node.put("password", password);
        String json = MAPPER.writeValueAsString(node);
        RequestBody body = RequestBody.create(JSON, json);
        Request req = new Request.Builder().url(baseUrl + "/login").post(body).build();
        httpClient.newCall(req).execute();
    }

    public static void main(String[] args) throws IOException {
        login("13456978427", "123456");
        Request request = new Request.Builder().url(baseUrl + "/test/tasks").build();
        Response res = httpClient.newCall(request).execute();
        System.out.println(res.body().string());
    }
}
