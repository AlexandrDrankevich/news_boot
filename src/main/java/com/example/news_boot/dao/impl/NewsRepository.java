package com.example.news_boot.dao.impl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.news_boot.entity.News;

public interface NewsRepository extends  JpaRepository<News,Integer> {
	public  List<News> findAllByOrderByNewsDateDesc(); 

}
