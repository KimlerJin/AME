//package com.ame.util;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.ByteArrayOutputStream;
//import java.util.Hashtable;
//import java.util.Map;
//
///**
// * 转成二维码
// *
// * @author andrew_he
// *
// */
//public class QRCodeUtils {
//    private static final Logger log = LoggerFactory.getLogger(QRCodeUtils.class);
//
//    private static int onColor = 0xFF000000; // 前景色
//    private static int offColor = 0xFFFFFFFF; // 背景色
//
//    public static byte[] generateQRCode(String value) {
//        byte[] byteArray = null;
//        Map<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
//        // 指定纠错等级
//        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
//        // 指定编码格式
//        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//        try {
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            BitMatrix bitMatrix = new MultiFormatWriter().encode(value, BarcodeFormat.QR_CODE, 500, 500, hints);
//            MatrixToImageConfig config = new MatrixToImageConfig(onColor, offColor);
//            MatrixToImageWriter.writeToStream(bitMatrix, "png", byteArrayOutputStream, config);
//            byteArray = byteArrayOutputStream.toByteArray();
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        return byteArray;
//    }
//
//    public static void generateQRCodeImage(String value) {
//
//    }
//
//}
