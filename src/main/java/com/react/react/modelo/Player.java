package com.react.react.modelo;

public class Player {

    private  int score;

    private  String id;

    public String getId() {
        return id;
    }

    String tipo="player";

    public String getTipo() {
        return tipo;
    }

    public void setId(String id) {
        this.id = id;
    }

    private int x;
     private  int y;
     private int tamanho=30;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }
}
