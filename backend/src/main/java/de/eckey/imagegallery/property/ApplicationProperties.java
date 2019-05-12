package de.eckey.imagegallery.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
public class ApplicationProperties {

    @Value("${frontend.viewTime}")
    private int viewTime;

    public int getViewTime() {
        return  viewTime;
    }
}
