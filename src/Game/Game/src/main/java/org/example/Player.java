package org.example;

public class Player {

    // 0 - значение свободной клетки
    // 1 - значение препятствия в клетке
    // 2 - значение игрока
    // 3 - значение преследователей
    // 4 - значение клетки с выигрышем
    private int cordHeight; // row
    private int cordWidth; // col

    public Player(Matrix scene, int sizeScene) {
        while (true) {
            cordHeight = (int) (Math.random()*sizeScene);
            cordWidth = (int) (Math.random()*sizeScene);
            if (scene.getOneCell(cordHeight, cordWidth) == 0) {
                scene.setOneCell(cordHeight, cordWidth, 2);
                break;
            }
        }
    }

    public void setCordHeight(int cordHeight) {
        this.cordHeight = cordHeight;
    }

    public void setCordWidth(int cordWidth) {
        this.cordWidth = cordWidth;
    }

    public int getCordHeight() {
        return cordHeight;
    }

    public int getCordWidth() {
        return cordWidth;
    }
}
