package com.zwc.notes.rk.gateway;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 参数层级转化
 * 多层级转单层级
 * 单层纪转多层级
 */
public class ApiVOUtils {
    /**
     * 单层级参数转化为多层级
     *
     * @param map
     * @param list
     * @param paramObject
     */
    public static void init(Map map, List<ApiParamVO> list, JSONObject paramObject) {
        Iterator var3 = list.iterator();

        while (var3.hasNext()) {
            ApiParamVO apiParamVO = (ApiParamVO) var3.next();
            if (null != apiParamVO.getChild()) {
                HashMap mapchi = new HashMap();
                map.put(apiParamVO.getName(), mapchi);
                init(mapchi, apiParamVO.getChild(), paramObject);
            } else {
                String name = apiParamVO.getAliaName();
                if (StringUtils.isEmpty(name)) {
                    name = apiParamVO.getName();
                }

                Object value = paramObject.get(name);
                map.put(apiParamVO.getName(), value);
            }
        }

    }
}
