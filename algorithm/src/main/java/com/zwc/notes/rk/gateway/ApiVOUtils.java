package com.zwc.notes.rk.gateway;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 参数层级转化
 * 多层级转单层级
 * 单层纪转多层级
 */
public class ApiVOUtils {
    /**
     * 获取顶级目录,并且初始化继承关系
     * 主要用于把单层级list参数根据parentUuid初始化为树类型
     */
    public static List<ApiParamVO> getTopLevel(List<ApiParamVO> apiParamVOList) {
        if (null == apiParamVOList || apiParamVOList.size() == 0) {
            return apiParamVOList;
        }
        convertParam(apiParamVOList);
        apiParamVOList = apiParamVOList.stream()
                .filter(paramVO -> null == paramVO.getParentUuid() || 0 == paramVO.getParentId())
                .collect(Collectors.toList());
        return apiParamVOList;
    }

    /**
     * 转化多层级参数返回值为单层级参数
     * <p>
     * input map保存name
     *
     * @param map
     * @param list
     * @param paramObject
     */
    public static void initInputToOneLevel(Map map, List<ApiParamVO> list, JSONObject paramObject) {
        if (null == paramObject) {
            return;
        }
        for (ApiParamVO apiParamVO : list) {
            if (null != apiParamVO.getChild()) {
                initInputToOneLevel(map, apiParamVO.getChild(), paramObject.getJSONObject(getRealName(apiParamVO)));
            } else {
                Object value = paramObject.get(getRealName(apiParamVO));
                map.put(apiParamVO.getName(), value);
            }
        }
    }

    /**
     * 根据parentUuid，把当前参数添加到父参数下
     */
    private static void convertParam(List<ApiParamVO> apiParamVOList) {
        // 主键关系
        Map<String, ApiParamVO> idParamMap = apiParamVOList.stream()
                .collect(Collectors.toMap(ApiParamVO::getUuid,
                        paramVO -> paramVO));

        for (ApiParamVO vo : apiParamVOList) {
            if (null != idParamMap.get(vo.getParentUuid())) {
                if (null == idParamMap.get(vo.getParentUuid()).getChild()) {
                    idParamMap.get(vo.getParentUuid()).setChild(new ArrayList<>());
                }
                idParamMap.get(vo.getParentUuid()).getChild().add(vo);
            }
        }
    }

    /**
     * 根据配置得请求参数，初始化方法入参类型
     *
     * @param inputList
     * @return
     */
    public static String[] getTypes(List<ApiParamVO> inputList) {
        if (null == inputList || inputList.size() == 0) {
            return null;
        }
        String[] rslts = new String[inputList.size()];
        for (int i = 0; i < inputList.size(); i++) {
            String className = DataType.getClassNameByType(inputList.get(i).getDataType());
            className = StringUtils.isEmpty(className) ? inputList.get(i).getClassName() : className;
            rslts[i] = className;
        }
        return rslts;
    }

