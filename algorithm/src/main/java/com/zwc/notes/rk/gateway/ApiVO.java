package com.zwc.notes.rk.gateway;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ApiVO {
    @ApiModelProperty(value = "API中文名称")
    private String detailName;
    @ApiModelProperty(value = "接口类型,1 JSF; 0 HTTP")
    private Integer apiType;
    @ApiModelProperty(value = "接口类名")
    private String className;
    @ApiModelProperty(value = "方法名")
    private String methodName;
    @ApiModelProperty(value = "接口别名")
    private String alia;

    private String token;
    @ApiModelProperty(value = "版本号")
    private String version;

    private Integer maxPerDay;
    @ApiModelProperty(value = "原版本号")
    private String sourceVersion;

    private String upgradeReason;
    @ApiModelProperty(value = "超时时间")
    private Integer timeout;

    private Integer downgradeCode;


    @ApiModelProperty(value = "请求入参")
    private List<ApiParamVO> inputList;
    @ApiModelProperty(value = "请求出参")
    private List<ApiParamVO> outputList;

    @ApiModelProperty(value = "appId,所属应用id")
    private Integer appId;

    private String leader;

    private String httpMethod;

    private String url;

    private String httpHeader;

    private String uuid;

}
