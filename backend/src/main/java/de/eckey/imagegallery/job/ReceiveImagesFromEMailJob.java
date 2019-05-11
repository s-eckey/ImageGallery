package de.eckey.imagegallery.job;

import de.eckey.imagegallery.cache.ImageCache;
import de.eckey.imagegallery.email.EMailReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class ReceiveImagesFromEMailJob {
    Logger logger = LoggerFactory.getLogger(ReceiveImagesFromEMailJob.class);

    @Autowired
    private EMailReceiver eMailReceiver;
    @Autowired
    private ImageCache imageCache;

    @Scheduled(cron = "${job.receiveimagesfromemail.cron:-}")
    public void run(){
        logger.debug("start job...");
        imageCache.addImages(eMailReceiver.getNewImages());
        logger.debug("finished");
    }

}
