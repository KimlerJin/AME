package com.ame.util;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
    private static final int BUFFER_SIZE = 2 * 1024;

    /**
     * 压缩成ZIP 方法2
     * 
     * @param srcFiles
     *            需要压缩的文件列表
     * @param out
     *            压缩文件输出流
     * @throws RuntimeException
     *             压缩失败会抛出运行时异常
     */
    public static void toZip(List<File> srcFiles, OutputStream out) throws RuntimeException {
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void toZip(List<byte[]> bytes, List<String> filteNames, OutputStream out) throws RuntimeException {
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            for (int i = 0; i < bytes.size(); i++) {
                zos.putNextEntry(new ZipEntry(filteNames.get(i)));
                zos.write(bytes.get(i));
                zos.closeEntry();

            }
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //
    // public static void main(String[] args) throws Exception {
    // /** 测试压缩方法1 */
    // FileOutputStream fos1 = new FileOutputStream(new File("d:/mytest01.zip"));
    // ZipUtil.toZip("D:/AaBb.java", fos1,true);

    // }
}
