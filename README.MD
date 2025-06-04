# 📚 基于微服务的图书管理系统

本项目是一个基于 **Spring Cloud 微服务架构** 的图书管理系统，旨在实现用户数据及其图书列表的管理。系统结构模块化，具有良好的可扩展性和可维护性。

## 🚀 项目结构

系统包含以下几个微服务：

1. **图书服务（Book Service）**
   - 管理与图书相关的信息。
   - 提供添加图书、获取图书详情等操作。

2. **用户-图书服务（User-Books Service）**
   - 管理用户与图书之间的关系。
   - 聚合用户和图书服务的数据。
   - 使用 MongoDB 存储用户与图书的映射，示例格式如下：

```txt
json
{
  "userId": "user1",
  "books": [
      bookId1,bookId2,bookId3,
      ... 
  ]
}
```

3. **API 网关（API Gateway）**
   - 作为所有客户端请求的统一入口。
   - 路由请求至相应微服务。
   - 提供负载均衡、鉴权（可选）、监控等能力。

4. **私人生活服务（Private Life Service）** *(计划中)*
   - 未来用于管理用户的私人生活相关数据。

### 🖥️ 前端（React）


本系统的前端使用 React 构建，实现与用户的图形交互界面，通过 API Gateway 与后端通信。

### 🔧 功能概览
- **用户注册与登录**
- **查看、添加、删除用户图书**
- **浏览全部图书信息**
- **响应式界面设计**


## 📂 项目目录结构
```txt
reactjs/
├── public/
├── src/
│   ├── components/       # 通用组件（如 Header）
│   ├── pages/            # 页面组件（如 Home、UserBooksPage）
│   ├── styles/           # 样式组件（.css文件）
│   ├── App.js
│   └── index.js
├── package.json
└── README.md
project-root/
├── reactjs/
    ├── public/
    ├── src/
    │   ├── components/       # 通用组件（如 Header）
    │   ├── pages/            # 页面组件（如 Home、UserBooksPage）
    │   ├── styles/           # 样式组件（.css文件）
    │   ├── App.js
    │   └── index.js
    ├── package.json
├── user-service/
├── book-service/
├── user-books-service/
├── api-gateway/
└── README.md
```
## 🛠️ 技术栈

- **Java 18**
- **Spring Boot**
- **Spring Cloud（Eureka）**
- **Mysql**（用于存储用户和图书数据）
- **MongoDB**（用于存储用户-图书数据）
- **Spring Data MongoDB**
- **Spring Gateway**
- **Spring Security(用户认证与授权)**
- **Maven**
- **React（前端）**

## 🧪 启动项目

1. **启动 Eureka 注册中心**
2. **分别启动各个服务**
   - `user-service`
   - `book-service`
   - `user-books-service`
   - `api-gateway`
3. 使用React(npm start)通过网关访问服务。

## ✅运行结果特点

### 主页面

![](/image/image.png)

### 查看书籍信息

![](/image/image2.png)

## 📌 未来改进方向

- 增加用户服务（User Service），管理与用户相关的信息及提供 RESTful 接口以创建、获取和管理用户数据。
- 集成私人生活服务

## 🤝 贡献方式

欢迎提交 Pull Request！如果有重大修改，请先提交 issue 进行讨论。

## 📝 开源协议

本项目采用 MIT 开源协议，详情请参阅 [LICENSE](LICENSE) 文件。


