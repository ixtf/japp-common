package com.hengyi.japp.common.toHtml.word;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by jzb on 16-10-16.
 */
public class DocToHtml {
    private final String filePath;
    private final String htmlFilePath;

    public DocToHtml(String filePath, String htmlFilePath) {
        this.filePath = filePath;
        this.htmlFilePath = htmlFilePath;
    }

    public void start() throws IOException, ParserConfigurationException, TransformerException {
        try (InputStream input = new FileInputStream(filePath);
             HWPFDocument wordDocument = new HWPFDocument(input);
             ByteArrayOutputStream outStream = new ByteArrayOutputStream();) {
            WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
            wordToHtmlConverter.setPicturesManager((byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) -> suggestedName);
            wordToHtmlConverter.processDocument(wordDocument);
            List pics = wordDocument.getPicturesTable().getAllPictures();
            if (pics != null) {
                for (int i = 0; i < pics.size(); i++) {
                    Picture pic = (Picture) pics.get(i);
                    //TODO 图片处理
//                    File file=FileUtils.getFile(path,pic.suggestFullFileName());
                    File file = FileUtils.getFile(System.getProperty("java.io.tmpdir"), pic.suggestFullFileName());
                    try (OutputStream o = new FileOutputStream(file);) {
                        pic.writeImageContent(o);
                    }
                }
            }
            Document htmlDocument = wordToHtmlConverter.getDocument();

            DOMSource domSource = new DOMSource(htmlDocument);
            StreamResult streamResult = new StreamResult(outStream);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer serializer = tf.newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, StandardCharsets.UTF_8.name());
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");
            serializer.transform(domSource, streamResult);
            String content = new String(outStream.toByteArray());
            FileUtils.write(new File(htmlFilePath), content, StandardCharsets.UTF_8.name());
        }
    }
}
