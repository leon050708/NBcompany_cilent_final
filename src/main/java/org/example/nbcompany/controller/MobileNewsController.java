package org.example.nbcompany.controller;

import com.github.pagehelper.PageInfo;
import org.example.nbcompany.dto.NewsDto.NewsResponseDto;
import org.example.nbcompany.dto.response.ApiResponse;
import org.example.nbcompany.service.MobileNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 移动端新闻控制器
 * 提供移动端专用的新闻接口，简化查询条件，只显示已发布的新闻
 */
@RestController
@RequestMapping("/api/mobile/news")
public class MobileNewsController {

    @Autowired
    private MobileNewsService mobileNewsService;

    /**
     * 获取移动端新闻列表
     * 只显示已发布的新闻，支持关键字搜索
     */
    @GetMapping
    public ApiResponse<PageInfo<NewsResponseDto>> getMobileNewsList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String keyword) {
        
        return ApiResponse.success(mobileNewsService.getMobileNewsList(pageNum, pageSize, keyword));
    }

    /**
     * 获取移动端新闻详情
     * 无需认证，但只显示已发布的新闻
     */
    @GetMapping("/{newsId}")
    public ApiResponse<NewsResponseDto> getMobileNewsDetail(@PathVariable Long newsId) {
        return ApiResponse.success(mobileNewsService.getMobileNewsDetail(newsId));
    }
}
