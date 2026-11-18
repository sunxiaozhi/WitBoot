# WitBoot 工程与 Agent 协作规范

## 唯一入口与目标

本文件是整个仓库唯一的项目级 Agent 规范，统一管理前端、后端、接口、测试与 OpenSpec 约定。所有路径均相对仓库根目录。

- **目标态**：建设职责清晰、契约稳定、领域逻辑可独立测试的模块化后台管理系统。下文的架构约束用于指导新代码和后续改造，不表示现有实现已经全部达标。
- **渐进收敛**：在当前任务涉及的范围内向目标态改进，保留既有调用方的兼容性。跨模块重构、契约迁移与依赖升级应有明确范围和验证依据。
- **单一来源**：项目工程规则统一在本文件维护；README 负责使用说明，OpenSpec 负责业务规范与变更记录，技能文件负责具体工作流。工具专用入口只引用本文件，避免复制规则或新增平行规范。
- **事实与要求**：平台及用户指令优先；业务行为遵循本次需求和有效规范，工程决策遵循本文件。实际版本、脚本和配置以 POM、`package.json` 及配置文件为准。
- **沟通**：使用中文说明需求、设计和验证结果；代码标识符使用英文，术语在前后端保持一致。

## 执行方式

1. **确认上下文**：检查工作区差异，阅读目标业务的实现、调用方、相关测试与规范，列清此次受影响的接口和模块，保留用户已有改动。
2. **明确契约**：确定正常行为、失败行为和兼容性。跨模块变更先说明职责分配、数据流及验证方案；会改变业务含义的未决问题需先澄清。
3. **完成闭环**：贯通当前需求涉及的数据模型、后端、前端及必要测试。新增抽象应封装真实复杂度，外部副作用通过可替换的接口隔离。
4. **验证交付**：按下文矩阵完成相关检查，核对最终差异。只有实现及验证完成后才报告完成；受环境限制的检查要说明缺口和影响。

需求分析及业务设计变更时阅读相关 OpenSpec 规范与现有实现。历史设计可用于了解原有决策。

## 后端目标架构

后端位于 `witboot-server/`，使用 Spring Boot 与 COLA，按业务能力组织模块内部代码。目标职责与依赖关系如下：

| 模块 | 职责 | 目标依赖约束 |
| --- | --- | --- |
| `witboot-client` | 用例服务接口、Cmd/Query、响应 DTO/VO、业务错误码 | 保持接口契约独立于 domain 和持久化实现 |
| `witboot-adapter` | HTTP 协议转换、输入格式校验、调用用例、组装响应 | 通过 client 接口调用用例，不访问 Mapper |
| `witboot-app` | 实现用例、协调领域行为、事务与对象装配 | 依赖 client、domain，通过 Gateway 访问外部资源 |
| `witboot-domain` | 实体、值对象、业务不变量、领域服务和 Gateway 接口 | 保持框架无关；共享依赖限于纯基础类型 |
| `witboot-infrastructure` | Gateway 实现、DO、MyBatis、缓存和外部系统适配 | 实现 domain 定义的接口，封装技术细节 |
| `witboot-start` | 应用入口、依赖装配、运行配置与集成测试 | 装配 adapter、app、infrastructure，保持业务逻辑在所属模块 |
| `witboot-common` | 稳定且确有跨业务复用价值的基础类型和工具 | 控制依赖外溢，业务规则留在所属业务模块 |

- **领域逻辑**：业务不变量由实体、值对象或领域服务维护；Controller 处理协议，CmdExe/QueryExe 协调用例。领域测试应能在不启动 Spring、数据库或 Redis 的条件下运行。
- **依赖方向**：domain 的 Gateway 是持久化和外部系统的替换点；实体保持与 Spring Security、HTTP、MyBatis 的隔离，技术适配在外层完成。
- **模型转换**：Cmd/Query 表达输入意图，DTO/VO 表达对外数据，Entity/值对象表达领域含义，DO 表达存储结构。Assembler 负责接口模型与领域模型转换，Convertor 负责领域模型与 DO 转换。
- **命名**：服务接口沿用 `IxxxService`，用例使用 `xxxCmdExe` / `xxxQueryExe`，持久化转换使用 `xxxConvertor`。新建同类模型统一采用所在业务的 DTO/VO 约定，避免仅为命名批量改动既有接口。
- **读写职责**：查询明确分页、过滤和排序；写入明确校验、权限与原子性。事务覆盖完整用例所需的原子操作，传播范围和回滚行为可验证。
- **持久化**：Mapper 接口与 `witboot-infrastructure/src/main/resources/mappers/` 的 SQL/结果映射同步修改；参数绑定、唯一约束、并发更新和查询成本应在受影响场景中验证。
- **结构演进**：涉及数据库结构或存量数据时，提供版本化变更、已有数据兼容方案与验证步骤；仅修改 Java 类型不算完成数据迁移。

