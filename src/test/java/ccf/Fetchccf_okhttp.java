package ccf;

import okhttp3.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by jzb on 16-6-30.
 */
public class Fetchccf_okhttp {
    private static final String baseUrl = "http://www.ccf.com.cn";
    private static final String loginUrl = baseUrl + "/member/member.php";
    private static final String dataUrl = baseUrl + "/monitor/index.php";

    private static List<Cookie> cookies;
    private static final OkHttpClient httpClient = new OkHttpClient.Builder().cookieJar(new CookieJar() {
        @Override
        public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
            if (Objects.equals(loginUrl, httpUrl.url().toString()))
                cookies = list;
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl httpUrl) {
            return cookies == null ? Collections.emptyList() : cookies;
        }
    }).build();

    public static void main(String[] args) throws IOException {
        login();
        Response res = data();
        System.out.println(res.body().string());
        res = data();
        System.out.println(res.body().string());
    }

    private static Response data() {
        try {
            RequestBody formBody = new FormBody.Builder()
                    .add("Monitor_IDs", "4")
                    /**
                     * 日均检索: day
                     * 月均检索: month
                     * */
                    .add("price_time_type", "day")
                    .add("search_type", "product")
                    .add("CP_ID", "200000")
                    .add("Monitor_ID", "4")
                    .add("startdate", "")
                    .add("enddate", "")
                    .add("queryyear", "2016")
                    .add("querymonth", "07")
                    .build();
//                    .add("Monitor_IDs", "64")
//                    .add("CP_ID", "B00000")
//                    .add("Monitor_ID", "64")
//                    .add("startdate", "")
//                    .add("enddate", "")
//                    .add("queryyear", "2016")
//                    .add("querymonth", "06")
//                    /**
//                     * 日均检索: day
//                     * 月均检索: month
//                     * */
//                    .add("price_time_type", "day")
//
//                    .add("target", "_blank")
//                    .add("search_type", "product")
//                    .build();
            Request req = new Request.Builder().url(dataUrl).post(formBody).build();
            Response res = httpClient.newCall(req).execute();
            if (res.isSuccessful())
                return res;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException();
    }

    private static void login() {
        try {
            RequestBody formBody = new FormBody.Builder()
                    .add("custlogin", "1")
                    .add("url", "/monitor/index.php")
                    .add("s", "")
                    .add("action", "login")
                    .add("username", "hysh")
                    .add("password", "9903")
                    .build();
            Request req = new Request.Builder().url(loginUrl).post(formBody).build();
            Response res = httpClient.newCall(req).execute();
            if (!res.isSuccessful())
                throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
