# Spring Security集成设计
## 背景

## 核心组件
Authentication：存储了认证信息，代表当前登录用户

* Authentication中是什么信息呢?
    * Principal：用户信息，没有认证时一般是用户名，认证后一般是用户对象

    * Credentials：用户凭证，一般是密码

    * Authorities：用户权限

SecurityContext：上下文对象，用来获取Authentication

SecurityContextHolder：上下文管理对象，用来在程序任何地方获取SecurityContext

## 重要点
UsernamePasswordAuthenticationFilter:负责处理我们在登陆页面填写了用户名密码后的登陆请求。入门案例的认证工作主要有它负责

ExceptionTranslationFilter： 处理过滤器链中抛出的任何AccessDeniedException和AuthenticationException

FilterSecurityInterceptor： 负责权限校验的过滤器

Authentication接口: 它的实现类，表示当前访问系统的用户，封装了用户相关信息

AuthenticationManager接口：定义了认证Authentication的方法

UserDetailsService接口：加载用户特定数据的核心接口。里面定义了一个根据用户名查询用户信息的方法

UserDetails接口：提供核心用户信息。通过UserDetailsService根据用户名获取处理的用户信息要封装成

UserDetails对象返回。然后将这些信息封装到Authentication对象中

## 实现
* 配置SecurityConfiguration

* UserDetails

* UserDetailsService UserDetailsServiceImpl

* JwtTokenUtil

* OncePerRequestFilter JwtAuthenticationTokenFilter