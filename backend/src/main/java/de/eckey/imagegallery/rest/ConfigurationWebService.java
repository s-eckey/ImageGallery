package de.eckey.imagegallery.rest;

import de.eckey.imagegallery.property.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController()
@RequestMapping("rest/configuration")
public class ConfigurationWebService {
    private Logger logger = LoggerFactory.getLogger(ConfigurationWebService.class);

    @Autowired
    private ApplicationProperties applicationProperties;

    private final Random random = new Random();

    @GetMapping("/viewTime")
    public int getViewTime() {
        final int viewTime = random.nextInt(applicationProperties.getViewTime());
        logger.debug("generated view time: {}", viewTime);
        return viewTime;
    }
}
