# NBCompany项目设计类文档

---

| 类名   | 所属包   | NbCompanyApplication |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| main   | String[] args | void            |             | 应用程序入口点，启动Spring Boot应用 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

---

| 类名   | 所属包   | CourseController    |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
| courseService | CourseService |         | 私有        | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| createCourse | CourseCreateDTO courseCreateDTO | ApiResponse<CourseDetailDTO> | | 创建课程 |
| getCourseById | Long id | ApiResponse<CourseDetailDTO> | | 获取课程详情 |
| getCourseList | CourseQueryDTO queryDTO | ApiResponse<PageResponse<CourseListItemDTO>> | | 获取课程列表（分页） |
| updateCourse | Long id, CourseUpdateDTO courseUpdateDTO | ApiResponse<CourseDetailDTO> | | 更新课程信息 |
| deleteCourse | Long id | ApiResponse<Void> | | 删除课程 |
| auditCourse | CourseAuditDTO courseAuditDTO | ApiResponse<CourseDetailDTO> | | 审核课程 |
| exportCourseList | CourseQueryDTO queryDTO | ResponseEntity<byte[]> | | 导出课程列表到Excel |
| incrementViewCount | Long id | ApiResponse<Boolean> | | 增加课程浏览次数 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

---

| 类名   | 所属包   | NewsController      |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
| newsService | NewsService |             | 私有        | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| getAllNews | int pageNum, int pageSize, NewsQueryDto queryDto | ApiResponse<PageResponse<NewsResponseDto>> | | 获取新闻列表 |
| createNews | CreateNewsDto createNewsDto | ApiResponse<NewsResponseDto> | | 创建新闻 |
| getNewsDetail | Long newsId | ApiResponse<NewsResponseDto> | | 获取新闻详情 |
| updateNews | Long newsId, UpdateNewsDto updateNewsDto | ApiResponse<NewsResponseDto> | | 更新新闻 |
| deleteNews | Long newsId | ApiResponse<Void> | | 删除新闻 |
| auditNews | Long newsId, AuditNewsDto auditNewsDto | ApiResponse<Void> | | 审核新闻 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

---

| 类名   | 所属包   | MeetingController   |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
| meetingService | MeetingService |       | 私有        | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| createMeeting | BizMeeting meeting | ApiResponse<MeetingDetailDto> | | 创建会议 |
| getMeetingById | Long id | ApiResponse<MeetingDetailDto> | | 获取会议详情 |
| getMeetingList | int pageNum, int pageSize | ApiResponse<PageResponse<MeetingDetailDto>> | | 获取会议列表 |
| updateMeeting | Long id, BizMeeting meeting | ApiResponse<MeetingDetailDto> | | 更新会议信息 |
| deleteMeeting | Long id | ApiResponse<Void> | | 删除会议 |
| registerMeeting | Long meetingId, Long userId | ApiResponse<Void> | | 注册参加会议 |
| cancelRegistration | Long meetingId, Long userId | ApiResponse<Void> | | 取消会议注册 |
| getMeetingRegistrations | Long meetingId | ApiResponse<List<BizMeetingRegistration>> | | 获取会议注册列表 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

---

| 类名   | 所属包   | CompanyController   |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
| companyService | CompanyService |         | 私有        | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| registerCompany | CompanyRegisterRequest request | ApiResponse<CompanyRegisterResponse> | | 注册企业 |
| getCompanyById | Long id | ApiResponse<CompanyDTO> | | 根据ID获取企业信息 |
| updateCompanyStatus | CompanyStatusRequest request | ApiResponse<Void> | | 更新企业状态 |
| createMember | CompanyCreateMemberDTO request | ApiResponse<Void> | | 创建企业成员 |
| updateMember | CompanyUpdateMemberDTO request | ApiResponse<Void> | | 更新企业成员信息 |
| updateMemberRole | CompanyUpdateMemberRoleDTO request | ApiResponse<Void> | | 更新企业成员角色 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

