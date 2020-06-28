package com.zwc.notes.rk.dynamic;


import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class ValidUtils {
    protected static final String ACTIVE_DATE = "active_date";
    protected static final String EXP_DATE = "exp_date";

    static Date getDate(Object expDate0) {
        if (expDate0 instanceof java.lang.String && StringUtils.isNotBlank(String.valueOf(expDate0))) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return sdf.parse(String.valueOf(expDate0));
            } catch (Exception e) {
                //log.error("exp_date parse error:{}", e.getMessage());
            }
        } else if (expDate0 instanceof Date) {
            return (Date) expDate0;
        }
        return null;
    }

    public static boolean isNull(Object object) {
        if (null == object) {
            return true;
        }
        if (object instanceof String) {
            return StringUtils.isBlank(String.valueOf(object));
        }
        return false;
    }

    /**
     * 是否有效
     *
     * @param resMap
     * @return
     */
    protected boolean isValid(Map<String, Object> resMap) {

        Object activeDate = resMap.get(ACTIVE_DATE);
        Object expDate0 = resMap.get(EXP_DATE);

        Date startTime = activeDate == null ? null : getDate(activeDate);
        Date endTime = expDate0 == null ? null : getDate(expDate0);

        if (isNull(startTime)) {
            if (isNull(endTime)) {
                //时间都为空
                //永久有效
                return true;
            } else {
                // 存在结束时间，则当前时间小于结束时间
                if (System.currentTimeMillis() <= endTime.getTime()) {
                    // 未过期
                    return true;
                } else {
                    // 过期
                    return false;
                }
            }
        } else {
            if (isNull(endTime)) {
                // 当前时间大于开始时间
                if (System.currentTimeMillis() > startTime.getTime()) {
                    // 未过期
                    return true;
                } else {
                    // 过期
                    return false;
                }

            } else {
                // 都不为空
                // 大于开始时间，小于结束时间
                if (System.currentTimeMillis() > startTime.getTime() && System.currentTimeMillis() <= endTime.getTime()) {
                    // 未过期
                    return true;
                } else {
                    // 过期
                    return false;
                }
            }
        }
    }

}
