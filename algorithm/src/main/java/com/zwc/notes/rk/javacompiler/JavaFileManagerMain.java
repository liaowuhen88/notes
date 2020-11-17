package com.zwc.notes.rk.javacompiler;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class JavaFileManagerMain {
    public static void main(String[] args) throws IOException {
        String fullQuanlifiedFileName = "D:\\java\\notes\\algorithm\\src\\main\\java\\com\\zwc\\notes\\rk\\javacompiler\\Calculator.java";
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager =
                compiler.getStandardFileManager(null, null, null);

        Iterable<? extends JavaFileObject> files =
                fileManager.getJavaFileObjectsFromStrings(
                        Arrays.asList(fullQuanlifiedFileName));
        JavaCompiler.CompilationTask task = compiler.getTask(
                null, fileManager, null, null, null, files);

        Boolean result = task.call();
        if (result == true) {
            System.out.println("Succeeded");
        }
        String[] strs = {"3", "2"};
        Process process = Runtime.getRuntime().exec("java com.zwc.notes.rk.javacompiler.Calculator", strs, new File("D:\\java\\notes\\algorithm\\src\\main\\java\\com\\zwc\\notes\\rk\\javacompiler\\"));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            System.out.println("success:" + str);
        }

        BufferedReader errorBufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "gbk"));
        String errorStr;
        while ((errorStr = errorBufferedReader.readLine()) != null) {
            System.out.println("fail:" + errorStr);
        }

    }

}