---

| 类名   | 所属包   | UserController      |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
| userService | UserService |         | 私有        | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| registerUser | UserRegisterRequest request | ApiResponse<LoginResponse> | | 用户注册 |
| login | LoginRequest request | ApiResponse<LoginResponse> | | 用户登录 |
| getUserProfile | void | ApiResponse<UserProfileResponse> | | 获取用户资料 |
| updateProfile | UpdateProfileRequest request | ApiResponse<UserProfileResponse> | | 更新用户资料 |
| updatePassword | UpdatePasswordRequest request | ApiResponse<Void> | | 更新密码 |
| createUser | AdminCreateUserDTO request | ApiResponse<Void> | | 管理员创建用户 |
| updateUser | AdminUpdateUserDTO request | ApiResponse<Void> | | 管理员更新用户 |
| deleteUser | Long id | ApiResponse<Void> | | 管理员删除用户 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

---

| 类名   | 所属包   | AdminController     |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
| userService | UserService |         | 私有        | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| getUserList | int pageNum, int pageSize, Long companyId, Integer companyRole, Integer userType, String username, String phoneNumber, Integer status | ApiResponse<PageResponse<SysUser>> | | 获取用户列表 |
| getCompanyUserList | int pageNum, int pageSize, Long companyId, String username, Integer companyRole, Integer status | ApiResponse<PageResponse<SysUser>> | | 获取企业用户列表 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

---

| 类名   | 所属包   | MobileController    |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
| mobileService | MobileService |         | 私有        | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| getCollaborations | void | ApiResponse<List<MobileBizCollaborationResponse>> | | 获取合作列表 |
| getCollaborationById | Long id | ApiResponse<MobileBizCollaborationResponse> | | 根据ID获取合作详情 |
| createCollaboration | Long partnerCompanyId | ApiResponse<Void> | | 创建合作 |
| updateCollaborationStatus | Long id, Integer status | ApiResponse<Void> | | 更新合作状态 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

---

| 类名   | 所属包   | MobileNewsController|     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
| mobileNewsService | MobileNewsService |         | 私有        | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| getNewsList | int pageNum, int pageSize | ApiResponse<PageResponse<NewsResponseDto>> | | 获取新闻列表 |
| getNewsDetail | Long id | ApiResponse<NewsResponseDto> | | 获取新闻详情 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

---

| 类名   | 所属包   | AnalyticsController |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
| analyticsService | AnalyticsService |         | 私有        | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| getAnalytics | AnalyticsQueryDto queryDto | ApiResponse<AnalyticsResponseDto> | | 获取数据分析 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

---

| 类名   | 所属包   | TestController      |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| test | void | String | | 测试接口 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

---

| 类名   | 所属包   | ExampleController   |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| example | void | String | | 示例接口 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

---

## 3. 服务接口（Service Interface）

| 类名   | 所属包   | CourseService       |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| createCourse | CourseCreateDTO courseCreateDTO | CourseDetailDTO | | 创建课程 |
| getCourseById | Long id | CourseDetailDTO | | 获取课程详情 |
| getCourseList | CourseQueryDTO queryDTO | PageInfo<CourseListItemDTO> | | 获取课程列表 |
| updateCourse | Long id, CourseUpdateDTO courseUpdateDTO | CourseDetailDTO | | 更新课程信息 |
| deleteCourse | Long id | void | | 删除课程 |
| auditCourse | CourseAuditDTO courseAuditDTO | CourseDetailDTO | | 审核课程 |
| exportCourseListToExcel | CourseQueryDTO queryDTO | byte[] | | 导出课程列表到Excel |
| incrementViewCount | Long id | boolean | | 增加课程浏览次数 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

