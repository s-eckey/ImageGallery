package de.eckey.imagegallery.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfiguration {

    @Bean
    public EMailReceiver eMailReceiver(){
        return new EMailReceiver();
    }

}
