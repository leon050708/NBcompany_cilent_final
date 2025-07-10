package org.example.nbcompany.service;

import com.github.pagehelper.PageInfo;
import org.example.nbcompany.dao.BizCourseDao;
import org.example.nbcompany.dao.SysCompanyMapper;
import org.example.nbcompany.dao.SysUserMapper;
import org.example.nbcompany.dto.CourseDto.*;
import org.example.nbcompany.entity.BizCourse;
import org.example.nbcompany.entity.SysCompany;
import org.example.nbcompany.entity.SysUser;
import org.example.nbcompany.service.impl.CourseServiceImpl;
import org.example.nbcompany.util.UserContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private BizCourseDao bizCourseDao;

    @Mock
    private SysCompanyMapper sysCompanyMapper;

    @Mock
    private SysUserMapper sysUserMapper;

    @InjectMocks
    private CourseServiceImpl courseService;

    private SysUser testUser;
    private BizCourse testCourse;
    private CourseCreateDTO createDTO;
    private CourseUpdateDTO updateDTO;
    private CourseQueryDTO queryDTO;

    @BeforeEach
    void setUp() {
        testUser = new SysUser();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setNickname("测试用户");
        testUser.setUserType(1);
        testUser.setCompanyId(1L);
        testUser.setCompanyRole(1);

        testCourse = new BizCourse();
        testCourse.setId(1L);
        testCourse.setCourseName("测试课程");
        testCourse.setSummary("这是一个测试课程");
        testCourse.setAuthorName("张老师");
        testCourse.setCourseVideoUrl("http://example.com/video.mp4");
        testCourse.setStatus(1);
        testCourse.setCompanyId(1L);
        testCourse.setAuthorId(1L);
        testCourse.setViewCount(10);
        testCourse.setCreatedAt(LocalDateTime.now());
        testCourse.setUpdatedAt(LocalDateTime.now());

        createDTO = new CourseCreateDTO();
        createDTO.setCourseName("新课程");
        createDTO.setSummary("新课程描述");
        createDTO.setCourseVideoUrl("http://example.com/new-video.mp4");
        createDTO.setStatus(0);

        updateDTO = new CourseUpdateDTO();
        updateDTO.setCourseName("更新课程");
        updateDTO.setSummary("更新课程描述");
        updateDTO.setCourseVideoUrl("http://example.com/updated-video.mp4");

        queryDTO = new CourseQueryDTO();
        queryDTO.setPageNum(1);
        queryDTO.setPageSize(10);
    }

    @Test
    void testCreateCourse_Success() {
        // 准备
        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUserId).thenReturn(1L);
            when(sysUserMapper.selectById(1L)).thenReturn(testUser);
            when(bizCourseDao.insert(any(BizCourse.class))).thenReturn(1);

            // 执行
            CourseDetailDTO result = courseService.createCourse(createDTO);

            // 验证
            assertNotNull(result);
            assertEquals("新课程", result.getCourseName());
            assertEquals("新课程描述", result.getSummary());
            verify(bizCourseDao).insert(any(BizCourse.class));
        }
    }

    @Test
    void testCreateCourse_UserNotInCompany() {
        // 准备
        testUser.setCompanyId(null);
        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUserId).thenReturn(1L);
            when(sysUserMapper.selectById(1L)).thenReturn(testUser);

            // 执行和验证
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> courseService.createCourse(createDTO));
            assertEquals("用户不属于任何企业，无法创建课程", exception.getMessage());
        }
    }

    @Test
    void testGetCourseById_Success() {
        // 准备
        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUserId).thenReturn(1L);
            when(bizCourseDao.findById(1L)).thenReturn(testCourse);
            when(sysUserMapper.selectById(1L)).thenReturn(testUser);

            // 执行
            CourseDetailDTO result = courseService.getCourseById(1L);

            // 验证
            assertNotNull(result);
            assertEquals("测试课程", result.getCourseName());
            assertEquals("张老师", result.getAuthorName());
            verify(bizCourseDao, atLeastOnce()).findById(1L);
        }
    }

    @Test
    void testGetCourseById_NotFound() {
        // 准备
        when(bizCourseDao.findById(1L)).thenReturn(null);

        // 执行
        CourseDetailDTO result = courseService.getCourseById(1L);

        // 验证
        assertNull(result);
    }

    @Test
    void testGetCourseById_NoPermission() {
        // 准备
        testCourse.setStatus(0); // 待审核状态
        testUser.setUserType(1); // 普通用户
        testUser.setCompanyId(2L); // 不同公司
        testCourse.setAuthorId(999L); // 不同作者

        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUserId).thenReturn(1L);
            when(bizCourseDao.findById(1L)).thenReturn(testCourse);
            when(sysUserMapper.selectById(1L)).thenReturn(testUser);

            // 执行和验证
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> courseService.getCourseById(1L));
            assertEquals("无权限访问此课程", exception.getMessage());
        }
    }

    @Test
    void testGetCourseList_Success() {
        // 准备
        List<BizCourse> courses = Arrays.asList(testCourse);
        SysCompany company = new SysCompany();
        company.setCompanyName("测试公司");
        
        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUserId).thenReturn(1L);
            when(sysUserMapper.selectById(1L)).thenReturn(testUser);
            when(bizCourseDao.findByQuery(any(CourseQueryDTO.class))).thenReturn(courses);
            when(sysCompanyMapper.selectById(1L)).thenReturn(company);

            // 执行
            PageInfo<CourseListItemDTO> result = courseService.getCourseList(queryDTO);

            // 验证
            assertNotNull(result);
            assertEquals(1, result.getList().size());
            assertEquals("测试课程", result.getList().get(0).getCourseName());
        }
    }

    @Test
    void testGetCourseList_WithNullQuery() {
        // 准备
        List<BizCourse> courses = Arrays.asList(testCourse);
        SysCompany company = new SysCompany();
        company.setCompanyName("测试公司");
        
        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUserId).thenReturn(1L);
            when(sysUserMapper.selectById(1L)).thenReturn(testUser);
            when(bizCourseDao.findByQuery(any(CourseQueryDTO.class))).thenReturn(courses);
            when(sysCompanyMapper.selectById(1L)).thenReturn(company);

            // 执行
            PageInfo<CourseListItemDTO> result = courseService.getCourseList(null);

            // 验证
            assertNotNull(result);
            assertEquals(1, result.getList().size());
        }
    }

    @Test
    void testUpdateCourse_Success() {
        // 准备
        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUserId).thenReturn(1L);
            when(bizCourseDao.findById(1L)).thenReturn(testCourse);
            when(sysUserMapper.selectById(1L)).thenReturn(testUser);
            when(bizCourseDao.update(any(BizCourse.class))).thenReturn(1);

            // 执行
            CourseDetailDTO result = courseService.updateCourse(1L, updateDTO);

            // 验证
            assertNotNull(result);
            assertEquals("更新课程", result.getCourseName());
            verify(bizCourseDao).update(any(BizCourse.class));
        }
    }

    @Test
    void testUpdateCourse_NotFound() {
        // 准备
        when(bizCourseDao.findById(1L)).thenReturn(null);

        // 执行
        CourseDetailDTO result = courseService.updateCourse(1L, updateDTO);

        // 验证
        assertNull(result);
    }

    @Test
    void testUpdateCourse_NoPermission() {
        // 准备
        testCourse.setAuthorId(999L); // 不同作者
        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUserId).thenReturn(1L);
            when(bizCourseDao.findById(1L)).thenReturn(testCourse);
            when(sysUserMapper.selectById(1L)).thenReturn(testUser);

            // 执行和验证
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> courseService.updateCourse(1L, updateDTO));
            assertEquals("无权限更新此课程", exception.getMessage());
        }
    }

    @Test
    void testDeleteCourse_Success() {
        // 准备
        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUserId).thenReturn(1L);
            when(bizCourseDao.findById(1L)).thenReturn(testCourse);
            when(sysUserMapper.selectById(1L)).thenReturn(testUser);
            when(bizCourseDao.deleteById(1L)).thenReturn(1);

            // 执行
            assertDoesNotThrow(() -> courseService.deleteCourse(1L));

            // 验证
            verify(bizCourseDao).deleteById(1L);
        }
    }

    @Test
    void testDeleteCourse_NotFound() {
        // 准备
        when(bizCourseDao.findById(1L)).thenReturn(null);

        // 执行和验证
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> courseService.deleteCourse(1L));
        assertEquals("课程不存在", exception.getMessage());
    }

    @Test
    void testDeleteCourse_NoPermission() {
        // 准备
        testCourse.setAuthorId(999L); // 不同作者
        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUserId).thenReturn(1L);
            when(bizCourseDao.findById(1L)).thenReturn(testCourse);
            when(sysUserMapper.selectById(1L)).thenReturn(testUser);

            // 执行和验证
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> courseService.deleteCourse(1L));
            assertEquals("无权限删除此课程", exception.getMessage());
        }
    }

    @Test
    void testAuditCourse_Success() {
        // 准备
        CourseAuditDTO auditDTO = new CourseAuditDTO();
        auditDTO.setId(1L);
        auditDTO.setStatus(1);

        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUserId).thenReturn(1L);
            userContextMock.when(UserContext::isPlatformAdmin).thenReturn(true); // 平台管理员
            when(bizCourseDao.findById(1L)).thenReturn(testCourse);
            when(bizCourseDao.update(any(BizCourse.class))).thenReturn(1);

            // 执行
            CourseDetailDTO result = courseService.auditCourse(auditDTO);

            // 验证
            assertNotNull(result);
            assertEquals(1, result.getStatus());
            verify(bizCourseDao).update(any(BizCourse.class));
        }
    }

    @Test
    void testAuditCourse_NotFound() {
        // 准备
        CourseAuditDTO auditDTO = new CourseAuditDTO();
        auditDTO.setId(1L);
        auditDTO.setStatus(1); // 设置有效状态
        when(bizCourseDao.findById(1L)).thenReturn(null);

        // 执行和验证
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> courseService.auditCourse(auditDTO));
        assertEquals("课程不存在", exception.getMessage());
    }

    @Test
    void testIncrementViewCount_Success() {
        // 准备
        testCourse.setStatus(1); // 已发布状态
        testCourse.setAuthorId(999L); // 不是当前用户创建的
        testUser.setUserType(1); // 不是平台管理员
        
        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUserId).thenReturn(1L);
            when(bizCourseDao.findById(1L)).thenReturn(testCourse);
            when(sysUserMapper.selectById(1L)).thenReturn(testUser);
            when(bizCourseDao.incrementViewCount(1L)).thenReturn(1);

            // 执行
            boolean result = courseService.incrementViewCount(1L);

            // 验证
            assertTrue(result);
            verify(bizCourseDao).incrementViewCount(1L);
        }
    }

    @Test
    void testIncrementViewCount_Failed_CourseNotFound() {
        // 准备
        when(bizCourseDao.findById(1L)).thenReturn(null);

        // 执行
        boolean result = courseService.incrementViewCount(1L);

        // 验证
        assertFalse(result);
    }

    @Test
    void testIncrementViewCount_Failed_NotPublished() {
        // 准备
        testCourse.setStatus(0); // 待审核状态
        
        when(bizCourseDao.findById(1L)).thenReturn(testCourse);

        // 执行
        boolean result = courseService.incrementViewCount(1L);

        // 验证
        assertFalse(result);
    }

    @Test
    void testIncrementViewCount_Failed_AuthorViewing() {
        // 准备
        testCourse.setStatus(1); // 已发布状态
        testCourse.setAuthorId(1L); // 作者本人查看
        
        try (MockedStatic<UserContext> userContextMock = mockStatic(UserContext.class)) {
            userContextMock.when(UserContext::getCurrentUserId).thenReturn(1L);
            when(bizCourseDao.findById(1L)).thenReturn(testCourse);
            when(sysUserMapper.selectById(1L)).thenReturn(testUser);

            // 执行
            boolean result = courseService.incrementViewCount(1L);

            // 验证
            assertFalse(result);
        }
    }
} 