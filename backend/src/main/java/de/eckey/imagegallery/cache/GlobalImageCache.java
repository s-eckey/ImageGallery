package de.eckey.imagegallery.cache;

import de.eckey.imagegallery.data.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class GlobalImageCache implements ImageCache {
    private Logger logger = LoggerFactory.getLogger(GlobalImageCache.class);
    private final List<Image> images = new ArrayList<>();

    public void addImage(Image image) {
        images.add(image);
    }

    public void addImages(Collection<Image> images) {
        this.images.addAll(images);
    }

    public Optional<Image> getNext() {
        if (images.isEmpty()) {
            logger.debug("no images left");
            return Optional.empty();
        }
        logger.debug("images remaining: {}", images.size() - 1);
        return Optional.of(images.remove(0));
    }
}
