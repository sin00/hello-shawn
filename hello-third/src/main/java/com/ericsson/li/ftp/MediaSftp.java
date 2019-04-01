package com.ericsson.li.ftp;

import java.io.*;

import com.jcraft.jsch.*;

public class MediaSftp {
    public Session session;
    private String usr;
    private String pwd;

    public MediaSftp(String usr, String pwd, String ip) throws JSchException {
        this.usr = usr;
        this.pwd = pwd;
        Connect(ip);
    }

    public void Connect(String ip) throws JSchException {
        JSch jsch = new JSch();
        try {
            this.session = jsch.getSession(this.usr, ip, 22);
            if (session != null) {
                session.setPassword(this.pwd);
                session.setConfig("StrictHostKeyChecking", "no");
                session.setTimeout(30000);
                session.connect();
                System.out.println("connect to " + ip);
                if (session != null && !session.isConnected()) {
                    this.close();
                }
            }
        } catch (JSchException e) {
            throw e;
        }
    }

    public void close() {
        if (session != null && session.isConnected()) {
            session.disconnect();
//            this.isConnectSSH = false;
        } else {
        }
    }

    public boolean download(String downloadFile, String saveFile) throws Exception {
        try {
            ChannelSftp sftp = (ChannelSftp)
                    session.openChannel("sftp");
            sftp.connect();
            sftp.cd("/sftp_dir/");
            File file = new File(saveFile);
            System.out.println(saveFile);
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            InputStream in = null;
            FileOutputStream outputStream = new FileOutputStream(file);

            in = sftp.get(downloadFile);
            byte[] buffer = new byte[20480];
            int count = 0;
            while ((count = in.read(buffer)) != -1) {
                if (count == 0) break;
                outputStream.write(buffer, 0, count);
            }

            in.close();
            outputStream.close();
            outputStream.flush();
//        sftp.disconnect();
            this.close();/**/
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    public void excuteCmd(String cmd) throws Exception {
        ChannelExec exec = null;
        try {
            Channel channel = session.openChannel("exec");
            exec = (ChannelExec) channel;
        } catch (JSchException e) {
            e.printStackTrace();
        }

        InputStream inputStream = exec.getInputStream();
        exec.setCommand(cmd);
        exec.connect();
        //StringBuffer buffer = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream), 524288000);
        String line = "";
        while ((line = reader.readLine()) != null) {
            //buffer.append(line);
            System.out.println(line);
        }

        //System.out.println("====>:");
       // System.out.println(buffer.toString());
        reader.close();
        inputStream.close();
        exec.disconnect();


    }

    public static void main(String[] args) throws Exception{
        MediaSftp mediaSftp = new MediaSftp("litx", "xtil", "192.168.88.128");
        String cmd = "mkdir test_dir;cd sftp_dir/; zip ../test_dir/hh.zip *hh";
        mediaSftp.excuteCmd(cmd);
        mediaSftp.close();
    }

}
