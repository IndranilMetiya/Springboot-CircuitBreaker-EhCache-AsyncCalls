package com.implement.examples.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.implement.examples.config.AsyncConfig;
import com.implement.examples.config.CacheConfig;

@Service
public class CallApiService {
	
	@Autowired
	AsyncConfig config;
	
	@Autowired
	CacheConfig cacheConfig;
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<String> callDemoApi1() {
		
		String url = "http://localhost:8080/demoApi1";
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
		
		cacheConfig.put("key1", response.getBody().toString());
		return response.getBody();

	}
	
	
	public String callDemoApi2() {
		String url = "http://localhost:8080/demoApi2";
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		String s1= response.getBody();
		System.err.println(Thread.currentThread().getName());
		cacheConfig.put("key2", s1);
		return s1; //.completedFuture(s1);

	}
	
	public String callDemoApi3() {
		String url = "http://localhost:8080/demoApi3";
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		String s1 = response.getBody();

		System.err.println(Thread.currentThread().getName());
		cacheConfig.put("key3", s1);
		return s1;// CompletableFuture.completedFuture(s1);
	}
	
	
	
	public void asyncCalls() throws InterruptedException, ExecutionException {
	    // Initiate the async calls without blocking
	   CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> callDemoApi3(), config.taskExecutor());
	  CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> callDemoApi2(), config.taskExecutor());

	    // Use thenAccept to process the results once they are available
	    future1.thenAccept(result -> System.err.println(result + "======s1====="));
	    future2.thenAccept(result -> System.err.println(result + "==+++++++====s2====+++++++++++=="));
	    Thread.sleep(12000);
	    System.err.println("key1 --- >"+cacheConfig.get("key1") + "   key2 ------> " + cacheConfig.get("key2")+ "    key3------->  "+ cacheConfig.get("key3"));
	    // Wait for all futures to complete
	    Thread.sleep(16000);
	    System.err.println("key1 --- >"+cacheConfig.get("key1") + "   key2 ------> " + cacheConfig.get("key2")+ "    key3------->  "+ cacheConfig.get("key3"));

	    CompletableFuture.allOf(future1, future2).join();
	}
	 
	
    

}
