package de.eckey.imagegallery.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:mail.properties", encoding = "UTF-8")
public class EMailProperties {

    @Value("${email}")
    private String email;
    @Value("${folder}")
    private String folder;
    @Value("${password}")
    private String password;


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFolder() {
        return folder;
    }
}
