package com.zwc.notes.rk.gateway;

import java.util.HashMap;
import java.util.Map;

public enum DataType {
    INT(0, "java.lang.Integer"),
    LONG(1, "java.lang.Long"),
    FLOAT(2, "java.lang.Float"),
    DOUBLE(3, "java.lang.Double"),
    CHAR(4, "java.lang.Character"),
    BOOLEAN(5, "java.lang.Boolean"),
    STRING(6, "java.lang.String"),
    MAP(7, "java.util.Map"),
    SET(8, "java.util.Set"),
    LIST(9, "java.util.List"),
    BIGDECIMAL(10, "java.math.BigDecimal"),
    USER_TYPE(11, ""),
    DATE(12, "java.util.Date"),
    Byte(13, "java.lang.Byte"),
    STRING_ARR(14, "java.util.String[]");

    private static final Map<Integer, String> TYPE_CLADD_MAP = new HashMap<>();

    static {
        for (DataType dataType : DataType.values()) {
            TYPE_CLADD_MAP.put(dataType.getType(), dataType.getClassName());
        }
    }

    private int type;

    private String className;

    DataType(int type, String className) {
        this.type = type;
        this.className = className;
    }

    public static String getClassNameByType(int type) {
        return TYPE_CLADD_MAP.get(type);
    }

    public int getType() {
        return type;
    }

    public String getClassName() {
        return className;
    }
}
