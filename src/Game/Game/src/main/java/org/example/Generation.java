package org.example;

public class Generation {
    // 0 - значение свободной клетки
    // 1 - значение препятствия в клетке
    // 2 - значение игрока
    // 3 - значение преследователей
    // 4 - значение клетки с выигрышем

    private static Matrix generationEmptyScene(int sizeScene) {
        Matrix scene = new Matrix(sizeScene);
        scene.fillMatrix(0);
        return scene;
    }

    public static boolean checkAround(Matrix scene, int  cordHeight, int cordWidth, int checkeredCount, int sizeScene) {
        if (cordHeight - 1 >= 0 && scene.getOneCell(cordHeight - 1, cordWidth) == checkeredCount) return false;
        if (cordWidth - 1 >= 0 && scene.getOneCell(cordHeight, cordWidth - 1) == checkeredCount) return false;
        if (cordHeight + 1 < sizeScene && scene.getOneCell(cordHeight + 1, cordWidth) == checkeredCount) return false;
        if (cordWidth + 1 < sizeScene && scene.getOneCell(cordHeight, cordWidth + 1) == checkeredCount) return false;
        return true;
    }

    public static int[] putWinPoint(Matrix scene, int sizeScene) {
        while (true) {
            int cordHeightWin = (int) (Math.random() * sizeScene);
            int cordWidthWin = (int) (Math.random() * sizeScene);
            if (scene.getOneCell(cordHeightWin, cordWidthWin) == 0) {
                scene.setOneCell(cordHeightWin, cordWidthWin, 4);
                return new int[]{cordHeightWin, cordWidthWin};
            }
        }
    }

    public static void putWalls(Matrix scene, int sizeScene, int countsWall) {
        for (int i = 0; i < countsWall; ) {
            int cordHeightWall = (int) (Math.random() * sizeScene);
            int cordWidthWall = (int) (Math.random() * sizeScene);
            if (scene.getOneCell(cordHeightWall, cordWidthWall) == 0) {
                scene.setOneCell(cordHeightWall, cordWidthWall, 1);
                ++i;
            }
        }
    }

    public static Matrix generationScene(int sizeScene, int countsWall, int countsPersecutor) {
        if (sizeScene <= 3 || countsPersecutor < 0 || countsWall < 0 ||
                ((sizeScene * sizeScene) / 2) < (countsWall + countsPersecutor + 1))
            throw new IllegalParametersException("Fatal error -Incorrect size scene");

        return generationEmptyScene(sizeScene);
    }


}
