package ccf;

import com.beust.jcommander.internal.Lists;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by jzb on 16-6-30.
 */
public class Fetchccf_http {
    private static final String baseUrl = "http://www.ccf.com.cn";
    private static final String loginUrl = baseUrl + "/member/member.php";
    private static final String dataUrl = baseUrl + "/monitor/index.php";
    private static DefaultHttpClient httpclient = new DefaultHttpClient();

    public static void main(String[] args) throws IOException {
        login();
        HttpResponse res = data();
        System.out.println("Login form get: " + res.getStatusLine());
        HttpEntity entity = res.getEntity();
        if (entity != null) {
            entity.writeTo(System.out);
        }
    }

    private static HttpResponse data() throws IOException {
        HttpPost httpost = new HttpPost(dataUrl);
        List<NameValuePair> nvps = Lists.newArrayList();

        nvps.add(new BasicNameValuePair("Monitor_IDs", "64"));
        nvps.add(new BasicNameValuePair("CP_ID", "B00000"));
        nvps.add(new BasicNameValuePair("Monitor_ID", "64"));
        nvps.add(new BasicNameValuePair("startdate", ""));
        nvps.add(new BasicNameValuePair("enddate", ""));
        nvps.add(new BasicNameValuePair("queryyear", "2016"));
        nvps.add(new BasicNameValuePair("querymonth", "06"));
        /**
         * 日均检索: day
         * 月均检索: month
         * */
        nvps.add(new BasicNameValuePair("price_time_type", "day"));
        nvps.add(new BasicNameValuePair("target", "_blank"));
        nvps.add(new BasicNameValuePair("search_type", "product"));
        httpost.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
        HttpResponse res = httpclient.execute(httpost);
        return res;
    }

    private static void login() throws IOException {
        HttpPost httpost = new HttpPost(loginUrl);
        List<NameValuePair> nvps = Lists.newArrayList();
        nvps.add(new BasicNameValuePair("custlogin", "1"));
        nvps.add(new BasicNameValuePair("url", "/monitor/index.php"));
        nvps.add(new BasicNameValuePair("url", "/monitor/index.php"));
        nvps.add(new BasicNameValuePair("s", ""));
        nvps.add(new BasicNameValuePair("action", "login"));
        nvps.add(new BasicNameValuePair("username", "hysh"));
        nvps.add(new BasicNameValuePair("password", "9903"));
        httpost.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
        HttpResponse res = httpclient.execute(httpost);
        HttpEntity entity = res.getEntity();
        System.out.println("Login form get: " + res.getStatusLine());
        if (entity != null) {
            entity.writeTo(System.out);
        }
    }

}
