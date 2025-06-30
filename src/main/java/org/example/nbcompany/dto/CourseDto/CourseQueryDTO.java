package org.example.nbcompany.dto.CourseDto;

import lombok.Data;

/**
 * 课程查询DTO
 * 用于课程查询和筛选时的数据传输
 * 包含查询条件，支持按多个维度筛选课程
 */
@Data
public class CourseQueryDTO {
    
    /** 课程名称 - 模糊查询 */
    private String courseName;
    
    /** 作者ID - 精确查询 */
    private Long authorId;
    
    /** 作者名称 - 模糊查询 */
    private String authorName;
    
    /** 企业ID - 精确查询 */
    private Long companyId;
    private String companyName;
    /** 审核状态 (0:待审核, 1:已发布/审核通过, 2:审核未通过) */
    private Integer status;
    
    /** 排序字段 (courseName, createdAt, viewCount, sortOrder) */
    private String sortBy = "createdAt";
    
    /** 排序方向 (asc, desc) */
    private String sortOrder = "desc";
    
    /** 页码 */
    private Integer pageNum = 1;
    
    /** 每页大小 */
    private Integer pageSize = 10;
    
    // 权限过滤相关字段
    /** 当前用户ID */
    private Long currentUserId;
    
    /** 当前用户类型 (1:企业用户, 2:平台管理员) */
    private Integer currentUserType;
    
    /** 当前用户企业角色 (1:普通用户, 2:企业管理员) */
    private Integer currentCompanyRole;
    
    /** 当前用户所属企业ID */
    private Long currentCompanyId;

} 