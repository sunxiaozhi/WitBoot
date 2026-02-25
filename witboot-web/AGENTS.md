# AGENTS.md

## 适用范围
本文件适用于当前仓库根目录及其所有子目录。

## 规则优先级
规则冲突时按以下顺序执行（从高到低）：
1. 用户在当前任务中的明确指令
2. 本文件（`AGENTS.md`）
3. 仓库内其他说明文档（如 `README.md`、模块内注释约定）
4. 通用工程实践默认值

## 项目定位
- 技术栈：`Vue 3` + `TypeScript` + `Vite` + `Pinia` + `Vue Router` + `Element Plus`
- 目标：后台管理系统前端（用户、角色、菜单、日志、系统监控等模块）
- 请求层统一入口：`src/utils/request.ts`

## 目录约定
- `src/views/`：页面模块（按业务目录组织）
- `src/components/`：可复用组件
- `src/layouts/`：布局组件（主布局：`Main.vue`）
- `src/api/`：接口定义，不在页面里直接写请求
- `src/router/`：路由和导航守卫
- `src/stores/`：Pinia 状态管理
- `src/utils/`：通用工具（鉴权、请求封装、响应码等）
- `cypress/`：E2E 测试

## 开发原则
1. 保持小步改动，优先修复根因，避免无关重构。
2. 新增业务逻辑时，优先放在 `views + api + stores` 合理分层，不把逻辑塞进模板。
3. 页面中禁止创建分散的 Axios 实例，统一使用 `src/utils/request.ts`。
4. 优先复用现有组件和样式变量，避免重复实现。
5. 保持与当前 UI 风格一致（Element Plus + 现有布局/表格/抽屉交互模式）。
6. 接口相关改动必须同步更新 `src/api` 层的类型与调用方，避免以 `any` 兜底掩盖问题。
7. 单次任务优先保证单一目标，不跨业务模块做顺手重构；如需扩散改动，应先说明原因与影响。

## 代码风格
- 使用 `script setup` + TypeScript。
- 缩进 2 空格、单引号、无分号、行宽 100（遵循 Prettier）。
- 组件文件名使用 PascalCase（如 `UserDrawer.vue`）。
- 业务页面目录名使用 kebab-case（如 `operation-log`）。
- 导入路径优先使用 `@/` 别名。

## 命令规范
- 启动开发：`npm run dev`
- 类型检查 + 构建：`npm run build`
- 仅构建：`npm run build-only`
- 生产预览：`npm run preview`
- 类型检查：`npm run type-check`
- 单元测试：`npm run test:unit`
- E2E（preview）：`npm run test:e2e`
- E2E（dev）：`npm run test:e2e:dev`
- 代码检查：`npm run lint`
- 代码格式化：`npm run format`

## 实现约定补充
- 请求封装保持在 `src/utils/request.ts`，默认约定：基础 URL 为 `/api`、超时 5000ms、`withCredentials: true`、`Content-Type: application/json`。
- 请求方法约定使用统一封装：`GET<T>`、`POST<T>`、`PUT<T>`、`DELETE<T>`。
- 需要鉴权的请求通过 `isToken` 控制是否附加 Bearer Token，Token 来源为 `getAccessToken()`，本地存储 key 为 `accessToken`。
- 认证守卫由 `src/router/index.ts` 统一处理，受保护路由使用 `meta.requiresAuth`。
- 路由定义位于 `src/router/routes.ts`；`/login` 外路由与 tab 行为由现有 `menuTabsStore` 机制维护，禁止绕开。
- Pinia 中 `menuTabsStore`、`menuCollapseStore` 使用持久化能力时，保持与现有 localStorage 行为一致。
- 路径别名统一使用 `@/` 指向 `src/`，新增代码优先使用该别名导入。

