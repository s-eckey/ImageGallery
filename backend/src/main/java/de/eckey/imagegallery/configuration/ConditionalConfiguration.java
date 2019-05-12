package de.eckey.imagegallery.configuration;

import de.eckey.imagegallery.cache.GlobalImageCache;
import de.eckey.imagegallery.cache.StaticImageCache;
import de.eckey.imagegallery.job.ReceiveImagesFromEMailJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ConditionalConfiguration {
    Logger logger = LoggerFactory.getLogger(ConditionalConfiguration.class);

    @Bean
    @Profile("staticImageCache")
    public StaticImageCache staticImageCache() throws Exception{
        logger.debug("injecting staticImageCache");
        return new StaticImageCache();
    }

    @Bean
    @ConditionalOnMissingBean(StaticImageCache.class)
    public GlobalImageCache globalImageCache(){
        logger.debug("injecting globalImageCache");
        return new GlobalImageCache();
    }

    @Bean
    @ConditionalOnBean(GlobalImageCache.class)
    public ReceiveImagesFromEMailJob receiveImagesFromEMailJob(){
        logger.debug("injecting receiveImagesFromEMailJob");
        return new ReceiveImagesFromEMailJob();
    }
}
