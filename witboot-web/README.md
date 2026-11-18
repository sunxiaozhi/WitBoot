# WitBoot Web

WitBoot Web 是一个基于 `Vue 3 + TypeScript + Vite + Element Plus` 的后台管理系统前端项目，当前已覆盖登录鉴权、首页工作台、用户与组织管理、菜单与角色管理、字典管理、日志审计、系统监控等常见管理后台模块。

前后端工程与 Agent 协作规范统一见仓库根目录 [AGENTS.md](../AGENTS.md)。

## 功能概览

- 登录与鉴权
  - 基于 `accessToken` 的登录态管理
  - 路由守卫控制受保护页面访问
  - 登录失效后自动清理本地状态并跳转登录页
- 主框架布局
  - 左侧菜单导航
  - 顶部通用头部
  - 页签式多标签导航
  - 页签右键菜单支持刷新、关闭当前、关闭其他、关闭左侧、关闭右侧、关闭全部
- 基础业务模块
  - 首页工作台
  - 人员管理
  - 部门管理
  - 菜单管理
  - 角色管理
  - 字典管理
  - 登录日志
  - 操作日志
  - 系统监控
  - 关于系统
- 异常页面
  - 404 页面

## 技术栈

- Vue 3
- TypeScript
- Vite 5
- Vue Router 4
- Pinia
- Element Plus
- Axios
- Sass
- ESLint 9
- Prettier 3
- Vitest
- Cypress

## 运行环境

- Node.js 18+
- pnpm 9+

建议统一使用 `pnpm` 作为包管理工具，避免与 `npm` 混用导致锁文件和依赖链接状态不一致。

## 快速开始

### 1. 安装依赖

```bash
pnpm install
```

### 2. 配置环境变量

开发环境变量位于 `.env.development`：

```env
SASS_DART_JS_API=modern-compiler
VITE_API_BASE_URL=http://localhost:8010
```

其中：

- `VITE_API_BASE_URL`：后端接口基础地址
- `SASS_DART_JS_API`：用于关闭 Sass 旧版 API 警告

### 3. 启动开发环境

```bash
pnpm dev
```

默认使用 Vite 启动本地开发服务，并开启局域网访问。

### 4. 生产构建

```bash
pnpm build
```

该命令会先执行类型检查，再执行生产构建。

### 5. 本地预览生产包

```bash
pnpm preview
```

## 可用脚本

| 命令                | 说明                              |
| ------------------- | --------------------------------- |
| `pnpm dev`          | 启动本地开发服务                  |
| `pnpm build`        | 类型检查并构建生产包              |
| `pnpm build-only`   | 仅执行 Vite 构建                  |
| `pnpm preview`      | 预览生产构建结果                  |
| `pnpm type-check`   | 执行 `vue-tsc` 类型检查           |
| `pnpm lint`         | 执行 ESLint 并自动修复可修复问题  |
| `pnpm format`       | 按 Prettier 规则格式化整个仓库    |
| `pnpm test:unit`    | 运行 Vitest 单元测试              |
| `pnpm test:e2e`     | 基于 preview 服务执行 Cypress E2E |
| `pnpm test:e2e:dev` | 基于开发服务启动 Cypress UI       |

## 当前页面模块

项目当前已注册的主要路由如下：

| 路由             | 页面     |
| ---------------- | -------- |
| `/login`         | 登录页   |
| `/home`          | 首页     |
| `/user`          | 人员管理 |
| `/department`    | 部门管理 |
| `/menu`          | 菜单管理 |
| `/role`          | 角色管理 |
| `/dictionary`    | 字典管理 |
| `/loginLog`      | 登录日志 |
| `/operationLog`  | 操作日志 |
| `/systemMonitor` | 系统监控 |
| `/about`         | 关于系统 |

## 目录结构

```text
.
├─ public/                    静态资源
├─ cypress/                   E2E 测试
├─ src/
│  ├─ api/                    接口定义
│  ├─ assets/                 全局样式与静态资源
│  ├─ components/             通用组件
│  ├─ composables/            组合式逻辑封装
│  ├─ config/                 站点级配置
│  ├─ layouts/                布局组件
│  ├─ router/                 路由配置与导航守卫
│  ├─ stores/                 Pinia 状态管理
│  ├─ utils/                  请求封装、鉴权、响应码等工具
│  └─ views/                  页面模块
├─ .env.development           开发环境变量
├─ eslint.config.js           ESLint 配置
├─ .prettierrc.json           Prettier 配置
├─ vite.config.ts             Vite 配置
└─ package.json               项目脚本与依赖
```

