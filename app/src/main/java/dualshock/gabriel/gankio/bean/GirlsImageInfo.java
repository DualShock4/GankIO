package dualshock.gabriel.gankio.bean;


import java.io.Serializable;

public class GirlsImageInfo implements Serializable{
    private int width;
    private int height;
    private String url;

    public GirlsImageInfo(String url) {
        this.url = url;
        width = 0;
        height = 0;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
