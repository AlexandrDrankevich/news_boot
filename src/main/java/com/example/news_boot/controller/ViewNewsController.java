package com.example.news_boot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.news_boot.entity.News;
import com.example.news_boot.service.NewsService;
import com.example.news_boot.service.ServiceException;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ViewNewsController {
    private static final String newsAttribute = "news";
    private static final String typeOfPresentation = "viewNews";
    private static final String presentationTypeAttribute = "presentation";
    @Autowired
    private NewsService newsService;

    @RequestMapping("/viewNews/{id}")
    public String viewNews(@PathVariable("id") String id, HttpServletRequest request) {
        try {
            News news = newsService.findById(Integer.parseInt(id));
            request.setAttribute(newsAttribute, news);
            request.setAttribute(presentationTypeAttribute, typeOfPresentation);
            return "baseLayout";
        } catch (ServiceException e) {
            return "error";
        }
    }
}
