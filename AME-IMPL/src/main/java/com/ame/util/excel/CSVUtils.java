//package com.ame.util.excel;
//
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.lang3.StringUtils;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.OutputStreamWriter;
//import java.util.List;
//import java.util.Map;
//
//public class CSVUtils {
//
//    /**
//     * 生成为CVS文件
//     *
//     * @param heads
//     *            CSV文件的列表头
//     * @param exportData
//     *            源数据List
//     * @param outputPath
//     *            文件路径
//     * @param fileName
//     *            文件名称
//     * @return
//     */
//    public static File createCSVFile(List<String> heads, List<Map<String, String>> exportData, String outputPath,
//        String fileName) throws Exception {
//        File csvFile = null;
//        BufferedWriter csvFileOutputStream = null;
//        if (!outputPath.endsWith("\\")) {
//            outputPath += "\\";
//        }
//        File file = new File(outputPath);
//        if (!file.exists()) {
//            file.mkdir();
//        }
//        csvFile = new File(outputPath + fileName + ".csv");
//        if (csvFile.exists()) {
//            deleteFile(outputPath, fileName);
//        }
//        System.out.println("csvFile：" + csvFile);
//
//        csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GBK"), 1024);
//        //双边排序，确保顺序对的上
////        Collections.sort(heads);
//        for (int i = 0; i < heads.size(); i++) {
//            csvFileOutputStream.write(heads.get(i) != null ? new String((heads.get(i)).getBytes("GBK"), "GBK") : "");
//            if (i < heads.size() - 1) {
//                csvFileOutputStream.write(",");
//            }
//        }
//        csvFileOutputStream.write("\r\n");
//        // 写入文件内容
//        for (Map<String, String> exportDatum : exportData) {
//            boolean isFirst = true;
//            //双边排序，确保顺序对的上
////            List<String> keys = exportDatum.keySet().stream().collect(Collectors.toList());
////            Collections.sort(keys);
//            for (String s : heads) {
//                if (isFirst) {
//                    isFirst = false;
//                } else {
//                    csvFileOutputStream.write(",");
//                }
//                csvFileOutputStream.write(exportDatum.get(s) == null ? "" : exportDatum.get(s));
//            }
//            csvFileOutputStream.write("\r\n");
//        }
//        csvFileOutputStream.flush();
//        csvFileOutputStream.close();
//        return csvFile;
//    }
//
//    /**
//     * 返回CSV模板文件byte
//     *
//     * @param fileName
//     * @param heads
//     * @return
//     * @throws Exception
//     */
//    public static byte[] createCSVFileBytes(String fileName, List<String> heads) throws Exception {
//        if (StringUtils.isEmpty(fileName)) {
//            return new byte[0];
//        }
//        if (fileName.length() < 3) {// 必须三个字符
//            fileName = fileName + "--";
//        }
//        File csvFile = File.createTempFile(fileName, ".csv");
//        csvFile.deleteOnExit();
//        BufferedWriter csvFileOutputStream =
//            new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GBK"), 1024);
//        for (int i = 0; i < heads.size(); i++) {
//            csvFileOutputStream.write(heads.get(i) != null ? new String((heads.get(i)).getBytes("GBK"), "GBK") : "");
//            if (i < heads.size() - 1) {
//                csvFileOutputStream.write(",");
//            }
//        }
//        csvFileOutputStream.write("\r\n");
//        csvFileOutputStream.flush();
//        csvFileOutputStream.close();
//        return FileUtils.readFileToByteArray(csvFile);
//    }
//
//    /**
//     * 删除该目录filePath下的所有文件
//     *
//     * @param filePath
//     *            文件目录路径
//     */
//    public static void deleteFiles(String filePath) {
//        File file = new File(filePath);
//        if (file.exists()) {
//            File[] files = file.listFiles();
//            for (int i = 0; i < files.length; i++) {
//                if (files[i].isFile()) {
//                    files[i].delete();
//                }
//            }
//        }
//    }
//
//    /**
//     * 删除文件
//     *
//     * @param filePath
//     *            文件名
//     */
//    public static void deleteFile(String filePath) {
//        File file = new File(filePath);
//        if (file.exists()) {
//            file.delete();
//        }
//    }
//
//    /**
//     * 删除单个文件
//     *
//     * @param filePath
//     *            文件目录路径
//     * @param fileName
//     *            文件名称
//     */
//    public static void deleteFile(String filePath, String fileName) {
//        File file = new File(filePath);
//        if (file.exists()) {
//            File[] files = file.listFiles();
//            for (int i = 0; i < files.length; i++) {
//                if (files[i].isFile()) {
//                    if (files[i].getName().equals(fileName)) {
//                        files[i].delete();
//                        return;
//                    }
//                }
//            }
//        }
//    }
//
//}