package com.hengyi.japp.common.toHtml;

import com.hengyi.japp.common.toHtml.excel.ExcelToHtml;
import com.hengyi.japp.common.toHtml.word.DocToHtml;
import com.hengyi.japp.common.toHtml.word.DocxToHtml;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.converter.xhtml.DefaultContentHandlerFactory;
import org.apache.poi.xwpf.converter.xhtml.IContentHandlerFactory;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

/**
 * Created by jzb on 16-11-7.
 */
public class ToHtmlBuilder {
    private String ext;
    private String filePath;
    private String htmlFilePath;
    private XHTMLOptions options;

    public ToHtmlBuilder ext(String ext) {
        this.ext = ext;
        return this;
    }

    public ToHtmlBuilder filePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public ToHtmlBuilder htmlFilePath(String htmlFilePath) {
        this.htmlFilePath = htmlFilePath;
        return this;
    }

    public ToHtmlBuilder options(XHTMLOptions options) {
        this.options = options;
        return this;
    }

    public ToHtml build() {
        ToHtmlType toHtmlType = ToHtmlType.valueOf(StringUtils.upperCase(ext));
        return new ToHtml(toHtmlType, filePath, htmlFilePath, options);
    }

}
