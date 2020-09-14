package com.zwc.notes.rk.gateway;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ApiParamVO {


    @ApiModelProperty(value = "字段名")
    private String name;
    @ApiModelProperty(value = "映射字段名")
    private String aliaName;
    @ApiModelProperty(value = "映射中文字段名")
    private String aliaDetailName;

    @ApiModelProperty(value = "字段中文名")
    private String detailName;

    @ApiModelProperty(value = "类型 0:int 1:long 2:float 3: double 4: char 5:boolean 6:string 7: map 8:set 9:list 10: bigDecimal 11:自定义类  12：date  13 byte ")
    private Integer dataType;

    private String className;
    @ApiModelProperty(value = "默认值")
    private String defaultValue;
    @ApiModelProperty(value = "固定值")
    private String fixed;

    @ApiModelProperty(value = "是否可空值必须为0或者1")
    private Integer required;

    private Integer length;

    private Integer precision;

    private Integer parentId;

    private Integer apiId;

    private String apiName;
    @ApiModelProperty(value = "参数类型 0 入参 1 出参")
    private Integer paramType;

    private String version;

    @ApiModelProperty(value = "状态  1 启用 0 禁用")
    private Integer status;

    private String desc;

    @ApiModelProperty(value = "层级参数")
    private List<ApiParamVO> child;

    private Integer level;

    private String uuid;

    private String parentUuid;

    private String describe;
}
