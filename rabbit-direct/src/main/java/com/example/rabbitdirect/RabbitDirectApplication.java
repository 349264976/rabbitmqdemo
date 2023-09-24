package com.example.rabbitdirect;

import com.example.rabbitdirect.config.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitDirectApplication implements ApplicationRunner {

	@Autowired
	private MessageService messageService;

	public static void main(String[] args) {
		SpringApplication.run(RabbitDirectApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		messageService.sendMessage();
	}
}
