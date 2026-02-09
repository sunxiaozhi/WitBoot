# CLAUDE.md

此文件为 Claude Code (claude.ai/code) 在此代码库中工作时提供指导。

## 常用命令

```bash
# 开发
npm run dev              # 启动开发服务器，支持热重载（包含 --host 参数）

# 构建
npm run build            # 构建生产版本（包含类型检查）
npm run build-only       # 构建不进行类型检查
npm run preview          # 预览生产构建

# 类型检查
npm run type-check       # 使用 vue-tsc 进行 TypeScript 类型检查

# 代码质量
npm run lint             # 运行 ESLint 并自动修复
npm run format           # 使用 Prettier 格式化代码

# 测试
npm run test:unit        # 使用 Vitest 运行单元测试
npm run test:e2e         # 对生产构建运行 E2E 测试
npm run test:e2e:dev     # 对开发服务器运行 E2E 测试
```

## 技术栈

- **框架**: Vue 3.5.26 with Composition API (`<script setup>`)
- **UI 库**: Element Plus 2.13.0 + Element Plus Icons
- **状态管理**: Pinia 3.0.4 with persistence plugin
- **路由**: Vue Router 4.6.4
- **构建工具**: Vite 5.4.21
- **语言**: TypeScript 5.3.3
- **样式**: SCSS with normalize.css
- **HTTP 客户端**: Axios 1.13.2 with custom wrapper

## 项目结构

```
src/
├── api/           # API 服务层（集中式 HTTP 请求）
├── assets/        # 静态资源（图片、样式等）
├── components/    # 可复用的 Vue 组件
├── config/        # 配置文件（siteConfig 等）
├── layouts/       # 布局组件（Main, CommonAside, CommonHeader, CommonFooter, CommonMenuTab）
├── router/        # Vue Router 配置（routes, index）
├── stores/        # Pinia stores（menuTabsStore, menuCollapseStore）
├── utils/         # 工具函数（request, auth, responseCode 等）
└── views/         # 页面组件
    ├── about/
    ├── department/
    ├── dictionary/
    ├── error/
    ├── home/
    ├── login/
    ├── login-log/
    ├── operation-log/
    └── user/
```

## HTTP 请求架构

项目使用位于 `src/utils/request.ts` 的自定义 Axios 封装：

- **基础 URL**: `/api`（开发环境代理解决跨域）
- **超时时间**: 5000ms
- **凭证**: 启用 (`withCredentials: true`)
- **Content Type**: `application/json`

**请求方法**:
- `GET<T>(url, params?, config?)`
- `POST<T>(url, data?, config?)`
- `PUT<T>(url, data?, config?)`
- `DELETE<T>(url, data?, config?)`

**自定义配置**:
- `isToken`: 设置为 `true` 可在请求头中包含 Bearer token
- Token 会自动从 `localStorage` 通过 `getAccessToken()` 附加

**响应处理**:
- 成功: `code` 在 [200, 400) 范围内
- 错误代码会触发自动清除 token 并重定向到登录页
- 403 状态码和 token 错误代码会导致登出

## 认证与授权

- Token 存储在 `localStorage` 中，key 为 `accessToken`
- 认证守卫在 `src/router/index.ts` 中检查 `requiresAuth` 元属性
- 带有 `requiresAuth: true` 的路由需要有效的 token
- 登录页面无需认证即可访问，但若已登录会重定向到首页
- Token 过期会自动重定向到登录页并显示错误提示

## 状态管理 (Pinia)

**可用的 Stores**:

1. **menuTabsStore** (`src/stores/menuTabsStore.ts`):
   - 管理 Tab 导航，使用 `pinia-plugin-persistedstate`
   - 方法: `addTab()`, `removeTab()`, `closeOtherTabs()`, `closeAllTabs()`
   - 持久化到 localStorage

2. **menuCollapseStore** (`src/stores/menuCollapseStore.ts`):
   - 控制侧边栏折叠状态
   - 持久化到 localStorage

所有 stores 使用 Composition API 风格的 `defineStore`。

## 路由

- 模式: `createWebHistory(import.meta.env.BASE_URL)`
- 路由定义在 `src/router/routes.ts`
- 页面标题根据路由 meta 标题自动设置，使用 `SITE_NAME` 前缀
- Tab 管理: 除 `/login` 外的所有路由会自动通过 menuTabsStore 添加到 tabs
- 受保护的路由使用 `meta: { requiresAuth: true }`

## 路径别名

- `@/` → `src/`（在 vite.config.ts 中配置）
- 所有导入应使用 `@` 前缀以保持路径简洁

## 代码风格

- **ESLint**: Flat config 格式，支持 Vue + TypeScript
  - 禁用多词组件名检查
  - 允许使用 console 进行调试
  - 配置了 Element Plus 全局变量
- **Prettier**: 禁用分号，2 空格缩进，单引号，100 字符行宽
- **SCSS**: 禁用 legacy-js-api 的弃用警告

## 开发注意事项

- 使用 `<script setup>` 语法编写 Vue 组件
- 全程使用 TypeScript 接口确保类型安全
- Element Plus 是主要的 UI 组件库
- 可使用 lodash-es 进行工具函数操作
- 使用 vuedraggable 实现拖拽功能
