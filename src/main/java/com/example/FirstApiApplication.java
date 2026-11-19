package com.example;

import com.example.utils.ExcelTableManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class FirstApiApplication {
	//static List<Map<String, String>> readData;
	public static void main(String[] args) {
		SpringApplication.run(FirstApiApplication.class, args);
		//readData = ExcelTableManager.readTable("StudentData.xlsx");
	}

}
