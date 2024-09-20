package com.News.demo.news.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.News.demo.news.Model.NewsResponse;
import com.News.demo.news.Model.newsModel;
import com.News.demo.news.Repo.newsRepo;
import com.News.demo.news.Service.newsService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Controller
public class NewsController {
	 private final newsService newsservice;
	public  String title1;
	
	 @Autowired
	public NewsController(newsService newsservice) {
		this.newsservice = newsservice;
		
	}

	
	
	
	 
	 
	 @GetMapping("/home")
	    public String viewAllNews(Model model) {
			
			
			  if(title1 != null) {
				  newsservice.truncateTable();
			  System.out.println("the title name is "+title1);
			  newsservice.getcategoryNews(title1); 
			  List <newsModel> newsList = newsservice.getAllNews(); 
			  model.addAttribute("newsList", newsList); 
			  title1=null;
			  System.out.println(title1);
			  return "home"; 
			  }
			 
			  
			  newsservice.truncateTable();
		 	newsservice.getTopHeadlines();
	        List<newsModel> newsList = newsservice.getAllNews();
	        model.addAttribute("newsList", newsList);
	        return "home"; 
	    }
	 @PostMapping("/title")
	    public String titleSearch(@RequestParam String  title) {
		 	title1=title;
		 	System.out.println("the title name is "+title1);
		 	
	        return "redirect:/home"; 
	    }
	 
}
