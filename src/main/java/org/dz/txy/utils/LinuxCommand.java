package org.dz.txy.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LinuxCommand {
    private final static Logger logger = LoggerFactory.getLogger(LinuxCommand.class);
    /**
     * 执行脚本
     * @param location 位置
     * @param shellName 脚本名称
     */
    public static String execShell(String location,String shellName) {
        String sh = location  + shellName;
        InputStream in = null;
        try {
            Process pro = Runtime.getRuntime().exec(new String[]{"/bin/sh","-c",sh});
            pro.waitFor();
            in = pro.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(in));
            String result = read.readLine();
            logger.info("LinuxCommand::execShell,result="+result);
            in.close();
            return result;
        } catch (IOException | InterruptedException e) {
            logger.error("LinuxCommand::execShell,error,{}",e);
        } finally {

        }
        return "LinuxCommand::execShell,error";
    }
}
