package de.eckey.imagegallery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ImagegalleryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImagegalleryApplication.class, args);
	}

}
