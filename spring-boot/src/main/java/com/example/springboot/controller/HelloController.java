package com.example.springboot.controller;

import com.example.springboot.config.AppConfig;


import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

	private final AppConfig appConfig;
	
	public HelloController(AppConfig appConfig)
	{
	  this.appConfig = appConfig;
	}

	@GetMapping("/")
	public String index() {
		System.out.println("content.getContent()");
		return appConfig.getEnvironment().getProperty("content");
	}

}
