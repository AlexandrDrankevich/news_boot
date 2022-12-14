package com.example.news_boot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.news_boot.entity.News;
import com.example.news_boot.service.NewsService;
import com.example.news_boot.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class BasePageController {
    private static final String newsOnPageAttribute = "news";
    @Autowired
    private NewsService newsService;

    @RequestMapping("/base_page")
    public String goToBasePage(HttpServletRequest request, Model model) {
        List<News> latestNews;
        int countNews = 5;
        try {
            latestNews = newsService.latestList(countNews);
            model.addAttribute(newsOnPageAttribute, latestNews);
            return "baseLayout";
        } catch (ServiceException e) {
            return "error";
        }
    }
}
