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
// * 转换为条形码
// *
// * @author andrew_he
// *
// */
//public class BarCodeUtils {
//
//    public static final Logger logger = LoggerFactory.getLogger(BarCodeUtils.class);
//
//    public static byte[] generateBarCode(String value) {
//        byte[] byteArray = null;
//        Map<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
//        // 指定纠错等级
//        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
//        // 指定编码格式
//        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//        try {
//            BitMatrix bitMatrix = new MultiFormatWriter().encode(value, BarcodeFormat.CODE_128, 60, 80, hints);
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            MatrixToImageWriter.writeToStream(bitMatrix, "png", byteArrayOutputStream);
//            byteArray = byteArrayOutputStream.toByteArray();
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//        return byteArray;
//    }
//
//}
