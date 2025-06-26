package org.example.nbcompany.controller;

import com.github.pagehelper.PageInfo;
import org.example.nbcompany.dto.NewsDto.CreateNewsDto;
import org.example.nbcompany.dto.NewsDto.NewsQueryDto;
import org.example.nbcompany.dto.NewsDto.NewsResponseDto;
import org.example.nbcompany.dto.NewsDto.UpdateNewsDto;
import org.example.nbcompany.dto.response.ApiResponse;
import org.example.nbcompany.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping
    public ApiResponse<NewsResponseDto> createNews(@RequestBody CreateNewsDto createNewsDto) {
        return ApiResponse.success(newsService.createNews(createNewsDto));
    }

    // ==================== 修改开始 ====================
    @GetMapping
    public ApiResponse<PageInfo<NewsResponseDto>> getAllNews(@RequestParam(defaultValue = "1") int pageNum,
                                                             @RequestParam(defaultValue = "10") int pageSize,
                                                             NewsQueryDto queryDto) {
        try {
            // 你原来的代码不变
            return ApiResponse.success(newsService.getAllNews(pageNum, pageSize, queryDto));
        } catch (Exception e) {
            // 添加这个catch块来打印详细错误
            System.err.println("!!!!!!!!!!  getAllNews 方法捕获到异常  !!!!!!!!!!");
            e.printStackTrace(); // 这会打印出完整的错误堆栈

            // 重新抛出异常，保持和之前一样的500错误行为
            throw e;
        }
    }
    // ==================== 修改结束 ====================

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
        // 修正：调用 success(null) 来匹配已存在的方法
        return ApiResponse.success(null);
    }
}