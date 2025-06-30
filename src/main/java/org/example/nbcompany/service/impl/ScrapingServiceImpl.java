package org.example.nbcompany.service.impl;

import org.example.nbcompany.dto.NewsDto.ScrapedNewsResponse;
import org.example.nbcompany.service.ScrapingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class ScrapingServiceImpl implements ScrapingService {

    // 【核心修改】定义一组常见的CSS选择器，按可能性排序
    private static final String[] TITLE_SELECTORS = {
            "h1", "h2", ".title", ".article-title", "#title", "#headline"
    };

    private static final String[] CONTENT_SELECTORS = {
            "div.article-content", "div.content", "div.main-content",
            "div.post-content", "article", "section.content"
    };

    @Override
    public ScrapedNewsResponse scrapeNews(String url) throws IOException {

        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                .timeout(30000)
                .get();

        // 使用新的辅助方法来查找元素
        String title = findFirstMatch(doc, TITLE_SELECTORS, "text");
        Element contentElement = findFirstElement(doc, CONTENT_SELECTORS);

        String content = "";
        String coverImageUrl = "";

        if (contentElement != null) {
            // 移除不必要的部分，例如脚本和样式块
            contentElement.select("script, style").remove();
            content = contentElement.html();

            // 在找到的内容块里寻找第一张图片
            Element firstImage = contentElement.select("img").first();
            if (firstImage != null) {
                coverImageUrl = firstImage.absUrl("src");
            }
        }

        // 如果内容区域没找到图片，尝试从meta标签里找
        if (coverImageUrl.isEmpty()) {
            coverImageUrl = doc.select("meta[property=og:image]").attr("content");
        }

        // 抓取简介
        String summary = doc.select("meta[name=description]").attr("content");

        ScrapedNewsResponse response = new ScrapedNewsResponse();
        response.setTitle(title);
        response.setContent(content);
        response.setSummary(summary.isEmpty() ? title : summary);
        response.setCoverImageUrl(coverImageUrl);

        return response;
    }

    /**
     * 辅助方法：尝试多个选择器，返回第一个匹配到的元素的文本
     * @param doc Jsoup文档对象
     * @param selectors 选择器数组
     * @param mode "text" 或 "html"
     * @return 匹配到的文本或空字符串
     */
    private String findFirstMatch(Document doc, String[] selectors, String mode) {
        for (String selector : selectors) {
            Element element = doc.select(selector).first();
            if (element != null) {
                return mode.equals("html") ? element.html() : element.text();
            }
        }
        return ""; // 如果都找不到，返回空
    }

    /**
     * 辅助方法：尝试多个选择器，返回第一个匹配到的Element元素
     * @param doc Jsoup文档对象
     * @param selectors 选择器数组
     * @return 匹配到的Element或null
     */
    private Element findFirstElement(Document doc, String[] selectors) {
        for (String selector : selectors) {
            Element element = doc.select(selector).first();
            if (element != null) {
                return element;
            }
        }
        return null;
    }
}