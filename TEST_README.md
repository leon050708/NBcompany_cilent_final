# NBCompany项目测试指南

## 📋 概述

本项目已配置完整的JUnit测试框架和JaCoCo代码覆盖率统计工具。通过编写全面的单元测试，确保代码质量和功能正确性。

## 🚀 快速开始

### 1. 运行所有测试
```bash
# 使用脚本（推荐）
./run-tests.sh

# 或手动执行
./mvnw clean test jacoco:report
```

### 2. 查看覆盖率报告
```bash
# 在浏览器中打开覆盖率报告
open target/site/jacoco/index.html
```

## 📊 当前测试状态

### ✅ 已完成的测试
- **UserServiceTest**: 12个测试用例，覆盖用户管理核心功能
- **JwtUtilTest**: 6个测试用例，覆盖JWT工具类功能
- **UserControllerTest**: 5个测试用例，覆盖用户API接口
- **NbCompanyApplicationTests**: 1个测试用例，应用启动测试

### 📈 覆盖率统计
- **指令覆盖率**: 14% (735/4,921)
- **行覆盖率**: 12% (143/1,169)
- **方法覆盖率**: 24% (55/226)
- **类覆盖率**: 87% (33/38)

## 🧪 测试文件结构

```
src/test/java/org/example/nbcompany/
├── service/
│   └── UserServiceTest.java          # 用户服务测试
├── controller/
│   └── UserControllerTest.java       # 用户控制器测试
├── util/
│   └── JwtUtilTest.java             # JWT工具类测试
└── NbCompanyApplicationTests.java    # 应用启动测试
```

## 🔧 技术栈

- **测试框架**: JUnit 5
- **Mock框架**: Mockito
- **覆盖率工具**: JaCoCo 0.8.11
- **构建工具**: Maven
- **Java版本**: 17

## 📝 编写新测试

### 1. 服务层测试模板
```java
@ExtendWith(MockitoExtension.class)
class YourServiceTest {

    @Mock
    private YourMapper mapper;

    @InjectMocks
    private YourServiceImpl service;

    @Test
    void testMethodName_Success() {
        // 准备测试数据
        when(mapper.someMethod()).thenReturn(expectedResult);

        // 执行测试
        Result result = service.methodName();

        // 验证结果
        assertNotNull(result);
        assertEquals(expectedValue, result.getValue());
    }
}
```

### 2. 控制器测试模板
```java
@ExtendWith(MockitoExtension.class)
class YourControllerTest {

    @Mock
    private YourService service;

    @InjectMocks
    private YourController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testEndpoint_Success() throws Exception {
        mockMvc.perform(get("/api/endpoint"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }
}
```

## 🎯 测试最佳实践

### 1. 测试命名规范
- 使用 `testMethodName_Scenario` 格式
- 例如: `testLogin_Success`, `testLogin_UserNotFound`

### 2. 测试结构 (AAA模式)
```java
@Test
void testMethodName_Scenario() {
    // Arrange (准备)
    when(mock.method()).thenReturn(value);

    // Act (执行)
    Result result = service.method();

    // Assert (验证)
    assertNotNull(result);
    assertEquals(expected, result.getValue());
}
```

### 3. Mock使用原则
- 只Mock外部依赖（数据库、外部API等）
- 不Mock被测试类本身
- 使用 `@InjectMocks` 自动注入Mock对象

### 4. 异常测试
```java
@Test
void testMethodName_ThrowsException() {
    // 准备
    when(mock.method()).thenThrow(new RuntimeException("Error"));

    // 执行和验证
    RuntimeException exception = assertThrows(RuntimeException.class, 
        () -> service.method());
    assertEquals("Error", exception.getMessage());
}
```

## 📈 覆盖率目标

| 模块 | 当前覆盖率 | 目标覆盖率 | 优先级 |
|------|------------|------------|--------|
| UserService | 45% | 80% | 高 |
| CourseService | 0.4% | 80% | 高 |
| MeetingService | 0.7% | 80% | 高 |
| NewsService | 0.9% | 80% | 高 |
| CompanyService | 2.4% | 80% | 中 |
| 控制器层 | 7% | 70% | 中 |
| 工具类 | 36% | 90% | 低 |

## 🚨 常见问题

### 1. 测试失败
```bash
# 查看详细错误信息
./mvnw test -X

# 运行单个测试类
./mvnw test -Dtest=UserServiceTest

# 运行单个测试方法
./mvnw test -Dtest=UserServiceTest#testLogin_Success
```

### 2. 覆盖率报告未生成
```bash
# 确保先运行测试
./mvnw test

# 然后生成报告
./mvnw jacoco:report
```

### 3. Mock对象问题
- 检查是否正确使用 `@Mock` 和 `@InjectMocks`
- 确保Mock方法调用参数匹配
- 使用 `any()` 匹配任意参数

## 📚 相关文档

- [JUnit 5 官方文档](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito 官方文档](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [JaCoCo 官方文档](https://www.jacoco.org/jacoco/trunk/doc/)
- [Spring Boot 测试指南](https://spring.io/guides/gs/testing-web/)

## 🤝 贡献指南

1. 为新功能编写对应的测试用例
2. 确保测试覆盖率不低于80%
3. 遵循测试命名和结构规范
4. 提交前运行所有测试确保通过

---

**最后更新**: 2025-07-10  
**维护者**: AI Assistant  
**项目版本**: 0.0.1-SNAPSHOT 