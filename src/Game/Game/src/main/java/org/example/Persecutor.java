package org.example;

public class Persecutor {
    private int cordHeight; // row
    private int cordWidth; // col

    public Persecutor(Matrix scene, int sizeScene) {
        while (true) {
            cordHeight = (int) (Math.random() * sizeScene);
            cordWidth = (int) (Math.random() * sizeScene);

            if (scene.getOneCell(cordHeight, cordWidth) == 0 &&
                    Generation.checkAround(scene, cordHeight, cordWidth, 2, sizeScene)) {
                scene.setOneCell(cordHeight, cordWidth, 3);
                break;
            }

        }
    }

    public int getCordHeight() {
        return cordHeight;
    }

    public int getCordWidth() {
        return cordWidth;
    }

    public void setCord(int cordHeight, int cordWidth) {
        this.cordHeight = cordHeight;
        this.cordWidth = cordWidth;
    }

}
