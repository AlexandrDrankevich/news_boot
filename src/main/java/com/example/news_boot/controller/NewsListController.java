package com.example.news_boot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.news_boot.entity.News;
import com.example.news_boot.service.NewsService;
import com.example.news_boot.service.ServiceException;

import java.util.List;

@Controller
@SessionAttributes({"newsCount", "pageNumber"})
public class NewsListController {
    private static final String newsOnPageAttribute = "news";
    private static final String presentationTypeAttribute = "presentation";
    private static final String newsCountParam = "newsCount";
    private static final String pageNumberParam = "pageNumber";
    private static final String pageCountAttribute = "pageCount";
    private static final String typeOfPresentation = "newsList";
    private static final String sessionNewsCounAttribute = "newsCount";
    private static final String sessionPageAttribute = "pageNumber";
    @Autowired
    private NewsService newsService;

    @RequestMapping("/newsList")
    public String showNewsList(Model model,
                               @RequestParam(value = newsCountParam, required = false) String newsCount,
                               @RequestParam(value = pageNumberParam, required = false) String page,
                               @SessionAttribute(value = sessionPageAttribute, required = false) String sessionPage,
                               @SessionAttribute(value = sessionNewsCounAttribute, required = false) String sessionNewsCount) {
        List<News> newsListOnPage;
        Integer pageNumber = getPageNumber(page, model, sessionPage);
        if (newsCount != null) {
            model.addAttribute(sessionNewsCounAttribute, newsCount);
            sessionNewsCount = newsCount;
        }
        try {
            newsListOnPage = newsService.list(pageNumber, sessionNewsCount);
            model.addAttribute(pageCountAttribute, newsService.getPageCount(sessionNewsCount));
            model.addAttribute(newsOnPageAttribute, newsListOnPage);
            model.addAttribute(presentationTypeAttribute, typeOfPresentation);
            return "baseLayout";
        } catch (ServiceException e) {
            return "error";
        }
    }

    private Integer getPageNumber(String page, Model model, String sessionPage) {
        Integer pageNumberDefault = 1;
        if (page != null) {
            model.addAttribute(sessionPageAttribute, Integer.valueOf(page));
            return Integer.valueOf(page);
        }
        if (sessionPage == null) {
            return pageNumberDefault;
        }
        return Integer.valueOf(sessionPage);
    }
}