## 任务分级与最小校验矩阵
- 文案/样式微调（无逻辑变化）：至少执行 `npm run lint`
- 组件交互或页面逻辑改动：至少执行 `npm run lint` + `npm run test:unit`（无对应单测时执行 `npm run build`）
- 路由、鉴权、状态管理改动：至少执行 `npm run build`，并补充关键流程手工验证步骤
- API 结构或请求参数/响应处理改动：至少执行 `npm run build`，并验证对应页面主流程
- 构建配置改动（`vite.config.ts`、`tsconfig*`、`eslint.config.js`）：至少执行 `npm run build` + `npm run lint`

## 高优先级业务模块清单
- 登录与鉴权：`src/views/login/`、`src/utils/auth.ts`、`src/router/index.ts`
- 路由与权限入口：`src/router/`、`src/layouts/Main.vue`
- 全局请求与响应处理：`src/utils/request.ts`、`src/utils/responseCode.ts`
- 关键业务页面：`src/views/user/`、`src/views/role/`、`src/views/menu/`
- 日志与审计相关：`src/views/login-log/`、`src/views/operation-log/`、对应 `src/api/*`

上述模块改动时额外要求：
1. 在结果中单独标记“高优先级模块变更”及影响面。
2. 至少执行 `npm run build`，并给出可复现的关键流程手工验证步骤。
3. 若存在接口字段或权限行为变化，需明确前后端协作点和回滚注意事项。

## 变更执行清单
1. 先阅读将要修改模块的现有实现，确认依赖关系（`api`、`store`、`router`、组件复用点）。
2. 实现最小闭环改动，避免顺手改动无关文件。
3. 至少执行与改动匹配的校验：
   - 纯类型/逻辑改动：`npm run build` 或 `npm run test:unit`
   - UI/交互改动：`npm run lint`，必要时补充截图说明
4. 输出变更说明时包含：
   - 修改了哪些文件
   - 为什么这么改
   - 如何验证
   - 已知风险或后续建议

## 异常处理约定
1. 命令执行失败时，先检查是否为参数/环境问题并做一次有依据的重试。
2. 若失败由外部条件导致（接口不可用、环境变量缺失、权限限制），需在结果中明确阻塞点与影响范围。
3. 无法继续推进时，给出可选降级方案（如手工验证路径）并标注可信度边界。
4. 不跳过关键校验且不说明理由。

## 测试要求
- 新增核心逻辑时补最小可验证单测（`*.test.ts` / `*.spec.ts`）。
- 涉及关键主流程（登录、路由跳转、列表操作）变更时，补充或更新 Cypress 用例。
- 若当前任务无法补测，需明确说明未覆盖点和潜在风险。
- 若无法补充自动化测试，至少提供可复现的手工验证步骤和回归检查清单。

## 提交与 PR 规范
- Commit 信息使用简洁中文，建议格式：`模块: 变更内容`
  - 示例：`用户管理: 修复编辑抽屉回填异常`
- PR 描述至少包含：
  - 变更背景
  - 主要修改点
  - 影响范围
  - 验证方式
- 涉及 UI 调整附截图；涉及接口变更注明前后端协作点。

## Agent 输出模板
每次任务完成后，按以下结构输出结果：
1. 变更文件：列出具体文件路径
2. 行为变化：说明用户可感知的变化与技术原因
3. 验证结果：列出已执行命令与结论（通过/失败）
4. 风险与未覆盖：说明未验证场景、潜在回归点
5. 后续建议：仅在确有必要时给出

## 安全与配置
- 本地配置放 `.env.development`，严禁提交密钥、令牌、生产地址凭据。
- 不在日志中打印敏感信息（token、cookie、用户隐私字段）。
- `package.json`、`vite.config.ts`、`tsconfig*`、`.env*` 变更需在说明中单独标记影响范围。

## 禁止事项
- 未经说明，不修改与任务无关的大量格式或重命名。
- 未经确认，不升级核心依赖或切换构建工具链。
- 不绕开 `router`/`store` 现有机制做临时硬编码。
