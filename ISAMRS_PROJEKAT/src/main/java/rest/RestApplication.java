package rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


import rest.dto.QRCodeReaderDTO;



@EnableScheduling
@SpringBootApplication
@EnableAsync
public class RestApplication {

	public static void main(String[] args) {
		QRCodeReaderDTO qrc = new QRCodeReaderDTO();
		SpringApplication.run(RestApplication.class, args);
	}

}
