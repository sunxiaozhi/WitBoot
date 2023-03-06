# WitBoot

## 组件构成

* **witboot-api**：API层，对外以 jar 包的形式提供接口
* **witboot-core**：核心层，主要包括公共配置、工具类等
* **witboot-dao**：数据持久层，与底层MySQL、Elasticsearch、MongoDB等进行数据交互
* **witboot-service**：业务逻辑服务层
* **witboot-manager**：通用业务处理层，对第三方平台进行接口封装，对 Service 层通用能力的下沉，如缓存方案、中间件通用处理，与 DAO 层交互，对多个 DAO 的组合复用。
* **witboot-web**：请求处理层，对访问控制进行转发，各类基本参数校验，或者不复用的业务简单处理等
* **witboot-start**：程序启动入口

## 待办任务
* ~~系统分层架构设计~~
* Swagger  Knife4j增强
* 接口版本
* MyBatis-Plus
* MySQL
* Druid
* Validate校验
* ~~统一数据返回~~
* ~~统一异常处理~~
* lombok配置
* Redis
* Elasticsearch
* Kafka
* Flyway

## 待完成功能
* 用户管理
* 组织机构管理
* 权限角色管理
* 菜单管理
* 字典管理
* 性能监控
* 数据变更记录(审计)

## 开发规范