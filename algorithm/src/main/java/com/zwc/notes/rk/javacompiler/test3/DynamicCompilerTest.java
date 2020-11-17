package com.zwc.notes.rk.javacompiler.test3;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class DynamicCompilerTest {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        StringBuilder src = new StringBuilder();
        src.append("package win.hgfdodo.compiler;");
        src.append("public class DynaClass {\n");
        src.append("    public String toString() {\n");
        src.append("        return \"Hello, I am \" + ");
        src.append("this.getClass().getSimpleName();\n");
        src.append("    }\n");
        src.append("}\n");

        String fullName = "win.hgfdodo.compiler.DynaClass";

        DynamicCompiler compiler = new DynamicCompiler();
        Class clz = compiler.compileAndLoad(fullName, src.toString());

        System.out.println(clz.newInstance().getClass());
        //获取方法
        Method printMethod = clz.getMethod("toString");
        //调用
        Object vo = printMethod.invoke(clz.newInstance());
        System.out.println(vo instanceof String);
        System.out.println(vo);
        System.out.println(clz.getConstructor().newInstance());
        compiler.closeFileManager();
    }
}
