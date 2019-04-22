package com.cs.analyzefood.util;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class FTPUtil {

    protected Logger LOG = LoggerFactory.getLogger(getClass());

    public Channel getChannel(Session session) {
        Channel channel = null;
        try {
            channel = session.openChannel("sftp");
            channel.connect();
            LOG.info("get Channel success!");
        } catch (JSchException e) {
            LOG.info("get Channel fail!", e);
        }
        return channel;
    }

    public Session getSession(String host, int port, String username,
                              final String password) {
        Session session = null;
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            session = jsch.getSession(username, host, port);
            session.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            session.setConfig(sshConfig);
            session.connect();
            LOG.info("Session connected!");
        } catch (JSchException e) {
            LOG.info("get Channel failed!", e);
        }
        return session;
    }

    /**
     * 创建文件夹
     */
    public void mkdir(ChannelSftp sftp, String dir) {
        try {
            sftp.mkdir(dir);
            System.out.println("创建文件夹成功！");
        } catch (SftpException e) {
            System.out.println("创建文件夹失败！");
            e.printStackTrace();
        }
    }


    /**
     * @param sftp
     * @param dir
     * @param file
     * @return
     */
    public String uploadFile(ChannelSftp sftp, String dir, File file) {
        String result = "";
        try {
            sftp.cd(dir);
            // File file = new File("D://34.txt"); //要上传的本地文件
            if (file != null) {
                sftp.put(new FileInputStream(file), file.getName());
                result = "上传成功！";
            } else {
                result = "文件为空！不能上传！";
            }
        } catch (Exception e) {
            LOG.info("上传失败！", e);
            result = "上传失败！";
        }
        return result;
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     * @param saveFile     存在本地的路径
     * @param sftp
     */
    public String download(String directory, String downloadFile,
                           String saveFile, ChannelSftp sftp) {
        String result = "";
        try {
            sftp.cd(directory);
            sftp.get(downloadFile, saveFile);
            result = "下载成功！";
        } catch (Exception e) {
            result = "下载失败！";
            LOG.info("下载失败！", e);
        }
        return result;
    }

    /**
     * 删除文件
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     * @param sftp
     */
    public String delete(String directory, String deleteFile, ChannelSftp sftp) {
        String result = "";
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
            result = "删除成功！";
        } catch (Exception e) {
            result = "删除失败！";
            LOG.info("删除失败！", e);
        }
        return result;
    }

    private void closeChannel(Channel channel) {
        if (channel != null) {
            if (channel.isConnected()) {
                channel.disconnect();
            }
        }
    }

    private void closeSession(Session session) {
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }

    public void closeAll(ChannelSftp sftp, Channel channel, Session session) {
        try {
            closeChannel(sftp);
            closeChannel(channel);
            closeSession(session);
        } catch (Exception e) {
            LOG.info("closeAll", e);
        }
    }
}
