package com.rohan.finance_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class FinanceTrackerApplication {

	public static void main(String[] args) {
//		Used for logging to see what is the timezone set
//		System.out.println("JVM TZ = " + TimeZone.getDefault().getID());

		SpringApplication.run(FinanceTrackerApplication.class, args);
	}

}
