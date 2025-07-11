package org.example.nbcompany.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.nbcompany.dao.NewsMapper;
import org.example.nbcompany.dto.NewsDto.*;
import org.example.nbcompany.entity.BizNews;
import org.example.nbcompany.entity.SysUser;
import org.example.nbcompany.service.NewsService;
import org.example.nbcompany.util.UserContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// --- Import Jsoup for sanitization ---
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public PageInfo<NewsResponseDto> getAllNews(int pageNum, int pageSize, NewsQueryDto queryDto) {
        SysUser currentUser = UserContext.getCurrentUser();
        if (currentUser == null) { throw new SecurityException("用户未登录"); }

        if (!UserContext.isPlatformAdmin()) {
            queryDto.setCompanyId(null);
            Integer queryStatus = queryDto.getStatus();
            if (queryStatus == null) {
                queryDto.setStatus(1);
            } else if (queryStatus == 0 || queryStatus == 2) {
                if (UserContext.isCompanyAdmin()) {
                    queryDto.setCompanyId(currentUser.getCompanyId());
                } else {
                    throw new SecurityException("您无权查看此状态的动态");
                }
            }
        }

        PageHelper.startPage(pageNum, pageSize);
        List<BizNews> newsList = newsMapper.findList(queryDto);
        PageInfo<BizNews> newsPageInfo = new PageInfo<>(newsList);
        List<NewsResponseDto> dtoList = newsList.stream().map(this::convertToDto).collect(Collectors.toList());
        PageInfo<NewsResponseDto> dtoPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(newsPageInfo, dtoPageInfo);
        dtoPageInfo.setList(dtoList);
        return dtoPageInfo;
    }


    @Override
    @Transactional
    public NewsResponseDto createNews(CreateNewsDto createNewsDto) {
        SysUser currentUser = UserContext.getCurrentUser();
        if (currentUser == null) {
            throw new SecurityException("用户未登录，无法发布动态");
        }
        if (!UserContext.isPlatformAdmin() && !UserContext.isCompanyAdmin()) {
            throw new SecurityException("您没有权限发布动态");
        }

        // --- HTML Sanitization START ---
        // Get the original, potentially unsafe HTML content
        String unsafeContent = createNewsDto.getContent();
        if (unsafeContent != null) {
            // Clean the HTML using Jsoup's basicWithImages() safelist.
            // This allows basic formatting (b, i, p, etc.) and img tags, but removes scripts.
            String safeContent = Jsoup.clean(unsafeContent, Safelist.basicWithImages());
            createNewsDto.setContent(safeContent);
        }
        // --- HTML Sanitization END ---

        BizNews news = new BizNews();
        BeanUtils.copyProperties(createNewsDto, news);
        news.setAuthorId(currentUser.getId());
        news.setAuthorName(currentUser.getNickname());
        if (!UserContext.isPlatformAdmin()) {
            news.setCompanyId(currentUser.getCompanyId());
        }
        news.setStatus(UserContext.isPlatformAdmin() ? 1 : 0);
        newsMapper.insert(news);
        return convertToDto(news);
    }

    @Override
    @Transactional
    public NewsResponseDto getNewsDetail(Long newsId) {
        BizNews news = newsMapper.findById(newsId);
        if (news == null) {
            throw new RuntimeException("动态不存在");
        }
        if (news.getStatus() != 1) {
            if (!UserContext.isPlatformAdmin()) {
                if (!Objects.equals(UserContext.getCurrentUserId(), news.getAuthorId()) &&
                        !(UserContext.isCompanyAdmin() && Objects.equals(UserContext.getCurrentUserCompanyId(), news.getCompanyId()))) {
                    throw new SecurityException("您无权查看此动态");
                }
            }
        }
        news.setViewCount(news.getViewCount() + 1);
        newsMapper.update(news);
        return convertToDto(news);
    }

    @Override
    @Transactional
    public NewsResponseDto updateNews(Long newsId, UpdateNewsDto updateNewsDto) {
        BizNews existingNews = newsMapper.findById(newsId);
        if (existingNews == null) {
            throw new RuntimeException("动态不存在，无法更新");
        }
        if (!UserContext.isPlatformAdmin() &&
                !(UserContext.isCompanyAdmin() && Objects.equals(UserContext.getCurrentUserCompanyId(), existingNews.getCompanyId()))) {
            throw new SecurityException("您没有权限修改此动态");
        }

        // --- HTML Sanitization START ---
        String unsafeContent = updateNewsDto.getContent();
        if (unsafeContent != null) {
            String safeContent = Jsoup.clean(unsafeContent, Safelist.basicWithImages());
            updateNewsDto.setContent(safeContent);
        }
        // --- HTML Sanitization END ---

        BeanUtils.copyProperties(updateNewsDto, existingNews);
        newsMapper.update(existingNews);
        return convertToDto(existingNews);
    }

    @Override
    @Transactional
    public void deleteNews(Long newsId) {
        BizNews existingNews = newsMapper.findById(newsId);
        if (existingNews == null) { return; }
        if (!UserContext.isPlatformAdmin() &&
                !(UserContext.isCompanyAdmin() && Objects.equals(UserContext.getCurrentUserCompanyId(), existingNews.getCompanyId()))) {
            throw new SecurityException("您没有权限删除此动态");
        }
        newsMapper.deleteById(newsId);
    }

    @Override
    @Transactional
    public void auditNews(Long newsId, AuditNewsDto auditNewsDto) {
        if (!UserContext.isPlatformAdmin()) {
            throw new SecurityException("只有平台超级管理员才能审核动态");
        }
        BizNews existingNews = newsMapper.findById(newsId);
        if (existingNews == null) {
            throw new RuntimeException("动态不存在，无法审核");
        }
        existingNews.setStatus(auditNewsDto.getStatus());
        newsMapper.update(existingNews);
    }

    private NewsResponseDto convertToDto(BizNews news) {
        if (news == null) { return null; }
        NewsResponseDto newsDto = new NewsResponseDto();
        BeanUtils.copyProperties(news, newsDto);
        if (news.getCompanyId() != null) {
            org.example.nbcompany.dao.SysCompanyMapper companyMapper = org.example.nbcompany.util.SpringContextHolder.getBean(org.example.nbcompany.dao.SysCompanyMapper.class);
            org.example.nbcompany.entity.SysCompany company = companyMapper.selectById(news.getCompanyId());
            if (company != null) {
                newsDto.setCompanyName(company.getCompanyName());
            }
        }
        return newsDto;
    }
}