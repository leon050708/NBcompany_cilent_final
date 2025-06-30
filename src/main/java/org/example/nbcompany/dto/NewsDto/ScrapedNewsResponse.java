package org.example.nbcompany.dto.NewsDto;

import lombok.Data;

@Data
public class ScrapedNewsResponse {
    private String title;
    private String content;
    private String summary;
    private String coverImageUrl;
}