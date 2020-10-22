package com.zwc.notes.rk.serialize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerializeUtils {
    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        String s = "111";
        oos.writeObject(s);
        String str = baos.toString();

        byte[] baStr = baos.toByteArray();
        byte[] gbStr = str.getBytes();

        System.out.println(baStr.length);
        System.out.println(gbStr.length);
        System.out.println("-----1----");
        for (int i = 0; i < baStr.length; i++) {
            System.out.println(baStr[i]);
        }
        System.out.println("-----2----");

        for (int i = 0; i < gbStr.length; i++) {
            System.out.println(gbStr[i]);
        }
        System.out.println("-----3----");
        byte[] testStr = baStr;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < testStr.length; i++) {
            sb.append(Integer.toBinaryString(testStr[i]) + " ");
        }
        System.out.println(sb.toString());
    }
}
