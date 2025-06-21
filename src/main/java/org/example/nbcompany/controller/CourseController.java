package org.example.nbcompany.controller;

import com.github.pagehelper.PageInfo;
import org.example.nbcompany.annotation.RequireLogin;
import org.example.nbcompany.dto.CourseDto.*;
import org.example.nbcompany.dto.response.ApiResponse;
import org.example.nbcompany.dto.response.PageResponse;
import org.example.nbcompany.service.CourseService;
import org.example.nbcompany.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 课程管理控制器
 * 提供课程相关的RESTful API接口
 * 所有接口都需要用户登录验证
 */
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    /**
     * 创建课程
     * @param courseCreateDTO 课程创建信息
     * @return 创建结果
     */
    @PostMapping
    @RequireLogin
    public ApiResponse<CourseDetailDTO> createCourse(@RequestBody CourseCreateDTO courseCreateDTO) {
        try {
            CourseDetailDTO result = courseService.createCourse(courseCreateDTO);
            return ApiResponse.success("课程创建成功", result);
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(400, e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error(500, "课程创建失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据ID获取课程详情
     * @param id 课程ID
     * @return 课程详情
     */
    @GetMapping("/{id}")
    @RequireLogin
    public ApiResponse<CourseDetailDTO> getCourseById(@PathVariable Long id) {
        try {
            CourseDetailDTO course = courseService.getCourseById(id);
            if (course == null) {
                return ApiResponse.error(404, "课程不存在");
            }
            return ApiResponse.success("获取课程详情成功", course);
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(403, e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error(500, "获取课程详情失败: " + e.getMessage());
        }
    }
    
    /**
     * 分页查询课程列表
     * @param queryDTO 查询条件
     * @return 课程列表
     */
    @GetMapping
    @RequireLogin
    public ApiResponse<PageResponse<CourseListItemDTO>> getCourseList(CourseQueryDTO queryDTO) {
        try {
            PageInfo<CourseListItemDTO> pageInfo = courseService.getCourseList(queryDTO);
            PageResponse<CourseListItemDTO> response = new PageResponse<>();
            response.setRecords(pageInfo.getList());
            response.setCurrent(pageInfo.getPageNum());
            response.setPages(pageInfo.getPages());
            response.setTotal(pageInfo.getTotal());
            return ApiResponse.success("获取课程列表成功", response);
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(403, e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error(500, "获取课程列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新课程信息
     * @param id 课程ID
     * @param courseUpdateDTO 课程更新信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    @RequireLogin
    public ApiResponse<CourseDetailDTO> updateCourse(@PathVariable Long id, @RequestBody CourseUpdateDTO courseUpdateDTO) {
        try {
            CourseDetailDTO result = courseService.updateCourse(id, courseUpdateDTO);
            if (result == null) {
                return ApiResponse.error(404, "课程不存在");
            }
            return ApiResponse.success("课程更新成功", result);
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(400, e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error(500, "课程更新失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除课程
     * @param id 课程ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    @RequireLogin
    public ApiResponse<Void> deleteCourse(@PathVariable Long id) {
        try {
            courseService.deleteCourse(id);
            return ApiResponse.success("课程删除成功", null);
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(400, e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error(500, "课程删除失败: " + e.getMessage());
        }
    }
    
    /**
     * 审核课程
     * @param courseAuditDTO 课程审核信息
     * @return 审核结果
     */
    @PostMapping("/audit")
    @RequireLogin
    public ApiResponse<CourseDetailDTO> auditCourse(@RequestBody CourseAuditDTO courseAuditDTO) {
        try {
            CourseDetailDTO result = courseService.auditCourse(courseAuditDTO);
            return ApiResponse.success("课程审核成功", result);
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(400, e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error(500, "课程审核失败: " + e.getMessage());
        }
    }
    
    /**
     * 导出课程列表到Excel
     * @param queryDTO 查询条件
     * @return Excel文件
     */
    @GetMapping("/export")
    @RequireLogin
    public ResponseEntity<byte[]> exportCourseListToExcel(CourseQueryDTO queryDTO) {
        try {
            byte[] excelData = courseService.exportCourseListToExcel(queryDTO);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", "课程列表.xlsx");
            headers.setContentLength(excelData.length);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelData);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    /**
     * 增加课程观看量
     */
    @PostMapping("/{id}/view")
    @RequireLogin
    public ApiResponse<Object> incrementViewCount(@PathVariable Long id) {
        try {
            boolean increased = courseService.incrementViewCount(id);
            if (increased) {
                return ApiResponse.success("观看量+1", java.util.Map.of("viewCountIncreased", true));
            } else {
                return ApiResponse.success("未增加观看量（非普通用户或未发布课程）", java.util.Map.of("viewCountIncreased", false));
            }
        } catch (Exception e) {
            return ApiResponse.error(500, "增加观看量失败: " + e.getMessage());
        }
    }
}
