package de.eckey.imagegallery.rest;

import de.eckey.imagegallery.cache.ImageCache;
import de.eckey.imagegallery.data.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("rest/image")
public class ImageWebService {
    Logger logger = LoggerFactory.getLogger(ImageWebService.class);

    @Autowired
    private ImageCache imageCache;

    @GetMapping("/next")
    public Image getImages() {

        final Image image = imageCache.getNext().orElse(null);
        if (image != null){
            logger.debug("getImage with name: {}.{}", image.getName(), image.getFileEnding());
        }
        return image;
    }
}
