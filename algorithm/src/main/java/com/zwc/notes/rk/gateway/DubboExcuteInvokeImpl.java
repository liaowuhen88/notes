package com.zwc.notes.rk.gateway;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DubboExcuteInvokeImpl {

    private Map<String, GenericService> classServiceMap = Maps.newHashMap();

    private String dubboIndex = "dubbo注册中心地址";

    public Object excute(ApiVO apiVO, JSONObject paramObject) {
        String method = apiVO.getMethodName();
        GenericService genericService = classServiceMap.get(getKey(apiVO));
        if (genericService == null) {
            genericService = initGenericService(apiVO);
        }

        String[] getTypes = ApiVOUtils.getTypes(apiVO.getInputList());
        Object[] datas;
        //if (null != parameterType && 1 == parameterType) {
        Map map = new HashMap();
        ApiVOUtils.initOnelevel2More(map, apiVO.getInputList(), paramObject);
        ApiVOUtils.checkApiParamDatas(apiVO.getInputList(), JSON.parseObject(JSON.toJSONString(map)));

        datas = new Object[apiVO.getInputList().size()];
        for (int i = 0; i < apiVO.getInputList().size(); i++) {
            datas[i] = map.get(apiVO.getInputList().get(i).getName());
        }

        log.info("调用方单层级入参:{}", JSON.toJSONString(paramObject));
        log.info(JSON.toJSONString(getTypes));
        log.info("调用第三方多层级入参:{}", JSON.toJSONString(datas));

        Object result = genericService.$invoke(method, getTypes,
                datas);
        log.info("调用第三方多层级出参：{}", JSON.toJSONString(result));
        Map mapResult = new HashMap();
        ApiVOUtils.initOutputToOnelevel(mapResult, apiVO.getOutputList(), JSON.parseObject(JSON.toJSONString(result)));
        log.info("单层级出参：{}", JSON.toJSONString(mapResult));
        return mapResult;
    }

    private GenericService initGenericService(ApiVO apiVO) {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("finance-gateway-manager");

        RegistryConfig registry = new RegistryConfig();
        registry.setAddress(dubboIndex);

        application.setRegistry(registry);

        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
// 弱类型接口名
        reference.setInterface(apiVO.getClassName());
        reference.setVersion("1.0.0");
        reference.setGroup(apiVO.getAlia());
// 声明为泛化接口
        reference.setGeneric(true);

        reference.setApplication(application);

// 用com.alibaba.dubbo.rpc.service.GenericService可以替代所有接口引用
        GenericService genericService = reference.get();

        classServiceMap.put(getKey(apiVO), genericService);
        return genericService;
    }

    private String getKey(ApiVO apiVO) {
        String interfaceClazz = apiVO.getClassName();
        String alia = apiVO.getAlia();

        if (StringUtils.isNotEmpty(apiVO.getToken())) {
            return interfaceClazz + "|" + alia + "|" + apiVO.getToken();
        } else {
            return interfaceClazz + "|" + alia;
        }
    }


}
