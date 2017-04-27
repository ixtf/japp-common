package image;

import com.google.common.collect.Lists;
import com.google.zxing.WriterException;
import com.hengyi.japp.common.J;
import com.hengyi.japp.common.J_image;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by jzb on 16-5-9.
 */
public class ImageTest {
    private final static Pattern imagePattern = Pattern.compile("(jpg|jpeg|gif|png|bmp)$", Pattern.CASE_INSENSITIVE);

    @Test
    public void test() {
        //是图片
        Assert.assertTrue(J.isImage("adb.jpg"));
        Assert.assertTrue(J.isImage("abc.jpG"));
        Assert.assertTrue(J.isImage("a    bc.jpG"));
        Assert.assertTrue(J.isImage("a$*_-#@sd._.jpG"));
        //不是图片
        Assert.assertFalse(J.isImage("a$*_-#@sd. .jpG"));
        Assert.assertFalse(J.isImage("a$*_-   #@sd..jpG"));
        Assert.assertFalse(J.isImage("sdfasfjpg"));
    }

    @Test
    public void test1() {
        System.out.println(imagePattern.matcher("GIF").find());
        System.out.println(imagePattern.matcher("sdfs.gif").find());

        List<String> list = Lists.newArrayList("sadfas.jpg", "dfsfs.gif");
        String s = list.stream()
                .filter(name -> J.isImage(name))
                .map(name -> new StringBuilder("<a href=\"")
                        .append("http://www.baidu.com")
                        .append("\">").append(name).append("</a>"))
                .collect(Collectors.joining("\n"));
        System.out.println(s);
        System.out.println(StringUtils.isBlank(s));
        list = Lists.newArrayList("sadfas.txt", "dfsfs.doc");
        s = list.stream()
                .filter(name -> J.isImage(name))
                .map(name -> new StringBuilder("<a href=\"")
                        .append("http://www.baidu.com")
                        .append("\">").append(name).append("</a>"))
                .collect(Collectors.joining("\n"));
        System.out.println(s);
        System.out.println(StringUtils.isBlank(s));

    }

    @Test
    public void test3() throws WriterException, IOException {
        System.out.println(J.qrcode("fdsadfasfasdf"));
        ImageIO.write(J.qrcode("test"), "png", new File("/home/jzb/test.png"));
    }

    @Test
    public void test4() throws WriterException, IOException {
        File file = FileUtils.getFile("/home/jzb/图片", "37ad578cb2d84b5383037afc8bccda26.jpg");
        ImageIO.write(J_image.resize(file, 50, 1), "png", new File("/home/jzb/test.png"));
    }

}
