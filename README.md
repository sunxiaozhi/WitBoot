# WitBoot

> 本项目结合Spring Boot3.0和COLA 4.0（整洁面向对象分层架构）实现。

## 组件构成
* **witboot-adapter**：
* **witboot-app**：
* **witboot-client**：
* **witboot-domain**：
* **witboot-infrastructure**：
* **witboot-start**：

## 待办任务
* 系统分层架构设计
* Swagger  Knife4j增强
* 接口版本
* MyBatis-Plus
* ~~MySQL~~
* ~~Druid~~
* Validate校验（接口）
* ~~统一数据返回~~
* ~~统一异常处理~~
* ~~Actuator~~
* ~~log4j2配置~~
* Redis
* Elasticsearch
* Kafka
* Flyway
* 接口限流
* 接口幂等性
* 接口加签验签

## 待完成功能
* 用户管理
* 部门管理
* 菜单管理
* 角色权限管理
* 字典管理
* 性能监控
* 数据变更记录
* 操作日志记录

## 开发规范

### 方法名约定

|CRUD操作| 方法名约定 |
|---|---|
|新增| create |
|添加| add |
|删除| remove（App和Domain层），delete（Infrastructure层） |
|修改| update |
|查询（单个结果） | get |
|查询（多个结果） | list |
|分页查询| page |
|统计| count |