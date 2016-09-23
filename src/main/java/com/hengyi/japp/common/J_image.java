package com.hengyi.japp.common;

import com.google.common.collect.ImmutableMap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Created by jzb on 15-12-5.
 */
public class J_image {

    public static final BufferedImage qrcode(String content) throws WriterException {
        Map<EncodeHintType, Object> hints = ImmutableMap.of(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 350, 350, hints);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return image;
    }
}