## 项目约定

### 请求与接口

- 请求统一通过 `src/utils/request.ts` 发起
- 默认请求配置：
  - 超时时间 `5000ms`
  - `withCredentials: true`
  - `Content-Type: application/json`
- 鉴权请求通过 `isToken` 控制是否自动附加 `Bearer Token`
- 新增接口时应优先补充 `src/api/` 类型与调用封装，不在页面中直接写 Axios 请求

### 鉴权与本地存储

- Token 本地存储 key 为 `accessToken`
- 路由是否需要登录由 `meta.requiresAuth` 控制
- 菜单折叠状态和页签状态通过 Pinia 持久化到 `localStorage`
- 登出时会清理：
  - `accessToken`
  - 页签缓存
  - 菜单折叠状态

### 代码风格

- 使用 `script setup` + TypeScript
- 2 空格缩进
- 单引号
- 不使用分号
- 单行宽度 100

## 开发建议

- 页面业务逻辑优先放在 `views + api + composables/stores` 的合理分层中
- 保持请求入口统一，不在页面中创建分散的 Axios 实例
- 优先复用现有通用组件和样式变量，保持后台界面风格一致
- 涉及路由、鉴权、状态管理、接口结构时，提交前至少执行一次 `pnpm build`

## 团队协作

### 分支与提交建议

- 功能开发、问题修复、样式调整尽量分支隔离，避免多类改动混在一次提交中
- 提交信息建议使用简洁中文，格式可参考：
  - `用户管理: 修复编辑抽屉回填异常`
  - `菜单页签: 增加右键关闭其他功能`
  - `README: 重构项目说明文档`
- 大规模格式化、依赖升级、配置调整建议单独提交，避免影响业务 review

### 联调约定

- 前后端联调前先确认 `VITE_API_BASE_URL` 指向的环境是否正确
- 接口变更时，前端应同步更新：
  - `src/api/` 中的请求方法与类型定义
  - 页面或 composable 中的调用参数
  - 相关成功态、空态、错误态处理
- 如后端返回码、字段名、分页结构发生变化，应在联调说明中明确标注，避免页面静默失败
- 涉及登录态、权限控制、菜单返回结构调整时，需同步回归登录、路由跳转、页签行为

### 合并前检查

建议在提交或发起 PR 前完成以下检查：

```bash
pnpm lint
pnpm build
```

如改动涉及核心交互或主流程，补充执行：

```bash
pnpm test:unit
pnpm test:e2e
```

检查重点：

- 是否新增了未使用变量、调试代码或临时注释
- 是否绕开了 `src/utils/request.ts` 直接发请求
- 是否出现了 `any` 兜底、接口类型缺失、错误提示缺失
- 是否影响登录、路由守卫、页签、持久化状态等公共能力

### PR 说明建议

PR 描述建议至少包含以下内容：

- 变更背景
- 主要修改点
- 影响范围
- 验证方式
- 已知风险或未覆盖场景

如果涉及以下类型改动，建议额外补充：

- UI 调整：附截图或录屏
- 接口字段变更：注明前后端协作点
- 权限或菜单逻辑变更：说明影响角色、页面入口和回滚注意事项

### 常见协作问题

- `node_modules` 异常或 CLI 入口丢失时，优先执行 `pnpm install`
- 不建议混用 `pnpm` 和 `npm`，避免锁文件和依赖链接状态不一致
- 如果格式化改动过多，优先拆成单独提交再继续业务开发
- 修改高优先级模块时，务必补充手工回归路径，尤其是：
  - 登录与退出
  - 路由跳转
  - 页签切换与关闭
  - 列表查询、分页、编辑、删除

## 验证建议

常见本地验证顺序：

```bash
pnpm lint
pnpm build
```

如涉及交互主流程，可补充：

```bash
pnpm test:unit
pnpm test:e2e
```

## 维护说明

- 站点标题配置位于 `src/config/siteConfig.ts`
- 路由定义位于 `src/router/routes.ts`
- 登录守卫位于 `src/router/index.ts`
- 页签导航状态位于 `src/stores/menuTabsStore.ts`
- 请求封装入口位于 `src/utils/request.ts`
