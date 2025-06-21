package org.example.nbcompany.dto.AnalyticsDto;

import lombok.Data;

@Data
public class TopViewedNewsItem {
    private Long id;
    private String title;
    private Long views;
}