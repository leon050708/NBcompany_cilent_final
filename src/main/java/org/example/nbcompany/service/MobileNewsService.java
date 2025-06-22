package org.example.nbcompany.service;

import com.github.pagehelper.PageInfo;
import org.example.nbcompany.dto.NewsDto.NewsResponseDto;

public interface MobileNewsService {
    
    /**
     * 获取移动端新闻列表
     * 只显示已发布的新闻，支持关键字搜索
     */
    PageInfo<NewsResponseDto> getMobileNewsList(int pageNum, int pageSize, String keyword);
    
    /**
     * 获取移动端新闻详情
     * 只显示已发布的新闻，无需认证
     */
    NewsResponseDto getMobileNewsDetail(Long newsId);
}
