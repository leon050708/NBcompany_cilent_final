package org.example.nbcompany.service;
import org.example.nbcompany.dto.AnalyticsDto.*;

public interface AnalyticsService {
    AnalyticsResponseDto getStatistics(AnalyticsQueryDto queryDto);
}