## 前端目标架构

前端位于 `witboot-web/`，使用 Vue、TypeScript、Vite、Pinia、Vue Router 与 Element Plus。Vue、路由、状态或 Vite 实现任务读取 [Vue 技能](witboot-web/.agents/skills/vue-best-practices/SKILL.md) 及其要求的参考资料。

- **职责分配**：`src/views/` 组合页面，`src/components/` 承载可复用展示与交互，`src/composables/` 封装业务状态与副作用，`src/api/` 定义有类型的接口调用，`src/stores/` 管理跨页面共享状态。页面局部状态留在页面或对应 composable。
- **组件契约**：采用 `<script setup lang="ts">`，显式定义 props/emits；派生数据用 computed，副作用用 watch 或生命周期。按独立职责拆分，保持状态归属和数据流清楚。
- **统一请求**：业务请求经 `src/api/` 调用 `src/utils/request.ts` 的统一封装，集中处理鉴权、错误和取消请求。返回类型、可空字段和表单模型显式声明，未知外部数据先校验再使用。
- **路由与登录态**：路由声明在 `src/router/routes.ts`，守卫在 `src/router/index.ts`；认证信息通过 `src/utils/auth.ts` 管理。页签、菜单折叠和持久化复用现有 Pinia 入口；修改存储结构时考虑旧缓存兼容及清理。
- **交互完整性**：数据页面覆盖加载、空结果、失败与重试；表单覆盖校验、提交中和重复提交；危险操作有明确反馈。复用 Element Plus、现有布局和样式变量，保证键盘操作及控件标签可用。
- **代码风格**：组件名用 PascalCase，新增业务目录用 kebab-case，导入使用 `@/` 别名；格式以 `.prettierrc.json` 为准。既有 URL、组件契约和持久化键的变更按兼容性迁移处理。
- **依赖管理**：统一使用 pnpm，维护 `pnpm-lock.yaml`；既有 `package-lock.json` 的清理作为独立工具链整理处理，日常任务不同时刷新两套锁文件。

## 前后端契约

- 变更接口时明确路径、HTTP 方法、参数位置、必填与可空规则、响应、失败语义和权限。后端 client/Controller、前端 API 类型及调用方同步更新。
- 复用 `witboot-common` 的 `ResponseResult`、`PageResult` 与全局异常处理，并与 `witboot-web/src/utils/request.d.ts`、`src/utils/request.ts` 保持一致。认证、授权及业务校验失败应有可区分的语义和用户反馈。
- 分页、日期时区、枚举及大整数 ID 的序列化约定在双方契约中一致；映射和边界情况由测试覆盖。
- 对外保持响应模型与持久化模型的隔离；登录信息、密码及内部异常细节按对外契约过滤。
- 公共响应、请求封装、认证、权限、菜单或缓存协议变更，检查所有受影响调用方，并给出迁移或回滚方式。

## 安全与环境

- 授权校验在后端执行；前端可见性用于交互体验。资源访问、批量操作和管理能力需要相应的服务端权限验证。
- 密码处理使用适合口令存储的加盐密码编码器；认证方案调整同时设计已有密码或令牌的兼容与失效策略。
- 凭据从环境变量或本地配置注入；提交、日志和错误响应保持脱敏。浏览器构建变量属于公开信息，不承载服务端密钥。
- 开发与测试显式指定隔离的环境和数据；测试使用可清理的数据，外部服务访问有超时和失败处理。

## OpenSpec 工作流

选取、推进或验收需求时先读 [需求与设计台账](openspec/requirements.md) 和 [需求管理流程](openspec/需求管理.md)。提案关联需求编号，阶段变化时更新台账中的状态与变更链接。

OpenSpec 任务先读取 [项目配置](openspec/config.yaml)、相关 `openspec/specs/` 与目标 `openspec/changes/`；语言和结构遵循配置，业务要求与工程规则各自保留单一来源。

| 任务 | 读取的工作流 |
| --- | --- |
| 探索需求和方案 | [.agents/skills/openspec-explore/SKILL.md](.agents/skills/openspec-explore/SKILL.md) |
| 建立变更提案 | [.agents/skills/openspec-propose/SKILL.md](.agents/skills/openspec-propose/SKILL.md) |
| 实施任务 | [.agents/skills/openspec-apply-change/SKILL.md](.agents/skills/openspec-apply-change/SKILL.md) |
| 修订已有方案 | [.agents/skills/openspec-update-change/SKILL.md](.agents/skills/openspec-update-change/SKILL.md) |
| 同步规范 | [.agents/skills/openspec-sync-specs/SKILL.md](.agents/skills/openspec-sync-specs/SKILL.md) |
| 完成归档 | [.agents/skills/openspec-archive-change/SKILL.md](.agents/skills/openspec-archive-change/SKILL.md) |

