package de.eckey.imagegallery.data;

public class Image {
    private String name;
    private byte[] imageData;
    private String fileEnding;

    public Image(){

    }

    public Image(String name, byte[] imageData, String fileEnding){
        this.name = name;
        this.imageData = imageData;
        this.fileEnding = fileEnding;
    }

    public String getName() {
        return name;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public String getFileEnding() {
        return fileEnding;
    }
}
