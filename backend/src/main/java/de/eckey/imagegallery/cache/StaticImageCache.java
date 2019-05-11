package de.eckey.imagegallery.cache;

import de.eckey.imagegallery.data.Image;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.*;

public class StaticImageCache implements ImageCache {
    private final List<Image> images;
    Logger logger = LoggerFactory.getLogger(StaticImageCache.class);

    public StaticImageCache() throws Exception {
        logger.debug("loading images...");
        images = Arrays.asList(getImage("/staticImages/1.jpg")
                , getImage("/staticImages/2.jpg")
                , getImage("/staticImages/3.jpg")
                , getImage("/staticImages/4.jpg")
                , getImage("/staticImages/5.jpg")
        );
        logger.debug("done");
    }

    private static Image getImage(String name) throws Exception {
        InputStream inputStream = new ClassPathResource(name).getInputStream();
        return new Image(name, IOUtils.toByteArray(inputStream), StringUtils.substringAfterLast(name, "."));
    }

    @Override
    public void addImage(Image image) {
        // nop
    }

    @Override
    public void addImages(Collection<Image> images) {
        // nop
    }

    @Override
    public Optional<Image> getNext() {
        try {
            return Optional.of(images.get(new Random().nextInt(6)));
        } catch (ArrayIndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }
}
