package com.News.demo.news.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.News.demo.news.Model.newsModel;

import jakarta.transaction.Transactional;

@Repository
public interface newsRepo  extends JpaRepository<newsModel,Long>{

	List<newsModel> findAll();
	newsModel findByTitleContaining(String title);
	@Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE news_model", nativeQuery = true)
    void truncateTable();
}
