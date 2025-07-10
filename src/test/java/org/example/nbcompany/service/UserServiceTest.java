package org.example.nbcompany.service;

import org.example.nbcompany.dao.SysUserMapper;
import org.example.nbcompany.dto.request.*;
import org.example.nbcompany.dto.response.*;
import org.example.nbcompany.entity.SysUser;
import org.example.nbcompany.exception.UserExistsException;
import org.example.nbcompany.service.impl.UserServiceImpl;
import org.example.nbcompany.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private SysUserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private SysUser testUser;
    private UserRegisterRequest registerRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        testUser = new SysUser();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("encodedPassword");
        testUser.setNickname("测试用户");
        testUser.setPhoneNumber("13800138000");
        testUser.setEmail("test@example.com");
        testUser.setGender(1);
        testUser.setUserType(1);
        testUser.setCompanyId(1L);
        testUser.setCompanyRole(1);
        testUser.setStatus(1);
        testUser.setCreatedAt(LocalDateTime.now());
        testUser.setUpdatedAt(LocalDateTime.now());

        registerRequest = new UserRegisterRequest();
        registerRequest.setUsername("newuser");
        registerRequest.setPassword("password123");
        registerRequest.setNickname("新用户");
        registerRequest.setPhoneNumber("13900139000");
        registerRequest.setEmail("new@example.com");
        registerRequest.setGender(1);
        registerRequest.setCompanyId(1L);

        loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password123");
    }

    // @Test
    // void testRegister_Success() {
    //     // 准备
    //     when(userMapper.selectByUsername("newuser")).thenReturn(null);
    //     when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
    //     when(userMapper.insert(any(SysUser.class))).thenReturn(1);

    //     // 执行
    //     assertDoesNotThrow(() -> userService.register(registerRequest));

    //     // 验证
    //     verify(userMapper).selectByUsername("newuser");
    //     verify(passwordEncoder).encode("password123");
    //     verify(userMapper).insert(any(SysUser.class));
    // }

    @Test
    void testRegister_UserExists() {
        // 准备
        when(userMapper.selectByUsername("newuser")).thenReturn(testUser);

        // 执行和验证
        UserExistsException exception = assertThrows(UserExistsException.class, 
            () -> userService.register(registerRequest));
        assertEquals("用户名 'newuser' 已被使用，请选择其他用户名", exception.getMessage());
    }

    @Test
    void testLogin_Success() {
        // 准备
        when(userMapper.selectByUsername("testuser")).thenReturn(testUser);
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);

        try (MockedStatic<JwtUtil> jwtUtilMock = mockStatic(JwtUtil.class)) {
            jwtUtilMock.when(() -> JwtUtil.generateToken(1L, "testuser", 1))
                    .thenReturn("mock.jwt.token");

            // 执行
            LoginResponse response = userService.login(loginRequest);

            // 验证
            assertNotNull(response);
            assertEquals("mock.jwt.token", response.getToken());
            assertNotNull(response.getUserInfo());
            assertEquals(1L, response.getUserInfo().getId());
            assertEquals("testuser", response.getUserInfo().getUsername());
        }
    }

    @Test
    void testLogin_UserNotFound() {
        // 准备
        when(userMapper.selectByUsername("testuser")).thenReturn(null);

        // 执行和验证
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> userService.login(loginRequest));
        assertEquals("用户名或密码错误", exception.getMessage());
    }

    @Test
    void testLogin_WrongPassword() {
        // 准备
        when(userMapper.selectByUsername("testuser")).thenReturn(testUser);
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(false);

        // 执行和验证
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> userService.login(loginRequest));
        assertEquals("用户名或密码错误", exception.getMessage());
    }

    @Test
    void testLogin_UserDisabled() {
        // 准备
        testUser.setStatus(0);
        when(userMapper.selectByUsername("testuser")).thenReturn(testUser);
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);

        // 执行和验证
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> userService.login(loginRequest));
        assertEquals("账号已被禁用", exception.getMessage());
    }

    @Test
    void testGetProfile_Success() {
        // 准备
        when(userMapper.selectById(1L)).thenReturn(testUser);

        // 执行
        UserProfileResponse response = userService.getProfile(1L);

        // 验证
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("testuser", response.getUsername());
        assertEquals("测试用户", response.getNickname());
        assertEquals("13800138000", response.getPhoneNumber());
        assertEquals("test@example.com", response.getEmail());
    }

    @Test
    void testGetProfile_UserNotFound() {
        // 准备
        when(userMapper.selectById(1L)).thenReturn(null);

        // 执行和验证
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> userService.getProfile(1L));
        assertEquals("用户不存在", exception.getMessage());
    }

    // @Test
    // void testUpdateProfile_Success() {
    //     // 准备
    //     when(userMapper.selectById(1L)).thenReturn(testUser);
    //     when(userMapper.updateById(any(SysUser.class))).thenReturn(1);

    //     UpdateProfileRequest request = new UpdateProfileRequest();
    //     request.setNickname("新昵称");
    //     request.setPhoneNumber("13900139000");
    //     request.setEmail("new@example.com");
    //     request.setGender(2);

    //     // 执行
    //     assertDoesNotThrow(() -> userService.updateProfile(1L, request));

    //     // 验证
    //     verify(userMapper).selectById(1L);
    //     verify(userMapper).updateById(any(SysUser.class));
    // }

    @Test
    void testUpdateProfile_UserNotFound() {
        // 准备
        when(userMapper.selectById(1L)).thenReturn(null);

        UpdateProfileRequest request = new UpdateProfileRequest();

        // 执行和验证
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> userService.updateProfile(1L, request));
        assertEquals("用户不存在", exception.getMessage());
    }

    // @Test
    // void testUpdatePassword_Success() {
    //     // 准备
    //     when(userMapper.selectById(1L)).thenReturn(testUser);
    //     when(passwordEncoder.matches("oldPassword", "encodedPassword")).thenReturn(true);
    //     when(passwordEncoder.encode("newPassword")).thenReturn("newEncodedPassword");
    //     when(userMapper.updateById(any(SysUser.class))).thenReturn(1);

    //     UpdatePasswordRequest request = new UpdatePasswordRequest();
    //     request.setOldPassword("oldPassword");
    //     request.setNewPassword("newPassword");
    //     request.setConfirmNewPassword("newPassword");

    //     // 执行
    //     assertDoesNotThrow(() -> userService.updatePassword(1L, request));

    //     // 验证
    //     verify(userMapper).selectById(1L);
    //     verify(passwordEncoder).matches("oldPassword", "encodedPassword");
    //     verify(passwordEncoder).encode("newPassword");
    //     verify(userMapper).updateById(any(SysUser.class));
    // }

    @Test
    void testUpdatePassword_WrongOldPassword() {
        // 准备
        when(userMapper.selectById(1L)).thenReturn(testUser);
        when(passwordEncoder.matches("oldPassword", "encodedPassword")).thenReturn(false);

        UpdatePasswordRequest request = new UpdatePasswordRequest();
        request.setOldPassword("oldPassword");
        request.setNewPassword("newPassword");
        request.setConfirmNewPassword("newPassword");

        // 执行和验证
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> userService.updatePassword(1L, request));
        assertEquals("原密码错误", exception.getMessage());
    }

    @Test
    void testUpdatePassword_PasswordMismatch() {
        // 准备
        when(userMapper.selectById(1L)).thenReturn(testUser);
        when(passwordEncoder.matches("oldPassword", "encodedPassword")).thenReturn(true);

        UpdatePasswordRequest request = new UpdatePasswordRequest();
        request.setOldPassword("oldPassword");
        request.setNewPassword("newPassword");
        request.setConfirmNewPassword("differentPassword");

        // 执行和验证
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> userService.updatePassword(1L, request));
        assertEquals("两次输入的新密码不一致", exception.getMessage());
    }

    @Test
    void testListUsers_Success() {
        // 准备
        List<SysUser> users = Arrays.asList(testUser);
        when(userMapper.selectByConditions(any(), any(), any(), any(), any(), any(), anyInt(), anyInt()))
                .thenReturn(users);
        when(userMapper.countByConditions(any(), any(), any(), any(), any(), any()))
                .thenReturn(1L);

        // 执行
        PageResponse<SysUser> response = userService.listUsers(1L, 1, 1, "test", "138", 1, 1, 10);

        // 验证
        assertNotNull(response);
        assertEquals(1, response.getCurrent());
        assertEquals(1, response.getTotal());
        assertEquals(1, response.getRecords().size());
        assertEquals(testUser, response.getRecords().get(0));
    }

    // @Test
    // void testCreateUser_Success() {
    //     // 准备
    //     when(userMapper.selectByUsername("newuser")).thenReturn(null);
    //     when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
    //     when(userMapper.insert(any(SysUser.class))).thenReturn(1);

    //     SysUser newUser = new SysUser();
    //     newUser.setUsername("newuser");
    //     newUser.setPassword("password123");

    //     // 执行
    //     assertDoesNotThrow(() -> userService.createUser(newUser));

    //     // 验证
    //     verify(userMapper).selectByUsername("newuser");
    //     verify(passwordEncoder).encode("password123");
    //     verify(userMapper).insert(any(SysUser.class));
    // }

    @Test
    void testCreateUser_UserExists() {
        // 准备
        when(userMapper.selectByUsername("newuser")).thenReturn(testUser);

        SysUser newUser = new SysUser();
        newUser.setUsername("newuser");

        // 执行和验证
        UserExistsException exception = assertThrows(UserExistsException.class, 
            () -> userService.createUser(newUser));
        assertEquals("用户名 'newuser' 已被使用，请选择其他用户名", exception.getMessage());
    }
} 