| 类名   | 所属包   | NewsService         |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| getAllNews | int pageNum, int pageSize, NewsQueryDto queryDto | PageInfo<NewsResponseDto> | | 获取新闻列表 |
| createNews | CreateNewsDto createNewsDto | NewsResponseDto | | 创建新闻 |
| getNewsDetail | Long newsId | NewsResponseDto | | 获取新闻详情 |
| updateNews | Long newsId, UpdateNewsDto updateNewsDto | NewsResponseDto | | 更新新闻 |
| deleteNews | Long newsId | void | | 删除新闻 |
| auditNews | Long newsId, AuditNewsDto auditNewsDto | void | | 审核新闻 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

| 类名   | 所属包   | MeetingService      |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| createMeeting | BizMeeting meeting | MeetingDetailDto | | 创建会议 |
| getMeetingById | Long id | MeetingDetailDto | | 获取会议详情 |
| getMeetingList | int pageNum, int pageSize | PageInfo<MeetingDetailDto> | | 获取会议列表 |
| updateMeeting | Long id, BizMeeting meeting | MeetingDetailDto | | 更新会议信息 |
| deleteMeeting | Long id | void | | 删除会议 |
| registerMeeting | Long meetingId, Long userId | void | | 注册参加会议 |
| cancelRegistration | Long meetingId, Long userId | void | | 取消会议注册 |
| getMeetingRegistrations | Long meetingId | List<BizMeetingRegistration> | | 获取会议注册列表 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

| 类名   | 所属包   | CompanyService      |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| registerCompany | CompanyRegisterRequest request | CompanyRegisterResponse | | 注册企业 |
| getCompanyById | Long id | CompanyDTO | | 根据ID获取企业信息 |
| updateCompanyStatus | CompanyStatusRequest request | void | | 更新企业状态 |
| createMember | CompanyCreateMemberDTO request | void | | 创建企业成员 |
| updateMember | CompanyUpdateMemberDTO request | void | | 更新企业成员信息 |
| updateMemberRole | CompanyUpdateMemberRoleDTO request | void | | 更新企业成员角色 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

| 类名   | 所属包   | UserService         |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| registerUser | UserRegisterRequest request | LoginResponse | | 用户注册 |
| login | LoginRequest request | LoginResponse | | 用户登录 |
| getUserProfile | void | UserProfileResponse | | 获取用户资料 |
| updateProfile | UpdateProfileRequest request | UserProfileResponse | | 更新用户资料 |
| updatePassword | UpdatePasswordRequest request | void | | 更新密码 |
| createUser | AdminCreateUserDTO request | void | | 管理员创建用户 |
| updateUser | AdminUpdateUserDTO request | void | | 管理员更新用户 |
| deleteUser | Long id | void | | 管理员删除用户 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

| 类名   | 所属包   | MobileService       |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| getCollaborations | void | List<MobileBizCollaborationResponse> | | 获取合作列表 |
| getCollaborationById | Long id | MobileBizCollaborationResponse | | 根据ID获取合作详情 |
| createCollaboration | Long partnerCompanyId | void | | 创建合作 |
| updateCollaborationStatus | Long id, Integer status | void | | 更新合作状态 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

| 类名   | 所属包   | MobileNewsService   |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| getNewsList | int pageNum, int pageSize | PageInfo<NewsResponseDto> | | 获取新闻列表 |
| getNewsDetail | Long id | NewsResponseDto | | 获取新闻详情 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

| 类名   | 所属包   | AnalyticsService    |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| getAnalytics | AnalyticsQueryDto queryDto | AnalyticsResponseDto | | 获取数据分析 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

| 类名   | 所属包   | ExcelExportService  |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| exportCourseListToExcel | List<CourseListItemDTO> courseList | byte[] | | 导出课程列表到Excel |
| exportNewsListToExcel | List<NewsResponseDto> newsList | byte[] | | 导出新闻列表到Excel |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

| 类名   | 所属包   | ScrapingService     |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| scrapeNews | ScrapeRequest request | ScrapedNewsResponse | | 抓取新闻 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

---

## 4. 服务实现类（Service Implementation）

