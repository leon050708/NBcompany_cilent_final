package org.example.nbcompany.dto.AnalyticsDto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ActivityTrendItem {
    private LocalDate date;
    private Long views;
    private Long registrations;
}