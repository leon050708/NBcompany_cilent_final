package org.example.nbcompany.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.nbcompany.dao.NewsMapper;
import org.example.nbcompany.dto.NewsDto.NewsQueryDto;
import org.example.nbcompany.dto.NewsDto.NewsResponseDto;
import org.example.nbcompany.entity.BizNews;
import org.example.nbcompany.service.MobileNewsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MobileNewsServiceImpl implements MobileNewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public PageInfo<NewsResponseDto> getMobileNewsList(int pageNum, int pageSize, String keyword) {
        // 构建查询条件，移动端只显示已发布的新闻
        NewsQueryDto queryDto = new NewsQueryDto();
        queryDto.setStatus(1); // 只查询已发布的新闻
        
        // 如果有关键字，设置综合关键字搜索
        if (keyword != null && !keyword.trim().isEmpty()) {
            queryDto.setKeyword(keyword);
        }
        
        PageHelper.startPage(pageNum, pageSize);
        List<BizNews> newsList = newsMapper.findList(queryDto);
        PageInfo<BizNews> newsPageInfo = new PageInfo<>(newsList);
        
        List<NewsResponseDto> dtoList = newsList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        
        PageInfo<NewsResponseDto> dtoPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(newsPageInfo, dtoPageInfo);
        dtoPageInfo.setList(dtoList);
        
        return dtoPageInfo;
    }

    @Override
    @Transactional
    public NewsResponseDto getMobileNewsDetail(Long newsId) {
        BizNews news = newsMapper.findById(newsId);
        if (news == null) {
            throw new RuntimeException("动态不存在");
        }
        
        // 移动端只显示已发布的新闻
        if (news.getStatus() != 1) {
            throw new RuntimeException("该动态暂未发布");
        }
        
        // 增加浏览次数
        news.setViewCount(news.getViewCount() + 1);
        newsMapper.update(news);
        
        return convertToDto(news);
    }
    
    private NewsResponseDto convertToDto(BizNews news) {
        if (news == null) {
            return null;
        }
        NewsResponseDto newsDto = new NewsResponseDto();
        BeanUtils.copyProperties(news, newsDto);
        return newsDto;
    }
}
