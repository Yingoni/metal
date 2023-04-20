package com.bs.metal.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @auther Bongo
 * @create 2023/4/16 22:47
 */
public class PyUtil {
    public static void toPy() throws IOException, InterruptedException {
        System.out.println("开始");
        String executer = "D:/anaconda/envs/yolov5/python.exe";
        String file_path = "E:/Python Studio/yolov5-V6.0/yolov5-V6.0/detect.py";
        String[] command_line = new String[]{executer,file_path};
        Process process = Runtime.getRuntime().exec(command_line);
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = in.readLine())!=null){
            System.out.println(line);
        }
        in.close();
        process.waitFor();
        System.out.println("结束");
    }
}
