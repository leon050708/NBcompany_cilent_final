package org.example.nbcompany.service;

import com.github.pagehelper.PageInfo;
import org.example.nbcompany.dao.NewsMapper;
import org.example.nbcompany.dao.SysCompanyMapper;
import org.example.nbcompany.dto.NewsDto.*;
import org.example.nbcompany.entity.BizNews;
import org.example.nbcompany.entity.SysCompany;
import org.example.nbcompany.entity.SysUser;
import org.example.nbcompany.service.impl.NewsServiceImpl;
import org.example.nbcompany.util.SpringContextHolder;
import org.example.nbcompany.util.UserContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @Mock
    private NewsMapper newsMapper;

    @Mock
    private SysCompanyMapper sysCompanyMapper;

    @Mock
    private ApplicationContext applicationContext;

    @InjectMocks
    private NewsServiceImpl newsService;

    private SysUser testUser;
    private BizNews testNews;
    private CreateNewsDto createNewsDto;
    private UpdateNewsDto updateNewsDto;
    private NewsQueryDto queryDto;
    private AuditNewsDto auditNewsDto;

    @BeforeEach
    void setUp() {
        testUser = new SysUser();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setNickname("测试用户");
        testUser.setUserType(1);
        testUser.setCompanyId(1L);
        testUser.setCompanyRole(1);

        testNews = new BizNews();
        testNews.setId(1L);
        testNews.setTitle("测试新闻");
        testNews.setContent("这是测试新闻内容");
        testNews.setAuthorId(1L);
        testNews.setAuthorName("测试用户");
        testNews.setCompanyId(1L);
        testNews.setStatus(1);
        testNews.setViewCount(10);
        testNews.setCreatedAt(LocalDateTime.now());
        testNews.setUpdatedAt(LocalDateTime.now());

        createNewsDto = new CreateNewsDto();
        createNewsDto.setTitle("新新闻");
        createNewsDto.setContent("新新闻内容");
        createNewsDto.setCoverImageUrl("http://example.com/image.jpg");

        updateNewsDto = new UpdateNewsDto();
        updateNewsDto.setTitle("更新新闻");
        updateNewsDto.setContent("更新新闻内容");

        queryDto = new NewsQueryDto();
        queryDto.setTitle("测试");
        queryDto.setStatus(1);

        auditNewsDto = new AuditNewsDto();
        auditNewsDto.setStatus(1);
    }

    @Test
    void testGetAllNews_Success() {
        // 准备
        List<BizNews> newsList = Arrays.asList(testNews);
        SysCompany company = new SysCompany();
        company.setCompanyName("测试公司");

        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class);
             MockedStatic<SpringContextHolder> springContextMock = mockStatic(SpringContextHolder.class)) {
            userContextMock.when(UserContext::getCurrentUser).thenReturn(testUser);
            userContextMock.when(UserContext::isPlatformAdmin).thenReturn(false);
            userContextMock.when(UserContext::isCompanyAdmin).thenReturn(false);
            springContextMock.when(() -> SpringContextHolder.getBean(SysCompanyMapper.class)).thenReturn(sysCompanyMapper);

            when(newsMapper.findList(any(NewsQueryDto.class))).thenReturn(newsList);
            when(sysCompanyMapper.selectById(1L)).thenReturn(company);

            // 执行
            PageInfo<NewsResponseDto> result = newsService.getAllNews(1, 10, queryDto);

            // 验证
            assertNotNull(result);
            assertEquals(1, result.getList().size());
            assertEquals("测试新闻", result.getList().get(0).getTitle());
        }
    }

    @Test
    void testGetAllNews_UserNotLoggedIn() {
        // 准备
        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUser).thenReturn(null);

            // 执行和验证
            SecurityException exception = assertThrows(SecurityException.class,
                () -> newsService.getAllNews(1, 10, queryDto));
            assertEquals("用户未登录", exception.getMessage());
        }
    }

    @Test
    void testGetAllNews_PlatformAdmin() {
        // 准备
        List<BizNews> newsList = Arrays.asList(testNews);
        SysCompany company = new SysCompany();
        company.setCompanyName("测试公司");

        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class);
             MockedStatic<SpringContextHolder> springContextMock = mockStatic(SpringContextHolder.class)) {
            userContextMock.when(UserContext::getCurrentUser).thenReturn(testUser);
            userContextMock.when(UserContext::isPlatformAdmin).thenReturn(true);
            springContextMock.when(() -> SpringContextHolder.getBean(SysCompanyMapper.class)).thenReturn(sysCompanyMapper);

            when(newsMapper.findList(any(NewsQueryDto.class))).thenReturn(newsList);
            when(sysCompanyMapper.selectById(1L)).thenReturn(company);

            // 执行
            PageInfo<NewsResponseDto> result = newsService.getAllNews(1, 10, queryDto);

            // 验证
            assertNotNull(result);
            assertEquals(1, result.getList().size());
        }
    }

    @Test
    void testCreateNews_Success() {
        // 准备
        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUser).thenReturn(testUser);
            userContextMock.when(UserContext::isPlatformAdmin).thenReturn(false);
            userContextMock.when(UserContext::isCompanyAdmin).thenReturn(true);
            when(newsMapper.insert(any(BizNews.class))).thenReturn(1);

            // 执行
            NewsResponseDto result = newsService.createNews(createNewsDto);

            // 验证
            assertNotNull(result);
            assertEquals("新新闻", result.getTitle());
            assertEquals("新新闻内容", result.getContent());
            assertEquals(0, result.getStatus()); // 企业管理员创建为待审核状态
            verify(newsMapper).insert(any(BizNews.class));
        }
    }

    @Test
    void testCreateNews_PlatformAdmin() {
        // 准备
        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUser).thenReturn(testUser);
            userContextMock.when(UserContext::isPlatformAdmin).thenReturn(true);
            when(newsMapper.insert(any(BizNews.class))).thenReturn(1);

            // 执行
            NewsResponseDto result = newsService.createNews(createNewsDto);

            // 验证
            assertNotNull(result);
            assertEquals(1, result.getStatus()); // 平台管理员创建直接发布
            verify(newsMapper).insert(any(BizNews.class));
        }
    }

    @Test
    void testCreateNews_UserNotLoggedIn() {
        // 准备
        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUser).thenReturn(null);

            // 执行和验证
            SecurityException exception = assertThrows(SecurityException.class,
                () -> newsService.createNews(createNewsDto));
            assertEquals("用户未登录，无法发布动态", exception.getMessage());
        }
    }

    @Test
    void testCreateNews_NoPermission() {
        // 准备
        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUser).thenReturn(testUser);
            userContextMock.when(UserContext::isPlatformAdmin).thenReturn(false);
            userContextMock.when(UserContext::isCompanyAdmin).thenReturn(false);

            // 执行和验证
            SecurityException exception = assertThrows(SecurityException.class,
                () -> newsService.createNews(createNewsDto));
            assertEquals("您没有权限发布动态", exception.getMessage());
        }
    }

    @Test
    void testCreateNews_WithHtmlSanitization() {
        // 准备
        createNewsDto.setContent("<script>alert('xss')</script><p>安全内容</p>");
        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUser).thenReturn(testUser);
            userContextMock.when(UserContext::isPlatformAdmin).thenReturn(true);
            when(newsMapper.insert(any(BizNews.class))).thenReturn(1);

            // 执行
            NewsResponseDto result = newsService.createNews(createNewsDto);

            // 验证
            assertNotNull(result);
            assertFalse(result.getContent().contains("<script>"));
            assertTrue(result.getContent().contains("<p>安全内容</p>"));
        }
    }

    @Test
    void testGetNewsDetail_Success() {
        // 准备
        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class);
             MockedStatic<SpringContextHolder> springContextMock = mockStatic(SpringContextHolder.class)) {
            userContextMock.when(UserContext::getCurrentUser).thenReturn(testUser);
            userContextMock.when(UserContext::getCurrentUserId).thenReturn(1L);
            springContextMock.when(() -> SpringContextHolder.getBean(SysCompanyMapper.class)).thenReturn(sysCompanyMapper);

            when(newsMapper.findById(1L)).thenReturn(testNews);
            when(newsMapper.update(any(BizNews.class))).thenReturn(1);

            // 执行
            NewsResponseDto result = newsService.getNewsDetail(1L);

            // 验证
            assertNotNull(result);
            assertEquals("测试新闻", result.getTitle());
            assertEquals(11, result.getViewCount()); // 观看次数+1
            verify(newsMapper).update(any(BizNews.class));
        }
    }

    @Test
    void testGetNewsDetail_NotFound() {
        // 准备
        when(newsMapper.findById(1L)).thenReturn(null);

        // 执行和验证
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> newsService.getNewsDetail(1L));
        assertEquals("动态不存在", exception.getMessage());
    }

    @Test
    void testGetNewsDetail_NoPermission() {
        // 准备
        testNews.setStatus(0); // 待审核状态
        testNews.setAuthorId(999L); // 不同作者

        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUser).thenReturn(testUser);
            userContextMock.when(UserContext::getCurrentUserId).thenReturn(1L);
            userContextMock.when(UserContext::isPlatformAdmin).thenReturn(false);
            userContextMock.when(UserContext::isCompanyAdmin).thenReturn(false);
            userContextMock.when(UserContext::getCurrentUserCompanyId).thenReturn(2L); // 不同公司

            when(newsMapper.findById(1L)).thenReturn(testNews);

            // 执行和验证
            SecurityException exception = assertThrows(SecurityException.class,
                () -> newsService.getNewsDetail(1L));
            assertEquals("您无权查看此动态", exception.getMessage());
        }
    }

    @Test
    void testUpdateNews_Success() {
        // 准备
        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class);
             MockedStatic<SpringContextHolder> springContextMock = mockStatic(SpringContextHolder.class)) {
            userContextMock.when(UserContext::getCurrentUser).thenReturn(testUser);
            userContextMock.when(UserContext::isPlatformAdmin).thenReturn(false);
            userContextMock.when(UserContext::isCompanyAdmin).thenReturn(true);
            userContextMock.when(UserContext::getCurrentUserCompanyId).thenReturn(1L);
            springContextMock.when(() -> SpringContextHolder.getBean(SysCompanyMapper.class)).thenReturn(sysCompanyMapper);

            when(newsMapper.findById(1L)).thenReturn(testNews);
            when(newsMapper.update(any(BizNews.class))).thenReturn(1);

            // 执行
            NewsResponseDto result = newsService.updateNews(1L, updateNewsDto);

            // 验证
            assertNotNull(result);
            assertEquals("更新新闻", result.getTitle());
            assertEquals("更新新闻内容", result.getContent());
            verify(newsMapper).update(any(BizNews.class));
        }
    }

    @Test
    void testUpdateNews_NotFound() {
        // 准备
        when(newsMapper.findById(1L)).thenReturn(null);

        // 执行和验证
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> newsService.updateNews(1L, updateNewsDto));
        assertEquals("动态不存在，无法更新", exception.getMessage());
    }

    @Test
    void testUpdateNews_NoPermission() {
        // 准备
        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUser).thenReturn(testUser);
            userContextMock.when(UserContext::isPlatformAdmin).thenReturn(false);
            userContextMock.when(UserContext::isCompanyAdmin).thenReturn(false);

            when(newsMapper.findById(1L)).thenReturn(testNews);

            // 执行和验证
            SecurityException exception = assertThrows(SecurityException.class,
                () -> newsService.updateNews(1L, updateNewsDto));
            assertEquals("您没有权限修改此动态", exception.getMessage());
        }
    }

    @Test
    void testDeleteNews_Success() {
        // 准备
        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUser).thenReturn(testUser);
            userContextMock.when(UserContext::isPlatformAdmin).thenReturn(false);
            userContextMock.when(UserContext::isCompanyAdmin).thenReturn(true);
            userContextMock.when(UserContext::getCurrentUserCompanyId).thenReturn(1L);

            when(newsMapper.findById(1L)).thenReturn(testNews);
            when(newsMapper.deleteById(1L)).thenReturn(1);

            // 执行
            assertDoesNotThrow(() -> newsService.deleteNews(1L));

            // 验证
            verify(newsMapper).deleteById(1L);
        }
    }

    @Test
    void testDeleteNews_NotFound() {
        // 准备
        when(newsMapper.findById(1L)).thenReturn(null);

        // 执行
        assertDoesNotThrow(() -> newsService.deleteNews(1L));

        // 验证 - 不存在的新闻不会抛出异常，直接返回
        verify(newsMapper, never()).deleteById(anyLong());
    }

    @Test
    void testDeleteNews_NoPermission() {
        // 准备
        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUser).thenReturn(testUser);
            userContextMock.when(UserContext::isPlatformAdmin).thenReturn(false);
            userContextMock.when(UserContext::isCompanyAdmin).thenReturn(false);

            when(newsMapper.findById(1L)).thenReturn(testNews);

            // 执行和验证
            SecurityException exception = assertThrows(SecurityException.class,
                () -> newsService.deleteNews(1L));
            assertEquals("您没有权限删除此动态", exception.getMessage());
        }
    }

    @Test
    void testAuditNews_Success() {
        // 准备
        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUser).thenReturn(testUser);
            userContextMock.when(UserContext::isPlatformAdmin).thenReturn(true);

            when(newsMapper.findById(1L)).thenReturn(testNews);
            when(newsMapper.update(any(BizNews.class))).thenReturn(1);

            // 执行
            assertDoesNotThrow(() -> newsService.auditNews(1L, auditNewsDto));

            // 验证
            verify(newsMapper).update(any(BizNews.class));
        }
    }

    @Test
    void testAuditNews_NoPermission() {
        // 准备
        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUser).thenReturn(testUser);
            userContextMock.when(UserContext::isPlatformAdmin).thenReturn(false);

            // 执行和验证
            SecurityException exception = assertThrows(SecurityException.class,
                () -> newsService.auditNews(1L, auditNewsDto));
            assertEquals("只有平台超级管理员才能审核动态", exception.getMessage());
        }
    }

    @Test
    void testAuditNews_NotFound() {
        // 准备
        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUser).thenReturn(testUser);
            userContextMock.when(UserContext::isPlatformAdmin).thenReturn(true);

            when(newsMapper.findById(1L)).thenReturn(null);

            // 执行和验证
            RuntimeException exception = assertThrows(RuntimeException.class,
                () -> newsService.auditNews(1L, auditNewsDto));
            assertEquals("动态不存在，无法审核", exception.getMessage());
        }
    }
} 