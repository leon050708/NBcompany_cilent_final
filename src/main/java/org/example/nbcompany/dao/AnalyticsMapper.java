package org.example.nbcompany.dao;

import org.apache.ibatis.annotations.Mapper;
import org.example.nbcompany.dto.AnalyticsDto.*; // 我们稍后会创建这些DTO
import java.util.List;

@Mapper
public interface AnalyticsMapper {

    // 获取总浏览次数
    Long countTotalViews(AnalyticsQueryDto query);

    // 获取总注册人数
    Long countTotalRegistrations(AnalyticsQueryDto query);

    // 获取活动趋势
    List<ActivityTrendItem> findActivityTrend(AnalyticsQueryDto query);

    // 获取浏览量最高的N条新闻
    List<TopViewedNewsItem> findTopViewedNews(AnalyticsQueryDto query);
}