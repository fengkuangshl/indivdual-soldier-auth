package com.key.win.file.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.MalformedURLException;

@Component
public class FtpUtils {

    private static Logger logger = LoggerFactory.getLogger(FtpUtils.class);

    @Value("${ftp.host}")
    private String host;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.username}")
    private String username;

    @Value("${ftp.password}")
    private String password;

    /**
     * 初始化ftp服务器
     */
    private FTPClient init() {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8");
        try {
            logger.info("开始连接ftp服务器:" + host + ":" + port);
            ftpClient.connect(host, port); // 连接ftp服务器
            logger.info("开始校验账号密码...");
            ftpClient.login(username, password); // 登录ftp服务器
            int replyCode = ftpClient.getReplyCode(); // 是否成功登录服务器
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                logger.error("连接失败...");
            } else {
                logger.error("连接成功~");
            }
        } catch (MalformedURLException e) {
            logger.error("上传文件,FPT init MalformedURLException  失败:{}", e.getMessage(), e);
        } catch (IOException e) {
            logger.error("上传文件,FPT init IOException  失败:{}", e.getMessage(), e);
        }
        return ftpClient;
    }

    /**
     * 上传文件
     *
     * @param pathName       ftp服务保存地址
     * @param fileName       上传到ftp的文件名
     * @param originfilename 待上传文件的名称（绝对地址） *
     * @return
     */
    public boolean uploadFile(String pathName, String fileName, String originfilename) {
        FTPClient ftpClient = init();
        boolean connected = ftpClient.isConnected();
        logger.info("当前连接状态:" + connected);
        InputStream inputStream = null;
        try {
            logger.info("开始上传文件:------------------------------------->");
            logger.info("上传到ftp的文件名:->" + fileName);
            logger.info("ftp服务保存地址:->" + pathName);
            logger.info("待上传文件的名称（绝对地址）:->" + originfilename);
            inputStream = new FileInputStream(new File(originfilename));
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //将客户端设置为被动模式
            ftpClient.enterLocalPassiveMode();
            createDirecroty(ftpClient, pathName);
            ftpClient.makeDirectory(pathName);
            ftpClient.changeWorkingDirectory(pathName);
            ftpClient.storeFile(fileName, inputStream);
            inputStream.close();
            ftpClient.logout();
            logger.info("文件上传文件成功");
            logger.info("<------------------------------------->");
        } catch (Exception e) {
            logger.error("上传文件失败:" + e.getMessage(), e);
            return false;
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    logger.error("上传文件,FPT disconnect 失败:{}", e.getMessage(), e);
                }
            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("上传文件,FPT关闭失败:{}", e.getMessage(), e);
                }
            }
        }
        return true;
    }


    /**
     * 上传分片文件
     *
     * @param pathName    ftp服务保存地址
     * @param fileName    上传到ftp的文件名
     * @param inputStream 输入文件流
     * @return
     */
    public String uploadFileSub(String pathName, String fileName, InputStream inputStream, boolean chunkOne) {
        FTPClient ftpClient = init();
        boolean flag = false;
        try {
            logger.info("开始上传分片文件:------------------------------------->");
            logger.info("上传到ftp的文件名:->" + fileName);
            logger.info("ftp服务保存地址:->" + pathName);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            createDirecroty(ftpClient, pathName);
            ftpClient.changeWorkingDirectory(pathName);
            //将客户端设置为被动模式
            ftpClient.enterLocalPassiveMode();
            if (chunkOne) {
                ftpClient.storeFile(fileName, inputStream);
            } else {
                //向目标文件拼接流数据
                ftpClient.appendFile(fileName, inputStream);
            }
            inputStream.close();
            ftpClient.logout();
            flag = true;
            logger.info("上传文件成功");
        } catch (Exception e) {
            logger.error("上传文件失败" + e.getMessage());
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    logger.error("上传文件,FPT disconnect 失败:{}", e.getMessage(), e);
                }
            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("上传文件,FPT关闭失败:{}", e.getMessage(), e);
                }
            }
        }
        return host;
    }


    /**
     * 上传文件
     *
     * @param pathName    ftp服务保存地址
     * @param fileName    上传到ftp的文件名
     * @param inputStream 输入文件流
     * @return
     */
    public String uploadFile(String pathName, String fileName, InputStream inputStream) {
        FTPClient ftpClient = init();
        boolean flag = false;
        try {
            logger.info("开始上传文件:------------------------------------->");
            logger.info("上传到ftp的文件名:->" + fileName);
            logger.info("ftp服务保存地址:->" + pathName);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //将客户端设置为被动模式
            ftpClient.enterLocalPassiveMode();
            createDirecroty(ftpClient, pathName);
            ftpClient.changeWorkingDirectory(pathName);
            ftpClient.storeFile(fileName, inputStream);
            inputStream.close();
            ftpClient.logout();
            flag = true;
            logger.info("上传文件成功");
        } catch (Exception e) {
            logger.error("上传文件失败" + e.getMessage(), e);
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    logger.error("上传文件,FPT disconnect 失败:{}", e.getMessage(), e);
                }
            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("上传文件,FPT关闭失败:{}", e.getMessage(), e);
                }
            }
        }
        return host;
    }

    // 改变目录路径
    public boolean changeWorkingDirectory(FTPClient ftpClient, String directory) {
        boolean flag = true;
        try {
            flag = ftpClient.changeWorkingDirectory(directory);
            if (flag) {
                logger.info("进入文件夹" + directory + " 成功！");
            } else {
                logger.info("进入文件夹" + directory + " 失败！开始创建文件夹");
            }
        } catch (IOException e) {
            logger.error("改变目录路径失败:{}", e.getMessage(), e);
        }
        return flag;
    }

    // 创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
    public boolean createDirecroty(FTPClient ftpClient, String remote) throws IOException {
        boolean success = true;
        String directory = remote ;
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!directory.equalsIgnoreCase(File.separator) && !changeWorkingDirectory(ftpClient, new String(directory))) {
            int start = 0;
            int end = 0;
            if (directory.startsWith(File.separator)) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf(File.separator, start);
            String path = "";
            String paths = "";
            while (true) {
                String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "UTF-8");
                path = path + File.separator + subDirectory;
                if (!existFile(ftpClient, path)) {
                    if (makeDirectory(ftpClient, subDirectory)) {
                        changeWorkingDirectory(ftpClient, subDirectory);
                    } else {
                        logger.error("创建目录[" + subDirectory + "]失败");
                        changeWorkingDirectory(ftpClient, subDirectory);
                    }
                } else {
                    changeWorkingDirectory(ftpClient, subDirectory);
                }

                paths = paths + File.separator + subDirectory;
                start = end + 1;
                end = directory.indexOf(File.separator, start);
                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }
        return success;
    }

    // 判断ftp服务器文件是否存在
    public boolean existFile(FTPClient ftpClient, String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr.length > 0) {
            flag = true;
        }
        return flag;
    }

    // 创建目录
    public boolean makeDirectory(FTPClient ftpClient, String dir) {
        boolean flag = true;
        try {
            flag = ftpClient.makeDirectory(dir);
            if (flag) {
                logger.info("创建文件夹" + dir + " 成功！");

            } else {
                logger.error("创建文件夹" + dir + " 失败！");
            }
        } catch (Exception e) {
            logger.error("创建文件夹失败:{}", e.getMessage(), e);
        }
        return flag;
    }

    /**
     * * 下载文件 *
     *
     * @param pathName  FTP服务器文件目录 *
     * @param filename  文件名称 *
     * @param localpath 下载后的文件路径 *
     * @return
     * @throws IOException
     */
    public boolean downloadFile(String pathName, String filename, String localpath) throws IOException {
        FTPClient ftpClient = init();
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        boolean flag = false;
        OutputStream os = null;
        try {
            logger.info("开始下载文件");
            // 切换FTP目录
            ftpClient.changeWorkingDirectory(pathName);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile file : ftpFiles) {
                if (filename.equalsIgnoreCase(file.getName())) {
                    File localFile = new File(localpath + "/" + file.getName());
                    os = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(file.getName(), os);
                    os.close();
                }
            }
            ftpClient.logout();
            flag = true;
            logger.info("下载文件成功");
        } catch (Exception e) {
            logger.error("下载文件失败:{}", e.getMessage(), e);
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    logger.error("下载文件,FTP disconnect 失败:{}", e.getMessage(), e);
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("下载文件,FTP关闭失败:{}", e.getMessage(), e);
                }
            }
        }
        return flag;
    }

    /**
     * @param pathName 第一个参数为文件存储在fpt的位置 不能以/ 开始
     * @param filename 文件名称
     * @return
     * @throws IOException
     */
    public InputStream downloadFile2(String pathName, String filename) throws IOException {
        FTPClient ftpClient = init();
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        InputStream in = null;
        try {
            boolean flag = ftpClient.changeWorkingDirectory(pathName);
            logger.info("进入文件夹..." + flag);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile file : ftpFiles) {
                if (filename.equalsIgnoreCase(file.getName())) {
                    in = ftpClient.retrieveFileStream(filename);
                    logger.info("获取文件流成功...");
                }
            }
            //boolean connected = ftpClient.isConnected();
            //if(connected) {
            //ftpClient.logout();
            //}
            //logger.info("登出FTP。。。");
        } catch (Exception e) {
            logger.error("下载文件失败:{}", e.getMessage(), e);
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    logger.error("下载文件,FTP关闭失败:{}", e.getMessage(), e);
                }
            }
        }
        return in;
    }

    /**
     * 删除文件
     *
     * @param pathName FTP服务器保存目录 *
     * @param filename 要删除的文件名称 *
     * @return
     */
    public boolean deleteFile(String pathName, String filename) {
        FTPClient ftpClient = init();
        boolean flag = false;
        try {
            logger.info("开始删除文件");
            // 切换FTP目录
            ftpClient.changeWorkingDirectory(pathName);
            ftpClient.dele(filename);
            ftpClient.logout();
            flag = true;
            logger.info("删除文件成功");
        } catch (Exception e) {
            logger.error("删除文件失败:" + e.getMessage(), e);
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    logger.error("删除文件,FTP关闭失败:{}", e.getMessage(), e);
                }
            }
        }
        return flag;
    }

    public static boolean deleteFile(String pathName) {
        File file = new File(pathName);
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

}
