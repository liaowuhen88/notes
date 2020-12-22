package com.zwc.notes.rk.nio;

import java.io.*;

public class FileSplit {
    public static void main(String[] args) throws IOException {
        String path = "D:\\data\\logfile";
        File file = new File(path);
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
        // 用5M的缓冲读取文本文件
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "utf-8"), 512 * 1024);

        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
//TODO: write your business
        }
    }
}