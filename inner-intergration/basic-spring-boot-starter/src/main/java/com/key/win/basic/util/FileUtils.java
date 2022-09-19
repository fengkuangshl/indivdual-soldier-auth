package com.key.win.basic.util;

import com.key.win.basic.exception.BizException;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.stream.Stream;

public class FileUtils {

    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 文件的md5
     *
     * @param inputStream
     * @return
     */
    public static String fileMd5(InputStream inputStream) {
        try {
            return DigestUtils.md5Hex(inputStream);
        } catch (IOException e) {
            logger.error("fileMd5 error:{}", e.getMessage(), e);
        }

        return null;
    }

    public static String saveFile(MultipartFile file, String path) {
        try {
            File targetFile = new File(path);
            if (targetFile.exists()) {
                return path;
            }

            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();
            }
            file.transferTo(targetFile);

            return path;
        } catch (Exception e) {
            logger.error("file save  error:{}", e.getMessage(), e);
        }

        return null;
    }

    public static boolean deleteFile(String pathname) {
        File file = new File(pathname);
        if (file.exists()) {
            boolean flag = file.delete();

            if (flag) {
                File[] files = file.getParentFile().listFiles();
                if (files == null || files.length == 0) {
                    file.getParentFile().delete();
                }
            }

            return flag;
        }

        return false;
    }

    public static void uploadFile(InputStream inputStream, String path, String fileName) throws Exception {
        File pathFile = new File(path);
        try {
            if (!pathFile.exists()) {
                pathFile.mkdir();
            }
        } catch (Exception e) {
            throw new BizException("上传文件夹创建失败");
        }
        //创建file
        File destFile = new File(path + fileName);
        //复制临时文件到指定目录下
        try {
            org.apache.commons.io.FileUtils.copyInputStreamToFile(inputStream, destFile);
            logger.info("{}上传成功！", path + fileName);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public static boolean uploadFile(byte[] file, String fileName, String uploadPhysicalPath) {
        boolean isSuccessUploadFile = false;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(uploadPhysicalPath);
            fos.write(file);
            fos.close();
            isSuccessUploadFile = true;
        } catch (Exception e) {
            logger.error(fileName + ":上传文件出错", e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    logger.error(fileName + ":上传文件出错(关闭时)", e);
                }
            }
        }
        return isSuccessUploadFile;
    }

    public static InputStream base64ToInputStream(String base64) {
        byte[] bytes = Base64.getDecoder().decode(base64);
        return new ByteArrayInputStream(bytes);
    }

    public static String getFileFullPath(String filePath) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);
        filePath = filePath.replaceAll("/", Matcher.quoteReplacement(File.separator));
        return filePath + File.separator + year + File.separator + (month + 1) + File.separator + day + File.separator;
    }

    public static String getChunkFileFullPath(String filePath, String md5) {
        return FileUtils.getFileFullPath(filePath) + "chunk" + File.separator + md5 + File.separator;
    }

    public static String getFilePhysicalPath(String filePath) {
        return AccessPathUtils.getRootPath() + FileUtils.getFileFullPath(filePath);
    }

    public static String getChunkFilePhysicalPath(String filePath, String md5) {
        return AccessPathUtils.getRootPath() + FileUtils.getChunkFileFullPath(filePath,md5);
    }

    /**
     * 文件合并
     *
     * @param targetFile
     * @param folder
     */
    public static void merge(String targetFile, String folder, String filename) {
        try {
            Files.createFile(Paths.get(targetFile));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        try (Stream<Path> stream = Files.list(Paths.get(folder))) {
            stream.filter(path -> !path.getFileName().toString().equals(filename))
                    .sorted((o1, o2) -> {
                        String p1 = o1.getFileName().toString();
                        String p2 = o2.getFileName().toString();
                        int i1 = p1.lastIndexOf("-");
                        int i2 = p2.lastIndexOf("-");
                        return Integer.valueOf(p2.substring(i2)).compareTo(Integer.valueOf(p1.substring(i1)));
                    })
                    .forEach(path -> {
                        try {
                            //以追加的形式写入文件
                            Files.write(Paths.get(targetFile), Files.readAllBytes(path), StandardOpenOption.APPEND);
                            //合并后删除该块
                            Files.delete(path);
                        } catch (IOException e) {
                            logger.error(e.getMessage(), e);
                        }
                    });
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
