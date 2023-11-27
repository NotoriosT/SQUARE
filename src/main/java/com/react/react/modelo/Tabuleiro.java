package com.react.react.modelo;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {
private List<Player>players=new ArrayList<>();

private List<Enemy>enemies=new ArrayList<>();
    private  int width= 800;

    private  int height=800;

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
}
