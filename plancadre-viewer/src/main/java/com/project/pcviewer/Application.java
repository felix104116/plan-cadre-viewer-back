package com.project.pcviewer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.lang.reflect.Field;
import java.nio.charset.Charset;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
		System.setProperty("file.encoding","UTF-8");
		Field charset = Charset.class.getDeclaredField("defaultCharset");
		charset.setAccessible(true);
		charset.set(null,null);
		SpringApplication.run(Application.class, args);
	}

}
