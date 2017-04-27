package poi;

import com.hengyi.japp.common.J;
import com.hengyi.japp.common.toHtml.ToHtmlType;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.converter.xhtml.DefaultContentHandlerFactory;
import org.apache.poi.xwpf.converter.xhtml.IContentHandlerFactory;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

/**
 * Created by jzb on 16-10-16.
 */
public class Word2HtmlTest {
    static String filePath = "/home/jzb/KuaiPan/Work/2016/审计报告issuelistV2.docx";
    static String imgUrl = "ImageLoader?imageId=";

    public static void main(String[] args) throws Exception {
        XHTMLOptions options = XHTMLOptions.create().URIResolver((uri) -> {
            int ls = uri.lastIndexOf('/');
            if (ls >= 0)
                uri = uri.substring(ls + 1);
            return imgUrl + uri;
        });
        J.file2Html()
                .ext(FilenameUtils.getExtension(filePath))
                .filePath(filePath)
                .htmlFilePath("/home/jzb/test.html")
                .options(options)
                .build()
                .start();
//        J.file2Html(toHtmlType, filePath, "/home/jzb/test.html", options);
//        try (InputStream fis = new FileInputStream(filePath);
//             XWPFDocument document = new XWPFDocument(fis);
//             OutputStream out = new FileOutputStream("/home/jzb/test.html");) {
//            IContentHandlerFactory factory = options.getContentHandlerFactory();
//            if (factory == null) {
//                factory = DefaultContentHandlerFactory.INSTANCE;
//            }
//            options.setIgnoreStylesIfUnused(false);
//            KWBXHTMLMapper mapper = new KWBXHTMLMapper(document, factory.create(out, null, options), options);
//            mapper.start();
//        }
    }
}
