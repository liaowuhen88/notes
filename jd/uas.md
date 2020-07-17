// 权限管理系统
uas 获取权限数据
```  
{
  "resource": {
    "roles": [
      "admin",
      "developer",
      "user"
    ],
    "stringPermissions": [
      "logDetail:view",
      "tableConstruct:view",
      "tableConstruct:delete",
      "tableConstruct:update",
      "nameData:update",
      "logDetail:add",
      "nameData:add",
      "dictionary:delete",
      "log:update",
      "logDetail:update",
      "login:delete",
      "nameData:delete",
      "login:view",
      "file:update",
      "dictionary:update",
      "dataAuth:view",
      "nameData:view",
      "login:update",
      "nameList:view",
      "file:view",
      "dataAuth:update",
      "log:view",
      "dictionary:view",
      "login:add",
      "file:delete",
      "dataAuth:delete",
      "log:delete",
      "logDetail:delete",
      "tableConstruct:add",
      "nameList:add",
      "dictionary:add",
      "dataAuth:add",
      "nameList:delete",
      "log:add",
      "nameList:update",
      "file:add"
    ]
  },
  "account": {
    "accountType": "companyChild",
    "apps": [
      "finance-gateway-manager",
      "jrs-namelist-api",
      "jrs-namelist-admin",
      "jdflow-manager",
      "finance-gateway-runtime",
      "sgy-carrier-presure",
      "jdflow-runtime",
      "risk_heaven_book"
    ],
    "companyId": "jdjr",
    "dataSource": "local",
    "displayName": "张文超",
    "email": "zhangwenchao19@jd.com",
    "erpName": "zhangwenchao19",
    "loginName": "zhangwenchao19",
    "organizationFullName": "京东集团-京东数字科技-风险管理中心-风险研发部-决策平台研发组",
    "phone": "18649041578",
    "state": "1",
    "userType": "erp"
  }
}
```  

* roles 角色类型
* stringPermissions 菜单权限
* account 用户信息
* account.apps 当前用户拥有系统权限

