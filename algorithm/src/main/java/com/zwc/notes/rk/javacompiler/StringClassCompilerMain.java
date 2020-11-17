package com.zwc.notes.rk.javacompiler;

import javax.tools.*;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class StringClassCompilerMain {
    public static void main(String[] args) {
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();
        StandardJavaFileManager standardJavaFileManager = javaCompiler.getStandardFileManager(null, null, null);
        JavaFileObject testFile = generateTest();
        Iterable<? extends JavaFileObject> classes = Arrays.asList(testFile);
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null, standardJavaFileManager, collector, null, null, classes);
        if (task.call()) {
            System.out.println("success");
        } else {
            System.out.println("failure!");
        }

        List<Diagnostic<? extends JavaFileObject>> diagnostics = collector.getDiagnostics();
        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics) {
            System.out.println("line:" + diagnostic.getLineNumber());
            System.out.println("msg:" + diagnostic.getMessage(Locale.ENGLISH));
            System.out.println("source:" + diagnostic.getSource());

        }
    }

    private static JavaFileObject generateTest() {
        String contents = new String(
                "package com.zwc.notes.rk.javacompiler;" +
                        "class CalculatorTest {\n" +
                        "  public void testMultiply() {\n" +
                        "    Calculator c = new Calculator();\n" +
                        "    System.out.println(c.multiply(2, 4));\n" +
                        "  }\n" +
                        "  public static void main(String[] args) {\n" +
                        "    CalculatorTest ct = new CalculatorTest();\n" +
                        "    ct.testMultiply();\n" +
                        "  }\n" +
                        "}\n");
        StringObject so = null;
        try {
            so = new StringObject("win.hgfdodo.dynamic.CalculatorTest", contents);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return so;

    }
}