| 类名   | 所属包   | CourseServiceImpl   |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | implements CourseService |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
| bizCourseDao | BizCourseDao |         | 私有        | |
| sysCompanyMapper | SysCompanyMapper |         | 私有        | |
| sysUserMapper | SysUserMapper |         | 私有        | |
| excelExportService | ExcelExportService |         | 私有        | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| createCourse | CourseCreateDTO courseCreateDTO | CourseDetailDTO | | 创建课程 |
| getCourseById | Long id | CourseDetailDTO | | 获取课程详情 |
| getCourseList | CourseQueryDTO queryDTO | PageInfo<CourseListItemDTO> | | 获取课程列表 |
| updateCourse | Long id, CourseUpdateDTO courseUpdateDTO | CourseDetailDTO | | 更新课程信息 |
| deleteCourse | Long id | void | | 删除课程 |
| auditCourse | CourseAuditDTO courseAuditDTO | CourseDetailDTO | | 审核课程 |
| exportCourseListToExcel | CourseQueryDTO queryDTO | byte[] | | 导出课程列表到Excel |
| incrementViewCount | Long id | boolean | | 增加课程浏览次数 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

| 类名   | 所属包   | NewsServiceImpl     |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | implements NewsService |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
| newsMapper | NewsMapper |         | 私有        | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| getAllNews | int pageNum, int pageSize, NewsQueryDto queryDto | PageInfo<NewsResponseDto> | | 获取新闻列表 |
| createNews | CreateNewsDto createNewsDto | NewsResponseDto | | 创建新闻 |
| getNewsDetail | Long newsId | NewsResponseDto | | 获取新闻详情 |
| updateNews | Long newsId, UpdateNewsDto updateNewsDto | NewsResponseDto | | 更新新闻 |
| deleteNews | Long newsId | void | | 删除新闻 |
| auditNews | Long newsId, AuditNewsDto auditNewsDto | void | | 审核新闻 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

| 类名   | 所属包   | MeetingServiceImpl  |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | implements MeetingService |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
| bizMeetingMapper | BizMeetingMapper |         | 私有        | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| createMeeting | BizMeeting meeting | MeetingDetailDto | | 创建会议 |
| getMeetingById | Long id | MeetingDetailDto | | 获取会议详情 |
| getMeetingList | int pageNum, int pageSize | PageInfo<MeetingDetailDto> | | 获取会议列表 |
| updateMeeting | Long id, BizMeeting meeting | MeetingDetailDto | | 更新会议信息 |
| deleteMeeting | Long id | void | | 删除会议 |
| registerMeeting | Long meetingId, Long userId | void | | 注册参加会议 |
| cancelRegistration | Long meetingId, Long userId | void | | 取消会议注册 |
| getMeetingRegistrations | Long meetingId | List<BizMeetingRegistration> | | 获取会议注册列表 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

| 类名   | 所属包   | CompanyServiceImpl  |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | implements CompanyService |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
| sysCompanyMapper | SysCompanyMapper |         | 私有        | |
| sysUserMapper | SysUserMapper |         | 私有        | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| registerCompany | CompanyRegisterRequest request | CompanyRegisterResponse | | 注册企业 |
| getCompanyById | Long id | CompanyDTO | | 根据ID获取企业信息 |
| updateCompanyStatus | CompanyStatusRequest request | void | | 更新企业状态 |
| createMember | CompanyCreateMemberDTO request | void | | 创建企业成员 |
| updateMember | CompanyUpdateMemberDTO request | void | | 更新企业成员信息 |
| updateMemberRole | CompanyUpdateMemberRoleDTO request | void | | 更新企业成员角色 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

