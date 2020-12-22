package com.zwc.notes.rk.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeMain {
    private static Unsafe unsafe = null;
    private static Field getUnsafe = null;

    static {
        try {
            getUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            getUnsafe.setAccessible(true);
            unsafe = (Unsafe) getUnsafe.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        mm();
    }

    /**
     * 操作数组:
     * 可以获取数组的在内容中的基本偏移量（arrayBaseOffset），获取数组内元素的间隔（比例），
     * 根据数组对象和偏移量获取元素值（getObject），设置数组元素值（putObject），示例如下。
     */
    private static void mm() {
        String[] strings = new String[]{"1", "2", "3"};
        long i = unsafe.arrayBaseOffset(String[].class);
        System.out.println("string[] base offset is :" + i);

//every index scale
        long scale = unsafe.arrayIndexScale(String[].class);
        System.out.println("string[] index scale is " + scale);

//print first string in strings[]
        System.out.println("first element is :" + unsafe.getObject(strings, i));

//set 100 to first string
        unsafe.putObject(strings, i + scale * 0, "100");

//print first string in strings[] again
        System.out.println("after set ,first element is :" + unsafe.getObject(strings, i + scale * 0));
    }
}
