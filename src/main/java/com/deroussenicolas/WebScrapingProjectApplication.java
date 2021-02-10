package com.deroussenicolas;

import java.util.Calendar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.deroussenicolas.batch.MyTask;

import java.util.Timer;
@SpringBootApplication
public class WebScrapingProjectApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(WebScrapingProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Calendar today = Calendar.getInstance();
		Timer timer = new Timer(); 
		timer.schedule(new MyTask(), today.getTime(), 1000*5); //  every 5s	
		//  every hours		1000*60*60
	}
	
}
