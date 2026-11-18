# Proposal

## Why

WitBoot 的后端默认启用 `prod`，而 `application-dev.yml` 尚无配置。需要一条可复现的本地启动与验证路径，为后续 Java 功能开发和 OpenSpec 验收提供独立环境。

## What Changes

- 落实 `ENV-001`。提供独立的开发和测试配置，从本地参数读取 MySQL、Redis 等连接信息。生产配置须显式选择。
- 落实 `ENV-002`。记录空白本地环境的准备、启动、检查与停止步骤，并验证前端入口和后端健康状态。
- 明确本次验收边界。登录成功的验收依赖管理员初始化需求 `DB-002`，数据库版本化迁移由 `DB-001` 跟踪。

## Capabilities

### New Capabilities

- `local-development` 提供隔离的本地开发与测试环境，以及可重复执行的启动验证。

### Modified Capabilities

无。

## Impact

- 涉及后端 Spring profile、MySQL 和 Redis 连接配置，以及前端开发环境的 API 地址。
- 涉及前后端启动文档和本地验证记录。依赖当前使用的 JDK、Maven、pnpm、MySQL 和 Redis。
- **BREAKING** 依赖隐式 `prod` 启动的部署启动方式需要显式选择生产 profile。
