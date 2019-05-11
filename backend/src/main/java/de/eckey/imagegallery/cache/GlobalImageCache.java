package de.eckey.imagegallery.cache;

import de.eckey.imagegallery.data.Image;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class GlobalImageCache implements ImageCache{
    private final List<Image> images = new ArrayList<>();

    public void addImage(Image image){
        images.add(image);
    }

    public void addImages(Collection<Image> images){
        this.images.addAll(images);
    }

    public Optional<Image> getNext(){
        if(images.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(images.remove(0));
    }
}
