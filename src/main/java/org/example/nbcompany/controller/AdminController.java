package org.example.nbcompany.controller;

import org.example.nbcompany.dto.request.CompanyStatusRequest;
import org.example.nbcompany.dto.response.*;
import org.example.nbcompany.entity.SysUser;
import org.example.nbcompany.service.CompanyService;
import org.example.nbcompany.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.nbcompany.dto.AnalyticsDto.AnalyticsQueryDto;
import org.example.nbcompany.dto.AnalyticsDto.AnalyticsResponseDto;
import org.example.nbcompany.service.AnalyticsService;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;
    private AnalyticsService analyticsService;
    @GetMapping("/users")
    public ApiResponse<PageResponse<SysUser>> listUsers(
            @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) Integer companyRole,
            @RequestParam(required = false) Integer userType,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponse<SysUser> response = userService.listUsers(companyId, companyRole, userType,
                username, phoneNumber, status, page, size);
        return ApiResponse.success("获取成功", response);
    }

    @PostMapping("/users")
    public ApiResponse<Void> createUser(@RequestBody SysUser user) {
        userService.createUser(user);
        return ApiResponse.success("用户创建成功", null);
    }

    @PutMapping("/users/{userId}")
    public ApiResponse<Void> updateUser(@PathVariable Long userId, @RequestBody SysUser user) {
        userService.updateUser(userId, user);
        return ApiResponse.success("用户信息修改成功", null);
    }

    @PutMapping("/companies/{companyId}/status")
    public ApiResponse<Void> updateCompanyStatus(@PathVariable Long companyId,
                                               @RequestBody CompanyStatusRequest request) {
        companyService.updateCompanyStatus(companyId, request.getStatus());
        return ApiResponse.success("企业状态修改成功", null);
    }
    @GetMapping("/analytics/user-activity")
    public ApiResponse<AnalyticsResponseDto> getUserActivityStatistics(AnalyticsQueryDto queryDto) {
        // @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')") 已经保护了整个Controller
        // Controller只负责接收参数和调用Service
        return ApiResponse.success(analyticsService.getStatistics(queryDto));
    }
} 