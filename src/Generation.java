public class Generation {
    // 0 - значение свободной клетки
    // 1 - значение препятствия в клетке
    // 2 - значение игрока
    // 3 - значение преследователей
    // 4 - значение клетки с выигрышем

    public static Matrix generationScene(int sizeScene, int countsWall, int countsPersecutor) {
        if (sizeScene < 3 || countsWall >= (sizeScene * sizeScene) + 1 + (2 * countsPersecutor) + 2)
            throw new IllegalArgumentException("Fatal error -Incorrect size scene");
        Matrix scene = new Matrix(sizeScene);
        scene.fillMatrix(0);
        int scenePointWin = (int) (Math.random()*sizeScene);
        scene.setOneCell(scenePointWin, scenePointWin, 4);
        /*
        * здесь нужно реализовать алгоритм заполнения
        * */
        for (int i = 0; i < countsWall;) {
            int sceneRow = (int) (Math.random()*sizeScene);
            int sceneCol = (int) (Math.random()*sizeScene);
            if (scene.getOneCell(sceneRow, sceneCol) == 0) {
                scene.setOneCell(sceneRow, sceneCol, 1);
                i++;
            }
        }

        /*
         * пока что стоит затычка, заполняющая рандомно рандомные ячейки в которых нули
         * */
        return scene;
    }
}
