package de.eckey.imagegallery.cache;

import de.eckey.imagegallery.data.Image;

import java.util.Collection;
import java.util.Optional;

public interface ImageCache {

    void addImage(Image image);

    void addImages(Collection<Image> images);

    Optional<Image> getNext();
}
