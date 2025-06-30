package org.example.nbcompany.controller;

import com.github.pagehelper.PageInfo;
import org.example.nbcompany.dto.NewsDto.CreateNewsDto;
import org.example.nbcompany.dto.NewsDto.NewsQueryDto;
import org.example.nbcompany.dto.NewsDto.NewsResponseDto;
import org.example.nbcompany.dto.NewsDto.UpdateNewsDto;
// 【重要】导入我们新增的DTO和服务
import org.example.nbcompany.dto.NewsDto.ScrapeRequest;
import org.example.nbcompany.dto.NewsDto.ScrapedNewsResponse;
import org.example.nbcompany.service.ScrapingService;
import org.example.nbcompany.dto.response.ApiResponse;
import org.example.nbcompany.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/api/news") // 基础路径是 /api/news
public class NewsController {

    @Autowired
    private NewsService newsService;

    // ==================== 新增部分开始 ====================

    @Autowired
    private ScrapingService scrapingService; // 注入抓取服务

    /**
     * 从指定URL抓取新闻内容的接口
     * @param request 包含目标URL的请求体
     * @return 包含抓取结果的ApiResponse
     */
    @PostMapping("/scrape") // 接口路径为 /api/news/scrape
    public ApiResponse<ScrapedNewsResponse> scrapeNewsFromUrl(@RequestBody ScrapeRequest request) {
        try {
            ScrapedNewsResponse scrapedData = scrapingService.scrapeNews(request.getUrl());
            // 如果抓取到的标题为空，可能意味着页面结构不匹配，可以返回一个更明确的提示
            if (scrapedData.getTitle() == null || scrapedData.getTitle().isEmpty()) {
                return ApiResponse.error(404, "抓取失败：无法在该页面找到标题，请检查URL或网站结构。");
            }
            return ApiResponse.success("抓取成功", scrapedData);
        } catch (IOException e) {
            // 打印错误日志，方便调试
            e.printStackTrace();
            return ApiResponse.error(500, "抓取失败：无法连接到指定的URL，请检查网络或URL是否正确。");
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(500, "抓取时发生未知错误。");
        }
    }

    // ==================== 新增部分结束 ====================

    @PostMapping
    public ApiResponse<NewsResponseDto> createNews(@RequestBody CreateNewsDto createNewsDto) {
        return ApiResponse.success(newsService.createNews(createNewsDto));
    }

    @GetMapping
    public ApiResponse<PageInfo<NewsResponseDto>> getAllNews(@RequestParam(defaultValue = "1") int pageNum,
                                                             @RequestParam(defaultValue = "10") int pageSize,
                                                             NewsQueryDto queryDto) {
        // 您之前的异常捕获代码很好，可以保留
        try {
            return ApiResponse.success(newsService.getAllNews(pageNum, pageSize, queryDto));
        } catch (Exception e) {
            System.err.println("!!!!!!!!!!  getAllNews 方法捕获到异常  !!!!!!!!!!");
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<NewsResponseDto> getNewsDetail(@PathVariable Long id) {
        return ApiResponse.success(newsService.getNewsDetail(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<NewsResponseDto> updateNews(@PathVariable Long id, @RequestBody UpdateNewsDto updateNewsDto) {
        return ApiResponse.success(newsService.updateNews(id, updateNewsDto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return ApiResponse.success(null);
    }
}