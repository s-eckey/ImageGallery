package de.eckey.imagegallery.rest;

import de.eckey.imagegallery.cache.ImageCache;
import de.eckey.imagegallery.data.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("rest/image")
public class ImageWebService {

    @Autowired
    private ImageCache imageCache;

    @GetMapping("/next")
    public Image getImages(){
        return imageCache.getNext().orElse(null);
    }
}
