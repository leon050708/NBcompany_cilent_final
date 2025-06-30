package org.example.nbcompany.service;

import org.example.nbcompany.dto.NewsDto.ScrapedNewsResponse;
import java.io.IOException;

/**
 * 新闻抓取服务接口
 */
public interface ScrapingService {

    /**
     * 根据给定的URL抓取新闻内容
     *
     * @param url 目标新闻页面的网址
     * @return 包含抓取到的标题、内容等信息的DTO对象
     * @throws IOException 如果网络连接或页面读取失败
     */
    ScrapedNewsResponse scrapeNews(String url) throws IOException;
}