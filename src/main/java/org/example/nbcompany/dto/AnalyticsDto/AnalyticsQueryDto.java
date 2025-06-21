package org.example.nbcompany.dto.AnalyticsDto;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AnalyticsQueryDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private String actionType;
    private Long companyId; // 注意：日志表可能没有companyId，需要通过user_id关联查询
}