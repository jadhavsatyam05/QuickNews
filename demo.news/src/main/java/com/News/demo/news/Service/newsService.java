package com.News.demo.news.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.News.demo.news.Model.NewsResponse;
import com.News.demo.news.Model.newsModel;
import com.News.demo.news.Repo.newsRepo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class newsService {
	private String apikey="1f84128afee446e1aa625110620e9694";
	private final RestTemplate restTemplate;
	private  final newsRepo newsrepo;
	 @PersistenceContext
	    private EntityManager entityManager;
	@Autowired
	public newsService(RestTemplate restTemplate,newsRepo newsrepo) {
		
		this.restTemplate = restTemplate;
		this.newsrepo=newsrepo;
	}
	
	public newsModel consumeApi() {
		return restTemplate.getForObject("https://newsapi.org/v2/everything?q=keyword&apiKey=1f84128afee446e1aa625110620e9694", newsModel.class);
	}
	
	public NewsResponse getTopHeadlines() {
		String url="https://newsapi.org/v2/top-headlines?country=us&apiKey="+apikey;
//		String url="https://newsapi.org/v2/everything?q=bitcoin&apiKey=1f84128afee446e1aa625110620e9694";
		 NewsResponse response=restTemplate.getForObject(url,NewsResponse.class );
		 if(response.getArticles()!=null &&response!=null) {
			 for(newsModel articles:response.getArticles()) {
				 newsrepo.save(articles);
			 } 
		 }
		return response; 
	}
	public NewsResponse getcategoryNews(String query) {
		System.out.println("Query is "+query);
		String url="https://newsapi.org/v2/top-headlines?category="+query+"&apiKey="+apikey;
		 NewsResponse response=restTemplate.getForObject(url,NewsResponse.class );
		 if(response.getArticles()!=null &&response!=null) {
			 for(newsModel articles:response.getArticles()) {
				 newsrepo.save(articles);
			 } 
		 }
		return response; 
	}
	  public List<newsModel> getAllNews() {
        return newsrepo.findAll();
    }
	  @Transactional
	  public void truncateTable() {
	        try {
	            // Execute the TRUNCATE SQL statement
	            entityManager.createNativeQuery("TRUNCATE TABLE news_model").executeUpdate(); 
	           
	        } catch (Exception e) {
	            e.printStackTrace();
	            
	        }
	    }
}
