package org.example.nbcompany.dto.AnalyticsDto;
import lombok.Data;
import java.util.List;

@Data
public class AnalyticsResponseDto {
    private Long totalViews;
    private Long totalRegistrations;
    private List<ActivityTrendItem> activityTrend;
    private List<TopViewedNewsItem> topViewedNews;
}