- 提案与方案修订只处理规划材料；实施按目标任务清单推进，仅勾选已完成的任务。需求变化时同步保持 proposal、design、specs、tasks 一致。
- OpenSpec 与 Vue 技能保持独立、按任务读取；项目工程规则集中维护在本文件，生成的工作流通过所属工具更新。

## 开发与验证命令

命令对应当前仓库脚本；脚本或工具链调整时同步更新本节。Java 版本以父 POM 的 `java.version` 为准，前端环境与安装步骤见 [前端 README](witboot-web/README.md)。

| 工作目录 | 目的 | 命令 |
| --- | --- | --- |
| 仓库根目录 | 后端编译 | `mvn -f witboot-server/pom.xml compile` |
| 仓库根目录 | 后端测试，须先准备隔离配置 | `mvn -f witboot-server/pom.xml test` |
| 仓库根目录 | 后端打包，包含测试 | `mvn -f witboot-server/pom.xml package` |
| `witboot-web/` | 开发服务 | `pnpm run dev` |
| `witboot-web/` | 类型检查与生产构建 | `pnpm run build` |
| `witboot-web/` | 代码检查，含自动修复 | `pnpm run lint` |
| `witboot-web/` | 单元测试，单次运行 | `pnpm run test:unit --run` |
| `witboot-web/` | E2E，先生成生产构建 | `pnpm run test:e2e` |

`lint` 带 `--fix`，`format` 脚本会写入文件；运行后核对差异，格式化范围限定在当前任务。

## 验证与完成标准

测试通过稳定的业务接口或用户行为验证结果；新增业务规则和缺陷修复覆盖正常、失败及边界场景。领域逻辑优先使用快速单测，SQL、缓存与协议适配使用隔离的集成测试，关键用户旅程使用 E2E。

| 变更范围 | 最小验证要求 |
| --- | --- |
| 仅文档 | 检查路径、命令、规则冲突与失效引用 |
| 前端文案或样式，无逻辑变化 | 相关文件 lint 与界面检查 |
| 前端组件、composable 或业务逻辑 | lint、相关单测与 build |
| 后端业务逻辑 | compile、相关单测；涉及 Spring 装配时补充集成验证 |
| Mapper、SQL、事务或数据结构 | 后端验证及真实隔离数据上的集成验证，检查迁移与回滚 |
| 接口、登录、权限、路由或共享状态 | 受影响端的构建和测试，并验证对应用户主流程、空态与失败路径 |
| 构建、依赖或环境配置 | 受影响端的完整构建与相关检查，注明工具链和部署影响 |

- 确认测试报告中的实际用例数和执行结果；零用例、跳过测试和仅编译通过均需如实区分。缺失相关测试时补充能验证行为的用例。
- 接口联调记录实际环境；界面改动提供可复现的检查步骤，必要时附截图。外部服务缺失或环境限制导致的未验证项，明确说明影响范围。
- 完成前核对实现、接口、测试与规范的一致性，并检查最终 diff，保留与当前任务无关的文件及生成产物。

## 当前实现的迁移注意

以下为执行时需要确认的现状；整改完成后更新或删除对应条目，避免将历史限制保留为长期规则。

- **默认环境**：`witboot-server/witboot-start/src/main/resources/application.yml` 当前激活 `prod`；启动或运行 `@SpringBootTest` 前显式选择本地测试 profile 并检查数据源与 Redis。现有 Redis 测试含写操作。
- **领域耦合**：现有 domain/common 中仍有框架相关依赖，目标分层需按任务逐步收敛；保持构建成功与既有行为兼容，不能直接删除依赖来宣称完成解耦。
- **测试发现**：现有后端混有 JUnit 4/5 测试，start POM 排除了 Vintage 引擎；涉及测试维护时核对用例是否被实际发现，逐步统一测试体系。
- **历史文档**：README 的技术版本、功能清单及旧请求约定可能滞后；版本以构建配置为准，URL、超时、持久化键等运行事实以代码及环境配置为准。

## 交付说明

结果说明包含修改内容及原因、影响范围、实际执行的检查及结果；只在存在兼容性变化、配置调整或未验证项时补充相应风险。接口或权限变更注明前后端协作点与迁移方式，UI 调整注明行为变化。

提交信息使用简洁中文，可采用 `模块: 变更内容`。PR 说明围绕问题、结果与验证证据组织。
