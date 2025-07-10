package org.example.nbcompany.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.nbcompany.dto.request.UpdatePasswordRequest;
import org.example.nbcompany.dto.request.UpdateProfileRequest;
import org.example.nbcompany.dto.response.ApiResponse;
import org.example.nbcompany.dto.response.UserProfileResponse;
import org.example.nbcompany.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetProfile_Success() throws Exception {
        // 准备
        Long userId = 1L;
        UserProfileResponse profileResponse = new UserProfileResponse();
        profileResponse.setId(userId);
        profileResponse.setUsername("testuser");
        profileResponse.setNickname("测试用户");
        profileResponse.setEmail("test@example.com");
        profileResponse.setPhoneNumber("13800138000");

        when(userService.getProfile(userId)).thenReturn(profileResponse);

        // 执行和验证
        mockMvc.perform(get("/api/v1/user/profile")
                .requestAttr("userId", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("获取成功"))
                .andExpect(jsonPath("$.data.id").value(userId))
                .andExpect(jsonPath("$.data.username").value("testuser"))
                .andExpect(jsonPath("$.data.nickname").value("测试用户"))
                .andExpect(jsonPath("$.data.email").value("test@example.com"))
                .andExpect(jsonPath("$.data.phoneNumber").value("13800138000"));
    }

    @Test
    void testUpdateProfile_Success() throws Exception {
        // 准备
        Long userId = 1L;
        UpdateProfileRequest request = new UpdateProfileRequest();
        request.setNickname("新昵称");
        request.setPhoneNumber("13900139000");
        request.setEmail("new@example.com");
        request.setGender(2);

        // 执行和验证
        mockMvc.perform(put("/api/v1/user/profile")
                .requestAttr("userId", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("修改成功"));
    }

    @Test
    void testUpdatePassword_Success() throws Exception {
        // 准备
        Long userId = 1L;
        UpdatePasswordRequest request = new UpdatePasswordRequest();
        request.setOldPassword("oldPassword");
        request.setNewPassword("newPassword");
        request.setConfirmNewPassword("newPassword");

        // 执行和验证
        mockMvc.perform(put("/api/v1/user/password")
                .requestAttr("userId", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("密码修改成功"));
    }

    @Test
    void testUpdateProfile_InvalidRequest() throws Exception {
        // 准备
        Long userId = 1L;
        UpdateProfileRequest request = new UpdateProfileRequest();
        // 不设置任何字段，测试空请求

        // 执行和验证
        mockMvc.perform(put("/api/v1/user/profile")
                .requestAttr("userId", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("修改成功"));
    }

    @Test
    void testUpdatePassword_InvalidRequest() throws Exception {
        // 准备
        Long userId = 1L;
        UpdatePasswordRequest request = new UpdatePasswordRequest();
        // 不设置任何字段，测试空请求

        // 执行和验证
        mockMvc.perform(put("/api/v1/user/password")
                .requestAttr("userId", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("密码修改成功"));
    }
} 