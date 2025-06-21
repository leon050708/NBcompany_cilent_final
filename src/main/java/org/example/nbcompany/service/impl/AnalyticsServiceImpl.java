package org.example.nbcompany.service.impl;

import org.example.nbcompany.dao.AnalyticsMapper;
import org.example.nbcompany.dto.AnalyticsDto.*;
import org.example.nbcompany.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    @Autowired
    private AnalyticsMapper analyticsMapper;

    @Override
    public AnalyticsResponseDto getStatistics(AnalyticsQueryDto queryDto) {
        // 创建最终的响应对象
        AnalyticsResponseDto response = new AnalyticsResponseDto();

        // 分别调用Mapper中的方法，获取各个统计数据
        response.setTotalViews(analyticsMapper.countTotalViews(queryDto));
        response.setTotalRegistrations(analyticsMapper.countTotalRegistrations(queryDto));
        response.setActivityTrend(analyticsMapper.findActivityTrend(queryDto));
        response.setTopViewedNews(analyticsMapper.findTopViewedNews(queryDto));

        return response;
    }
}