    /**
     *  转化单层级参数到多层级参数
     * @param map
     * @param list
     * @param paramObject
     */
    public static void initOnelevel2More(Map map, List<ApiParamVO> list, JSONObject paramObject) {
        for (ApiParamVO apiParamVO : list) {
            if (null != apiParamVO.getChild()) {
                HashMap mapchi = new HashMap();
                map.put(apiParamVO.getName(), mapchi);
                initOnelevel2More(mapchi, apiParamVO.getChild(), paramObject);
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

    /**
     * 多层级参数转为单层级
     * @param map
     * @param list
     * @param paramObject
     */
    public static void initOutputToOnelevel(Map map, List<ApiParamVO> list, JSONObject paramObject) {
        if (null == paramObject) {
            return;
        }
        for (ApiParamVO apiParamVO : list) {
            if (null != apiParamVO.getChild()) {
                initOutputToOnelevel(map, apiParamVO.getChild(), paramObject.getJSONObject(apiParamVO.getName()));
            } else {
                Object value = paramObject.get(apiParamVO.getName());
                if (isNull(value)) {
                    value = apiParamVO.getDefaultValue();
                }
                String name = apiParamVO.getAliaName();
                // 如果有别名，优先取别名
                if (StringUtils.isEmpty(name)) {
                    name = apiParamVO.getName();
                }
                map.put(name, value);
            }
        }
    }

    /**
     * 验证参数合法性
     *
     * @param inputList
     * @param paramObject
     */
    public static void checkApiParamDatas(List<ApiParamVO> inputList, JSONObject paramObject) {
        for (int i = 0; i < inputList.size(); i++) {
            ApiParamVO apiParamVO = inputList.get(i);
            //自定义类处理
            if (hasChind(apiParamVO)) {
                List<ApiParamVO> child = apiParamVO.getChild();
                if (null == child || child.size() == 0) {
                    throw new IllegalArgumentException(apiParamVO.getClassName() + "自定义对象参数未配置");
                }
                if (null == paramObject) {
                    // 需要校验非空
                    if (1 == apiParamVO.getRequired()) {
                        throw new IllegalArgumentException(getRealName(apiParamVO) + "值不能为空");
                    }
                } else {
                    checkApiParamDatas(child, paramObject.getJSONObject(apiParamVO.getName()));
                }
            } else {
                Object paramValue = getValue(apiParamVO.getDataType(), apiParamVO.getName(), apiParamVO.getAliaName(), paramObject);
                // 需要校验非空
                if (1 == apiParamVO.getRequired()) {
                    if (isNull(paramValue)) {
                        throw new IllegalArgumentException(getRealName(apiParamVO) + "值不能为空");
                    }
                }
            }

        }
    }

    /**
     * 参数是否包含子参数
     *
     * @param apiParamVO
     * @return
     */
    private static boolean hasChind(ApiParamVO apiParamVO) {
        if (null != apiParamVO.getChild() && apiParamVO.getChild().size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 获取映射参数名
     *
     * @param apiParamVO
     * @return
     */
    private static String getRealName(ApiParamVO apiParamVO) {
        if (StringUtils.isNotEmpty(apiParamVO.getAliaName())) {
            return apiParamVO.getAliaName();
        }
        return apiParamVO.getName();
    }

    /**
     * 判断参数是否为空
     *
     * @param paramValue
     * @return
     */
    public static boolean isNull(Object paramValue) {
        if (null == paramValue) {
            return true;
        } else {
            if (paramValue instanceof String) {
                if (StringUtils.isEmpty((String) paramValue)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Object getValue(int dataType, String name, String aliaName, JSONObject paramObject) {
        if (null == paramObject) {
            return null;
        }
        // 优先映射
        Object value = null;
        if (StringUtils.isNotEmpty(aliaName)) {
            value = paramObject.get(aliaName);
        }
        if (null == value) {
            value = paramObject.get(name);
        }
        if (null == value) {
            return null;
        }
        switch (dataType) {
            case 0:
                return TypeUtils.castToInt(value);
            case 1:
                return TypeUtils.castToLong(value);
            case 2:
                return TypeUtils.castToFloat(value);
            case 3:
                return TypeUtils.castToDouble(value);
            case 4:
                String str = TypeUtils.castToString(value);
                if (StringUtils.isEmpty(str)) {
                    return null;
                } else {
                    return str.charAt(0);
                }
            case 5:
                return TypeUtils.castToBoolean(value);
            case 6:
                return TypeUtils.castToString(value);
            case 7:
                if (value instanceof JSONObject) {
                    return value;
                } else {
                    throw new RuntimeException(value + "can not cast to map ");
                }
            case 8:
                if (value instanceof JSONArray) {
                    return ((JSONArray) value).stream().collect(Collectors.toSet());
                } else {
                    throw new RuntimeException(value + "can not cast to set ");
                }
            case 9:
                return TypeUtils.castToJavaBean(value, List.class);
            case 10:
                return TypeUtils.castToBigDecimal(value);
            case 11:
                return value;
            case 12:
                return TypeUtils.castToDate(value);
            case 13:
                return TypeUtils.castToByte(value);
            default:
                throw new RuntimeException(dataType + "未知数据类型");
        }
    }
}
