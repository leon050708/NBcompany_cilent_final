package org.example.nbcompany.service;

import org.example.nbcompany.dao.SysCompanyMapper;
import org.example.nbcompany.dto.request.CompanyRegisterRequest;
import org.example.nbcompany.dto.response.CompanyRegisterResponse;
import org.example.nbcompany.dto.response.PageResponse;
import org.example.nbcompany.entity.SysCompany;
import org.example.nbcompany.service.impl.CompanyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    private SysCompanyMapper companyMapper;

    @InjectMocks
    private CompanyServiceImpl companyService;

    private CompanyRegisterRequest registerRequest;
    private SysCompany testCompany;

    @BeforeEach
    void setUp() {
        registerRequest = new CompanyRegisterRequest();
        registerRequest.setCompanyName("测试公司");
        registerRequest.setContactPerson("张三");
        registerRequest.setContactPhone("13800138000");
        registerRequest.setContactEmail("test@example.com");

        testCompany = new SysCompany();
        testCompany.setId(1L);
        testCompany.setCompanyName("测试公司");
        testCompany.setContactPerson("张三");
        testCompany.setContactPhone("13800138000");
        testCompany.setContactEmail("test@example.com");
        testCompany.setStatus(0);
        testCompany.setCreatedAt(LocalDateTime.now());
        testCompany.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void testRegister_Success() {
        // 准备
        doNothing().when(companyMapper).insert(any(SysCompany.class));

        // 执行
        CompanyRegisterResponse result = companyService.register(registerRequest);

        // 验证
        assertNotNull(result);
        assertEquals("测试公司", result.getCompanyName());
        verify(companyMapper).insert(any(SysCompany.class));
    }

    @Test
    void testRegister_WithNullValues() {
        // 准备
        registerRequest.setContactEmail(null);
        registerRequest.setContactPhone(null);
        doNothing().when(companyMapper).insert(any(SysCompany.class));

        // 执行
        CompanyRegisterResponse result = companyService.register(registerRequest);

        // 验证
        assertNotNull(result);
        assertEquals("测试公司", result.getCompanyName());
        verify(companyMapper).insert(any(SysCompany.class));
    }

    @Test
    void testListCompanies_Success() {
        // 准备
        List<SysCompany> companies = Arrays.asList(testCompany);
        when(companyMapper.selectByKeyword(anyString(), anyInt(), anyInt())).thenReturn(companies);
        when(companyMapper.countByKeyword(anyString())).thenReturn(1L);

        // 执行
        PageResponse<SysCompany> result = companyService.listCompanies("测试", 1, 10);

        // 验证
        assertNotNull(result);
        assertEquals(1, result.getRecords().size());
        assertEquals("测试公司", result.getRecords().get(0).getCompanyName());
        assertEquals(1, result.getTotal());
        assertEquals(1, result.getCurrent());
        assertEquals(1, result.getPages());
    }

    @Test
    void testListCompanies_EmptyResult() {
        // 准备
        when(companyMapper.selectByKeyword(anyString(), anyInt(), anyInt())).thenReturn(Arrays.asList());
        when(companyMapper.countByKeyword(anyString())).thenReturn(0L);

        // 执行
        PageResponse<SysCompany> result = companyService.listCompanies("不存在", 1, 10);

        // 验证
        assertNotNull(result);
        assertEquals(0, result.getRecords().size());
        assertEquals(0, result.getTotal());
        assertEquals(1, result.getCurrent());
        assertEquals(0, result.getPages());
    }

    @Test
    void testListCompanies_WithNullKeyword() {
        // 准备
        List<SysCompany> companies = Arrays.asList(testCompany);
        when(companyMapper.selectByKeyword(eq(null), anyInt(), anyInt())).thenReturn(companies);
        when(companyMapper.countByKeyword(eq(null))).thenReturn(1L);

        // 执行
        PageResponse<SysCompany> result = companyService.listCompanies(null, 1, 10);

        // 验证
        assertNotNull(result);
        assertEquals(1, result.getRecords().size());
        assertEquals(1, result.getTotal());
    }

    @Test
    void testListCompanies_MultiplePages() {
        // 准备
        List<SysCompany> companies = Arrays.asList(testCompany);
        when(companyMapper.selectByKeyword(anyString(), anyInt(), anyInt())).thenReturn(companies);
        when(companyMapper.countByKeyword(anyString())).thenReturn(25L);

        // 执行
        PageResponse<SysCompany> result = companyService.listCompanies("测试", 2, 10);

        // 验证
        assertNotNull(result);
        assertEquals(25, result.getTotal());
        assertEquals(2, result.getCurrent());
        assertEquals(3, result.getPages()); // 25条记录，每页10条，需要3页
    }

    @Test
    void testGetCompanyById_Success() {
        // 准备
        when(companyMapper.selectById(1L)).thenReturn(testCompany);

        // 执行
        SysCompany result = companyService.getCompanyById(1L);

        // 验证
        assertNotNull(result);
        assertEquals("测试公司", result.getCompanyName());
        assertEquals("张三", result.getContactPerson());
        assertEquals("13800138000", result.getContactPhone());
        assertEquals("test@example.com", result.getContactEmail());
    }

    @Test
    void testGetCompanyById_NotFound() {
        // 准备
        when(companyMapper.selectById(999L)).thenReturn(null);

        // 执行和验证
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> companyService.getCompanyById(999L));
        assertEquals("企业不存在", exception.getMessage());
    }

    @Test
    void testUpdateCompanyStatus_Success() {
        // 准备
        when(companyMapper.selectById(1L)).thenReturn(testCompany);
        doNothing().when(companyMapper).updateById(any(SysCompany.class));

        // 执行
        assertDoesNotThrow(() -> companyService.updateCompanyStatus(1L, 1));

        // 验证
        verify(companyMapper).updateById(any(SysCompany.class));
    }

    @Test
    void testUpdateCompanyStatus_NotFound() {
        // 准备
        when(companyMapper.selectById(999L)).thenReturn(null);

        // 执行和验证
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> companyService.updateCompanyStatus(999L, 1));
        assertEquals("企业不存在", exception.getMessage());
    }

    @Test
    void testUpdateCompanyStatus_ToApproved() {
        // 准备
        testCompany.setStatus(0); // 待审核状态
        when(companyMapper.selectById(1L)).thenReturn(testCompany);
        doNothing().when(companyMapper).updateById(any(SysCompany.class));

        // 执行
        companyService.updateCompanyStatus(1L, 1);

        // 验证
        verify(companyMapper).updateById(argThat(company -> 
            company.getStatus() == 1 && 
            company.getUpdatedAt() != null
        ));
    }

    @Test
    void testUpdateCompanyStatus_ToRejected() {
        // 准备
        testCompany.setStatus(0); // 待审核状态
        when(companyMapper.selectById(1L)).thenReturn(testCompany);
        doNothing().when(companyMapper).updateById(any(SysCompany.class));

        // 执行
        companyService.updateCompanyStatus(1L, 2);

        // 验证
        verify(companyMapper).updateById(argThat(company -> 
            company.getStatus() == 2 && 
            company.getUpdatedAt() != null
        ));
    }

    @Test
    void testUpdateCompanyStatus_ToDisabled() {
        // 准备
        testCompany.setStatus(1); // 已审核状态
        when(companyMapper.selectById(1L)).thenReturn(testCompany);
        doNothing().when(companyMapper).updateById(any(SysCompany.class));

        // 执行
        companyService.updateCompanyStatus(1L, 3);

        // 验证
        verify(companyMapper).updateById(argThat(company -> 
            company.getStatus() == 3 && 
            company.getUpdatedAt() != null
        ));
    }
} 