package com.implement.examples.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.implement.examples.service.CallApiService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
public class ExampleController {
    
    @Autowired
    CallApiService service;
    
	
    
    @GetMapping("/getdemoApi1")
    @CircuitBreaker(name = "api1CircuitBreaker", fallbackMethod = "api1Fallback")
    public List<String> getData() {
              List<String> sList = service.callDemoApi1();
            return sList;
    
    }
    
//    @GetMapping("/getdemoApi2")
//    @CircuitBreaker(name = "api1CircuitBreaker", fallbackMethod = "api1Fallback")
//    public String getData1() {
//    	return service.callDemoApi3();
//    }
//    
//    @GetMapping("/getdemoApi3")
//    @CircuitBreaker(name = "api1CircuitBreaker", fallbackMethod = "api1Fallback")
//    public String getData2() {
//    	return service.callDemoApi2();
//             
//    
//    }
    
    @GetMapping("/getAsync")
    @CircuitBreaker(name = "api1CircuitBreaker", fallbackMethod = "api3Fallback")
    public void asyncData() {
    	 try {
			service.asyncCalls();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		} catch (ExecutionException e) {
			
			e.printStackTrace();
		}
             
    
    }
    
    public List<String> api1Fallback(Throwable throwable) {
        List<String> sList = new ArrayList<>();
        sList.add("Fallback ------------- method executed");
        return sList;
    }
    
    public void api3Fallback(Throwable throwable) {
        List<String> sList = new ArrayList<>();
        sList.add("Fallback ------async------- method executed");
    }
}