| 类名   | 所属包   | UserServiceImpl     |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | implements UserService |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
| sysUserMapper | SysUserMapper |         | 私有        | |
| sysCompanyMapper | SysCompanyMapper |         | 私有        | |
| jwtUtil | JwtUtil |         | 私有        | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| registerUser | UserRegisterRequest request | LoginResponse | | 用户注册 |
| login | LoginRequest request | LoginResponse | | 用户登录 |
| getUserProfile | void | UserProfileResponse | | 获取用户资料 |
| updateProfile | UpdateProfileRequest request | UserProfileResponse | | 更新用户资料 |
| updatePassword | UpdatePasswordRequest request | void | | 更新密码 |
| createUser | AdminCreateUserDTO request | void | | 管理员创建用户 |
| updateUser | AdminUpdateUserDTO request | void | | 管理员更新用户 |
| deleteUser | Long id | void | | 管理员删除用户 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

| 类名   | 所属包   | MobileServiceImpl   |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | implements MobileService |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
| mobileMapper | MobileMapper |         | 私有        | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| getCollaborations | void | List<MobileBizCollaborationResponse> | | 获取合作列表 |
| getCollaborationById | Long id | MobileBizCollaborationResponse | | 根据ID获取合作详情 |
| createCollaboration | Long partnerCompanyId | void | | 创建合作 |
| updateCollaborationStatus | Long id, Integer status | void | | 更新合作状态 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

| 类名   | 所属包   | MobileNewsServiceImpl|     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | implements MobileNewsService |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
| newsMapper | NewsMapper |         | 私有        | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| getNewsList | int pageNum, int pageSize | PageInfo<NewsResponseDto> | | 获取新闻列表 |
| getNewsDetail | Long id | NewsResponseDto | | 获取新闻详情 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

| 类名   | 所属包   | AnalyticsServiceImpl|     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | implements AnalyticsService |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
| analyticsMapper | AnalyticsMapper |         | 私有        | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| getAnalytics | AnalyticsQueryDto queryDto | AnalyticsResponseDto | | 获取数据分析 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

| 类名   | 所属包   | ExcelExportServiceImpl|     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | implements ExcelExportService |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| exportCourseListToExcel | List<CourseListItemDTO> courseList | byte[] | | 导出课程列表到Excel |
| exportNewsListToExcel | List<NewsResponseDto> newsList | byte[] | | 导出新闻列表到Excel |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

| 类名   | 所属包   | ScrapingServiceImpl |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | implements ScrapingService |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| scrapeNews | ScrapeRequest request | ScrapedNewsResponse | | 抓取新闻 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

---

## 5. 工具类（Util）

| 类名   | 所属包   | JwtUtil           |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
| secret | String |         | 私有        | |
| expiration | Long |         | 私有        | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| generateToken | String username | String | | 生成JWT令牌 |
| getUsernameFromToken | String token | String | | 从令牌中获取用户名 |
| validateToken | String token | boolean | | 验证令牌有效性 |
| getExpirationDateFromToken | String token | Date | | 从令牌中获取过期时间 |
| isTokenExpired | String token | boolean | | 检查令牌是否过期 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

| 类名   | 所属包   | SpringContextHolder |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
| applicationContext | ApplicationContext |         | 私有        | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| setApplicationContext | ApplicationContext applicationContext | void | | 设置应用上下文 |
| getApplicationContext | ApplicationContext | | | 获取应用上下文 |
| getBean | String name | Object | | 根据名称获取Bean |
| getBean | Class<T> clazz | T | | 根据类型获取Bean |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

| 类名   | 所属包   | UserContext       |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
| userHolder | ThreadLocal<SysUser> |         | 私有        | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| setCurrentUser | SysUser user | void | | 设置当前用户 |
| getCurrentUser | SysUser | | | 获取当前用户 |
| clear | void | | | 清除当前用户 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

| 类名   | 所属包   | Result<T>         |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
| code | Integer |         | 私有        | |
| message | String |         | 私有        | |
| data | T |         | 私有        | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| success | T data | Result<T> | | 创建成功结果 |
| error | String message | Result<T> | | 创建错误结果 |
| error | Integer code, String message | Result<T> | | 创建带错误码的错误结果 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

