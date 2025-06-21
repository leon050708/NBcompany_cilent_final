# NBCompany 企业管理系统

## 项目概述

NBCompany是一个基于Spring Boot的企业管理系统，提供企业注册、用户管理、课程管理、会议管理、新闻动态等功能模块。系统采用JWT认证，支持多角色权限控制。

## 技术栈

- **后端**: Spring Boot, MyBatis, MySQL
- **认证**: JWT (JSON Web Token)
- **权限控制**: 基于角色的访问控制 (RBAC)
- **数据导出**: Excel导出功能

## 系统角色

1. **平台超级管理员** (`userType: 2`): 拥有所有权限
2. **企业管理员** (`companyRole: 2`): 管理本企业用户和内容
3. **普通用户** (`companyRole: 1`): 基础功能访问权限

---

## API接口文档

### 基础配置

- **基础URL**: `http://localhost:8080`
- **认证方式**: JWT Token (Bearer Token)
- **请求头**: `Authorization: Bearer {JWT_TOKEN}`

### 1. 认证接口 (`/api/v1/auth`)

#### 1.1 用户登录
```bash
POST /api/v1/auth/login
Content-Type: application/json

{
    "username": "admin",
    "password": "password"
}
```

**响应示例**:
```json
{
    "code": 200,
    "message": "登录成功",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        "userInfo": {
            "id": 1,
            "username": "admin",
            "nickname": "平台超级管理员",
            "userType": 2,
            "companyId": null,
            "companyName": null,
            "companyRole": null
        }
    }
}
```

#### 1.2 企业注册
```bash
POST /api/v1/auth/register/company
Content-Type: application/json

{
    "companyName": "新建科技公司",
    "contactPerson": "王小明",
    "contactPhone": "13912345678",
    "contactEmail": "wangxm@newtech.com"
}
```

#### 1.3 用户注册
```bash
POST /api/v1/auth/register/user
Content-Type: application/json

{
    "username": "newemployee",
    "password": "password123",
    "nickname": "新员工",
    "phoneNumber": "13312345678",
    "email": "newemployee@example.com",
    "gender": 1,
    "companyId": 1
}
```

### 2. 用户管理接口 (`/api/v1/user`)

#### 2.1 获取当前用户信息
```bash
GET /api/v1/user/profile
Authorization: Bearer {JWT_TOKEN}
```

#### 2.2 修改用户信息
```bash
PUT /api/v1/user/profile
Authorization: Bearer {JWT_TOKEN}
Content-Type: application/json

{
    "nickname": "新昵称",
    "phoneNumber": "13800000002",
    "email": "new.email@example.com",
    "gender": 1
}
```

#### 2.3 修改密码
```bash
PUT /api/v1/user/password
Authorization: Bearer {JWT_TOKEN}
Content-Type: application/json

{
    "oldPassword": "password123",
    "newPassword": "newPassword456"
}
```

### 3. 企业管理接口 (`/api/v1/companies`)

#### 3.1 获取企业列表
```bash
GET /api/v1/companies?keyword=科技&page=1&size=10
```

#### 3.2 审核企业状态 (管理员)
```bash
PUT /api/v1/admin/companies/{companyId}/status
Authorization: Bearer {ADMIN_TOKEN}
Content-Type: application/json

{
    "status": 1
}
```

### 4. 企业成员管理接口 (`/api/v1/company/{companyId}/members`)

#### 4.1 获取企业成员列表
```bash
GET /api/v1/company/{companyId}/members?page=1&size=10
Authorization: Bearer {COMPANY_ADMIN_TOKEN}
```

#### 4.2 创建企业成员
```bash
POST /api/v1/company/{companyId}/members
Authorization: Bearer {COMPANY_ADMIN_TOKEN}
Content-Type: application/json

{
    "username": "newmember",
    "password": "password123",
    "nickname": "新成员",
    "phoneNumber": "13700137000",
    "email": "new.member@newtech.com",
    "companyRole": 1
}
```

#### 4.3 修改成员角色
```bash
PUT /api/v1/company/{companyId}/members/{memberId}/role
Authorization: Bearer {COMPANY_ADMIN_TOKEN}
Content-Type: application/json

{
    "companyRole": 2
}
```

### 5. 课程管理接口 (`/api/courses`)

#### 5.1 创建课程
```bash
POST /api/courses
Authorization: Bearer {USER_TOKEN}
Content-Type: application/json

{
    "courseName": "Java基础教程",
    "coverImageUrl": "https://example.com/cover.jpg",
    "summary": "Java编程语言基础入门课程",
    "courseVideoUrl": "https://example.com/video.mp4",
    "sortOrder": 1
}
```

#### 5.2 获取课程列表
```bash
GET /api/courses?pageNum=1&pageSize=10&courseName=Java&status=1
Authorization: Bearer {USER_TOKEN}
```

#### 5.3 获取课程详情
```bash
GET /api/courses/{courseId}
Authorization: Bearer {USER_TOKEN}
```

