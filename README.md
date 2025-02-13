<h1>MOOK</h1>
<h3>本项目是一个基于SpringBoot和Vue3的现代化课程管理系统，旨在为用户和管理员提供便捷的课程管理与学习体验。系统采用前后端分离架构，前端分为用户端和管理端两个模块。用户端支持课程浏览、评论、点赞、支付及视频观看等功能；管理端则提供用户管理、课程管理、章节管理、角色权限配置等后台操作。后端基于SpringBoot框架，实现了登录鉴权、Token验证、路由守卫、拦截器、动态菜单等核心功能，确保系统的安全性与灵活性。通过模块化设计和现代化的技术栈，项目具备良好的扩展性和维护性，能够满足多样化的课程管理需求。</h3>

### 特性

- **最流行技术栈**：基于 Java 和 SpringBoot 高性能框架，结合 Vue3 和 Element 等前沿技术进行开发
- **代码规范**：项目内置丰富的规范插件，确保代码质量和一致性，有效提高团队协作效率。
- **动态路由**：后端动态路由，结合 RBAC（Role-Based Access Control）权限模型，提供精细的菜单路由控制。
- **JWT鉴权**：使用 JSON Web Token（JWT）进行身份验证和授权，增强应用的安全性。
- **细粒度权限控制**：实现按钮和接口级别的权限控制，确保不同用户或角色在界面操作和接口访问时具有不同的权限限制。



### 项目功能

**用户端功能：**

- **课程浏览与查询**：支持多条件查询课程，查看课程详情。
- **评论与互动**：用户可对课程进行评论、点赞或取消点赞。
- **支付功能（拓展）**：支持课程购买与支付。
- **视频学习（拓展）**：用户可观看课程视频。
- **个人信息管理**：修改头像、查看个人资料。

**管理端功能：**

- **用户管理**：用户列表展示、多条件查询、重置密码、启禁用用户、添加用户、修改用户角色。
- **课程管理**：课程列表展示、多条件查询、新增课程、修改课程状态、编辑课程详情、分页查询。
- **章节管理**：章节查询、新增章节、修改章节、章节重排序（拓展）。
- **角色与权限管理**：角色添加、为角色配置菜单、动态菜单实现。
- **数据统计与分析**：支持分页查询与数据展示。

**通用功能：**

- **登录与鉴权**：支持验证码登录、Token鉴权、路由守卫与拦截器。
- **头像管理**：用户头像上传与显示。
- **Axios封装**：统一接口请求管理，提升开发效率。
- **路由与导航**：实现登录跳转、主页布局与导航。




### 快速开始


### 本地启动

#### 后端

启动项目需要以下环境：

- Java 17+
- Maven 3.8+

1. 克隆项目

```sh
git clone https://github.com/icebro7/mooc.git
```

2. 安装依赖

```sh
mvn clean install
```

3. 启动服务

```sh
mvn spring-boot:run
```

服务现在应该正在运行，访问接口文档：`http://localhost:8080/swagger-ui.html`

#### 前端

启动项目需要以下环境：

- Node.js v18.8.0+
- npm

1. 进入前端目录

```sh
cd mook/frontend
```

2. 安装依赖

```sh
npm i -g npm # 已安装可忽略
```

3. 启动

```sh
npm run serve
```
