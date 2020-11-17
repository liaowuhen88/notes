package com.zwc.notes.rk.javacompiler;


//CompileMain.java

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * step 1:
 * <p>
 * mvn install:install-file -Dfile=E:\jar\tools.jar -DgroupId=com.sun.tools -DartifactId=tools -Dversion=1.0 -Dpackaging=jar
 * <p>
 * step2:
 * <dependency>
 * <groupId>com.sun.tools</groupId>
 * <artifactId>tools</artifactId>
 * <version>1.0</version>
 * </dependency>
 */
public class CompileMain {

    public static void main(String[] args) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int result = compiler.run(null, null, null, "D:\\java\\notes\\algorithm\\src\\main\\java\\com\\zwc\\notes\\rk\\javacompiler\\Test.java");
        System.out.println(result == 0 ? "编译成功" : "编译失败");

        Process process = Runtime.getRuntime().exec("java Test", null, new File("D:\\java\\notes\\algorithm\\src\\main\\java\\com\\zwc\\notes\\rk\\javacompiler\\"));

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