#### 5.4 更新课程
```bash
PUT /api/courses/{courseId}
Authorization: Bearer {USER_TOKEN}
Content-Type: application/json

{
    "courseName": "Java进阶教程",
    "summary": "Java编程语言进阶课程"
}
```

#### 5.5 删除课程
```bash
DELETE /api/courses/{courseId}
Authorization: Bearer {USER_TOKEN}
```

#### 5.6 课程审核 (管理员)
```bash
POST /api/courses/audit
Authorization: Bearer {ADMIN_TOKEN}
Content-Type: application/json

{
    "id": 1,
    "status": 1,
    "auditRemark": "课程内容符合要求，审核通过"
}
```

#### 5.7 导出课程列表到Excel
```bash
GET /api/courses/export?courseName=Java&status=1
Authorization: Bearer {USER_TOKEN}
Accept: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
```

**权限说明**:
- **管理员用户**: 可以导出所有课程（任何状态）
- **普通用户**: 可以导出已发布的课程和自己上传的课程

### 6. 会议管理接口 (`/api/v1/meetings`)

#### 6.1 获取会议列表
```bash
GET /api/v1/meetings?meetingName=大会&page=1&size=10
```

#### 6.2 获取会议详情
```bash
GET /api/v1/meetings/{meetingId}
```

#### 6.3 创建会议
```bash
POST /api/v1/meetings
Authorization: Bearer {COMPANY_ADMIN_TOKEN}
Content-Type: application/json

{
    "meetingName": "季度技术分享会",
    "startTime": "2025-07-15T14:00:00",
    "endTime": "2025-07-15T16:00:00",
    "location": "公司A座一楼会议室",
    "organizer": "技术部",
    "content": "<p>本次分享会包含前端和后端两个主题。</p>"
}
```

#### 6.4 编辑会议
```bash
PUT /api/v1/meetings/{meetingId}
Authorization: Bearer {COMPANY_ADMIN_TOKEN}
Content-Type: application/json

{
    "meetingName": "季度技术分享会 - [已更新标题]",
    "location": "公司B座三楼报告厅",
    "organizer": "技术部 & 产品部"
}
```

#### 6.5 删除会议
```bash
DELETE /api/v1/meetings/{meetingId}
Authorization: Bearer {COMPANY_ADMIN_TOKEN}
```

#### 6.6 审核会议 (管理员)
```bash
PUT /api/v1/admin/meetings/{meetingId}/status
Authorization: Bearer {ADMIN_TOKEN}
Content-Type: application/json

{
    "status": 1
}
```

### 7. 新闻动态接口 (`/api/news`)

#### 7.1 获取新闻列表
```bash
GET /api/news?page=1&size=10&status=1
Authorization: Bearer {USER_TOKEN}
```

#### 7.2 创建新闻
```bash
POST /api/news
Authorization: Bearer {USER_TOKEN}
Content-Type: application/json

{
    "title": "行业新动态",
    "content": "这是新闻内容"
}
```

#### 7.3 更新新闻
```bash
PUT /api/news/{newsId}
Authorization: Bearer {USER_TOKEN}
Content-Type: application/json

{
    "title": "更新的新闻标题",
    "content": "更新的新闻内容"
}
```

#### 7.4 删除新闻
```bash
DELETE /api/news/{newsId}
Authorization: Bearer {USER_TOKEN}
```

#### 7.5 审核新闻 (管理员)
```bash
PUT /api/admin/news/{newsId}/status
Authorization: Bearer {ADMIN_TOKEN}
Content-Type: application/json

{
    "status": 1
}
```

---

## 权限测试报告

### 超级管理员权限测试

**测试账号**: `myadmin` (userType: 2)

**测试结果**:
- ✅ 可以直接发布"已发布"状态的动态
- ✅ 可以审核动态状态
- ✅ 可以删除任意动态
- ✅ 拥有所有模块的完整权限

### 企业管理员权限测试

**测试账号**: `realadmin` (companyRole: 2)

**测试结果**:
- ✅ 发布的动态初始状态为"待审核"
- ✅ 可以修改自己公司发布的动态
- ✅ 可以删除自己公司发布的动态
- ❌ 无法访问超级管理员审核接口
- ✅ 可以管理本企业成员

### 普通用户权限测试

**测试账号**: `test_normal_user` (companyRole: 1)

**测试结果**:
- ❌ 无法发布动态
- ❌ 无法修改动态
- ❌ 无法删除动态
- ❌ 无法查看待审核动态
- ✅ 可以查看已发布的公开内容

---

## JWT Token使用指南

### 获取Token
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"password"}'
```

### 在请求中使用Token
```bash
curl -X GET http://localhost:8080/api/v1/user/profile \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Postman环境配置
1. 创建环境变量：
   - `BASE_URL`: `http://localhost:8080`
   - `TOKEN`: 登录后获取的JWT Token

