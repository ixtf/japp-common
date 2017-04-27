package com.hengyi.japp.common.toHtml;

import com.hengyi.japp.common.toHtml.excel.ExcelToHtml;
import com.hengyi.japp.common.toHtml.word.DocToHtml;
import com.hengyi.japp.common.toHtml.word.DocxToHtml;
import org.apache.poi.xwpf.converter.xhtml.DefaultContentHandlerFactory;
import org.apache.poi.xwpf.converter.xhtml.IContentHandlerFactory;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

/**
 * Created by jzb on 16-11-7.
 */
public class ToHtml {
    private final ToHtmlType toHtmlType;
    private final String filePath;
    private final String htmlFilePath;
    private final XHTMLOptions options;

    ToHtml(ToHtmlType toHtmlType, String filePath, String htmlFilePath, XHTMLOptions options) {
        this.toHtmlType = toHtmlType;
        this.filePath = filePath;
        this.htmlFilePath = htmlFilePath;
        this.options = options;
    }

    public void start() throws Exception {
        switch (toHtmlType) {
            case XLS:
            case XLSX:
                ExcelToHtml excelToHtml = ExcelToHtml.create(filePath, new PrintWriter(new FileWriter(htmlFilePath)));
                excelToHtml.setCompleteHTML(true);
                excelToHtml.printPage();
                return;
            case DOC:
                DocToHtml docToHtml = new DocToHtml(filePath, htmlFilePath);
                docToHtml.start();
                return;
            case DOCX:
                try (InputStream fis = new FileInputStream(filePath);
                     XWPFDocument document = new XWPFDocument(fis);
                     OutputStream out = new FileOutputStream(htmlFilePath);) {
                    IContentHandlerFactory factory = options.getContentHandlerFactory();
                    if (factory == null) {
                        factory = DefaultContentHandlerFactory.INSTANCE;
                    }
                    options.setIgnoreStylesIfUnused(false);
                    DocxToHtml mapper = new DocxToHtml(document, factory.create(out, null, options), options);
                    mapper.start();
                }
                return;
        }
    }
}
