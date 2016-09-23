package ccf;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by jzb on 16-6-30.
 */
public class Fetchccf_selenium {
    private static final String loginUrl = "http://www.ccf.com.cn/member/member.php";
    private static final String dataUrl = "http://www.ccf.com.cn/monitor/index.php";

    private static WebDriver webdriver = new FirefoxDriver();
//    private static WebDriver webdriver = new HtmlUnitDriver();
//    static {
//        System.setProperty("webdriver.chrome.driver", "/usr/bin/google-chrome");
//    }
//
//    private static WebDriver webdriver = new ChromeDriver();

    public static void main(String[] args) throws IOException {
        login();
        data();
        queryData();

        webdriver.findElements(By.tagName("table")).stream()
                .filter(t -> Objects.equals(t.getAttribute("bgcolor"), "#FFFFFF"))
                .findFirst()
                .ifPresent(t -> {
                    String[] ss = t.getText().split("\n");
                    System.out.println(ss);
                    System.out.println("===================================\n");
                });
        webdriver.quit();
    }

    private static void queryData() throws IOException {
        data();
//        WebElement querymonth = webdriver.findElement(By.name("querymonth"));
//        querymonth.sendKeys("06");
//
//        WebElement form = webdriver.findElement(By.name("form1"));
//        form.submit();
    }

    private static void data() throws IOException {
        webdriver.get(dataUrl);
        WebElement a_200000_3 = webdriver.findElement(By.id("a_200000_3"));
        a_200000_3.click();

        Select startyear = new Select(webdriver.findElement(By.id("startyear")));
        startyear.selectByValue("2016");

        Select startmonth = new Select(webdriver.findElement(By.id("startmonth")));
        startmonth.selectByValue("06");

        Select endyear = new Select(webdriver.findElement(By.id("endyear")));
        endyear.selectByValue("2016");

        Select endmonth = new Select(webdriver.findElement(By.id("endmonth")));
        endmonth.selectByValue("06");

        WebElement searchbtn = webdriver.findElement(By.className("searchbtn"));
        searchbtn.click();

//        webdriver.close();

        //切换到最新的页面
        webdriver.switchTo().window(
                webdriver.getWindowHandles().stream()
                        .reduce((a, b) -> b)
                        .get()
        );
    }

    private static void login() throws IOException {
        webdriver.get(loginUrl);
        WebElement username = webdriver.findElement(By.name("username"));
        username.sendKeys("hysh");
        WebElement password = webdriver.findElement(By.name("password"));
        password.sendKeys("9903");
        WebElement form = webdriver.findElement(By.name("LoginForm"));
        form.submit();
    }

}