---

## 6. 异常类（Exception）

| 类名   | 所属包   | GlobalExceptionHandler |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| handleException | Exception e | ApiResponse<Void> | | 处理通用异常 |
| handleUserExistsException | UserExistsException e | ApiResponse<Void> | | 处理用户已存在异常 |
| handleValidationException | MethodArgumentNotValidException e | ApiResponse<Void> | | 处理参数验证异常 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

| 类名   | 所属包   | UserExistsException |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | extends RuntimeException |     |     |
| 属性   |          |                     |     |     |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| UserExistsException | String message | | | 创建用户已存在异常 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

---

## 7. 配置类（Config）

| 类名   | 所属包   | SecurityConfig      |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| configure | HttpSecurity http | void | | 配置HTTP安全策略 |
| passwordEncoder | void | PasswordEncoder | | 配置密码编码器 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

---

## 8. 切面类（Aspect）

| 类名   | 所属包   | RequireLoginAspect  |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
| jwtUtil | JwtUtil |         | 私有        | |
| sysUserMapper | SysUserMapper |         | 私有        | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| checkLogin | ProceedingJoinPoint joinPoint | Object | | 检查用户登录状态 |
| getTokenFromRequest | String | | | 从请求中获取令牌 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

---

## 9. 注解类（Annotation）

| 类名   | 所属包   | RequireLogin        |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

---

## 10. 过滤器类（Filter）

| 类名   | 所属包   | JwtAuthFilter       |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | implements Filter    |     |     |
| 属性   |          |                     |     |     |
| 名称   | 类型     | 默认值              | Pub/Prv/Pro | |
| jwtUtil | JwtUtil |         | 私有        | |
| sysUserMapper | SysUserMapper |         | 私有        | |
|        |          |                     |             | |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| doFilter | ServletRequest request, ServletResponse response, FilterChain chain | void | | 执行JWT认证过滤 |
| init | FilterConfig filterConfig | void | | 初始化过滤器 |
| destroy | void | | | 销毁过滤器 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

---

## 11. 安全相关类（Security）

| 类名   | 所属包   | PermissionEvaluator |     |     |
|--------|----------|---------------------|-----|-----|
| 继承   | 实现     | 无                  |     |     |
| 属性   |          |                     |     |     |
| 方法   |          |                     |             | |
| 名称   | 参数     | 返回值              | 异常        | 描述 |
| hasPermission | Authentication authentication, Object targetDomainObject, Object permission | boolean | | 检查用户权限 |
| hasPermission | Authentication authentication, Serializable targetId, String targetType, Object permission | boolean | | 检查用户对特定对象的权限 |
|        |          |                     |             | |
| 事件   |          |                     |             | |
| 名称   | 条件     | 参数                | 目的        | |
|        |          |                     |             | |

---

## 项目架构总结

### 设计模式
1. **分层架构模式**: 控制器层、服务层、数据访问层清晰分离
2. **依赖注入模式**: 使用Spring的@Autowired进行依赖注入
3. **单例模式**: Spring容器管理的Bean默认单例
4. **工厂模式**: Spring IoC容器作为Bean工厂
5. **代理模式**: AOP切面使用动态代理
6. **策略模式**: 不同的Service实现不同的业务策略

### 核心特性
1. **JWT认证**: 基于令牌的身份验证
2. **权限控制**: 基于注解的权限验证
3. **异常处理**: 全局异常处理机制
4. **数据分页**: 统一的分页查询支持
5. **Excel导出**: 数据导出功能
6. **移动端支持**: 专门的移动端接口
7. **数据分析**: 系统数据分析功能

### 技术栈
- **框架**: Spring Boot
- **安全**: Spring Security + JWT
- **数据库**: MySQL + MyBatis
- **分页**: PageHelper
- **工具**: Lombok, Apache POI
- **架构**: 分层架构 + AOP 