package com.example.news_boot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.news_boot.dao.impl.NewsRepository;
import com.example.news_boot.entity.News;
import com.example.news_boot.service.NewsService;
import com.example.news_boot.service.ServiceException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
	@Autowired
	private NewsRepository newsRepository;

	@Override
	@Transactional
	public List<News> latestList(int count) throws ServiceException {
		try {
			List<News> latestNews = newsRepository.findAllByOrderByNewsDateDesc();
					if (latestNews.isEmpty()) {
				return null;
			}
			if(latestNews.size()>count) {
				latestNews.subList(count,latestNews.size()).clear();
			}
			
			return latestNews;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public List<News> list(Integer pageNumber, String newsCountOnPage) throws ServiceException {
		String newsCount = checkNewsCount(newsCountOnPage);
		try {
			List<News> allNewsList = newsRepository.findAllByOrderByNewsDateDesc();
			return getNewsOnPage(allNewsList, pageNumber, newsCount);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public News findById(int id) throws ServiceException {
		try {
			return newsRepository.findById(id).get();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public void save(News news) throws ServiceException {
		try {
			newsRepository.save(news);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public List<Integer> getPageCount(String newsCountOnPage) throws ServiceException {
		String newsCount = checkNewsCount(newsCountOnPage);
		try {
			return createPageCountList(newsRepository.findAll(), newsCount);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public void delete(String[] idNews) throws ServiceException {
		List<Integer> listId = Arrays.stream(idNews).map(x -> Integer.valueOf(x)).toList();
		try {
			newsRepository.deleteAllById(listId);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	private List<Integer> createPageCountList(List<News> allNewsList, String newsCount) {
		double numberNews = Double.parseDouble(newsCount);
		int number = (int) (Math.ceil(allNewsList.size() / numberNews));
		List<Integer> pageCount = new ArrayList<Integer>();
		for (int i = 1; i <= number; i++) {
			pageCount.add(i);
		}
		return pageCount;
	}

	private List<News> getNewsOnPage(List<News> allNewsList, Integer pageNumber, String newsCount) {
		int numberNews = Integer.valueOf(newsCount);
		List<News> newsListOnPage = new ArrayList<News>();
		if (allNewsList.isEmpty()) {
			return null;
		}
		int startNews = pageNumber * numberNews - numberNews;
		int finishNews = pageNumber * numberNews;
		if (finishNews > allNewsList.size()) {
			finishNews = allNewsList.size();
		}
		for (int i = startNews; i < finishNews; i++) {
			newsListOnPage.add(allNewsList.get(i));
		}
		if (newsListOnPage.isEmpty() && pageNumber > 1) {
			return getNewsOnPage(allNewsList, pageNumber - 1, newsCount);
		}
		return newsListOnPage;

	}

	private String checkNewsCount(String newsCountOnPage) {
		String newsCount = "5";
		if (newsCountOnPage != null) {
			newsCount = newsCountOnPage;
		}
		return newsCount;
	}

}