2. 在请求头中添加：
   ```
   Authorization: Bearer {{TOKEN}}
   Content-Type: application/json
   ```

### Token安全注意事项
- Token有效期为24小时
- 不要将Token分享给不相关人员
- 定期更换Token
- 不要在代码中硬编码Token

---

## Excel导出功能

### 功能说明
课程管理模块支持将课程列表导出为Excel文件，方便用户进行数据分析和备份。

### API接口
```bash
GET /api/courses/export
Authorization: Bearer {token}
Accept: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
```

### 查询参数
- `courseName`: 课程名称（模糊查询）
- `status`: 课程状态（0:待审核, 1:已发布, 2:审核未通过）
- `authorName`: 作者名称（模糊查询）
- `companyName`: 企业名称（模糊查询）

### 权限控制
- **普通用户**: 只能导出已发布的课程（status=1）
- **企业管理员**: 可以导出本企业的所有课程
- **平台管理员**: 可以导出所有课程

### 测试方法

#### 使用Postman测试
1. 选择 `GET` 方法
2. URL: `http://localhost:8080/api/courses/export`
3. Headers: `Authorization: Bearer {your_jwt_token}`
4. 点击 "Send and Download" 下载文件

#### 使用curl测试
```bash
curl -X GET "http://localhost:8080/api/courses/export" \
  -H "Authorization: Bearer {your_jwt_token}" \
  --output "课程列表.xlsx"
```

### Excel文件格式
导出的Excel文件包含以下列：
- 课程ID
- 课程名称
- 课程封面
- 课程简介
- 作者名称
- 企业名称
- 审核状态
- 状态描述
- 观看次数
- 创建时间

---

## 错误处理

### 常见错误码
- **401 Unauthorized**: Token过期或无效
- **403 Forbidden**: 权限不足
- **400 Bad Request**: 请求参数错误
- **500 Internal Server Error**: 服务器内部错误

### 错误响应格式
```json
{
    "code": 400,
    "message": "错误描述",
    "data": null
}
```

---

## 测试数据准备

### 创建测试用户
```sql
-- 平台超级管理员
INSERT INTO sys_user (username, password, user_type) VALUES
('admin', '$2a$10$fL3n3v9v5b.npL/E/e4BGe.xRz.w6A7D9E0b6A5A4A3A2A1A0A', 2);

-- 企业管理员
INSERT INTO sys_user (username, password, user_type, company_role, company_id) VALUES
('zhangsan', 'password', 1, 2, 1);

-- 普通用户
INSERT INTO sys_user (username, password, user_type, company_role, company_id) VALUES
('testuser', 'password', 1, 1, 1);
```

### 创建测试企业
```sql
INSERT INTO sys_company (company_name, contact_person, contact_phone, contact_email, status) VALUES
('数智未来科技有限公司', '张三', '13800000001', 'zhangsan@tech.com', 1),
('绿色能源集团', '李四', '13800000002', 'lisi@energy.com', 1);
```

---

## 部署说明

### 环境要求
- JDK 8+
- MySQL 5.7+
- Maven 3.6+

### 启动步骤
1. 克隆项目到本地
2. 配置数据库连接（application.properties）
3. 执行数据库初始化脚本（test.sql）
4. 运行 `mvn spring-boot:run`
5. 访问 `http://localhost:8080`

### 配置说明
主要配置文件：`src/main/resources/application.properties`
- 数据库连接配置
- JWT密钥配置
- 文件上传配置

---

## 开发指南

### 项目结构
```
src/main/java/org/example/nbcompany/
├── annotation/          # 自定义注解
├── config/             # 配置类
├── controller/         # 控制器
├── dao/               # 数据访问层
├── dto/               # 数据传输对象
├── entity/            # 实体类
├── filter/            # 过滤器
├── security/          # 安全相关
├── service/           # 服务层
└── util/              # 工具类
```

### 添加新功能
1. 创建实体类（entity包）
2. 创建DTO类（dto包）
3. 创建Mapper接口（dao包）
4. 创建Service接口和实现（service包）
5. 创建Controller（controller包）
6. 添加权限控制注解
7. 编写测试用例

---

## 常见问题

### Q: Token过期了怎么办？
A: 重新调用登录接口获取新的token

### Q: 为什么有些API访问不了？
A: 检查用户权限，某些API需要特定的用户角色

### Q: 如何知道token是否有效？
A: 尝试调用一个简单的API（如获取用户信息）来验证

### Q: Excel导出显示乱码怎么办？
A: 使用Postman的"Send and Download"功能，不要使用普通的"Send"按钮

---

## 更新日志

### v1.0.0 (2024-01-01)
- 初始版本发布
- 实现基础的用户管理功能
- 实现企业注册和管理功能
- 实现课程管理功能
- 实现会议管理功能
- 实现新闻动态功能
- 实现Excel导出功能

---

## 联系方式

如有问题或建议，请联系开发团队。