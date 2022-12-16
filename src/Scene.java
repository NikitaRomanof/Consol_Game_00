import java.util.ArrayList;
import java.util.List;

public class Scene {

    // 0 - значение свободной клетки
    // 1 - значение препятствия в клетке
    // 2 - значение игрока
    // 3 - значение преследователей
    // 4 - значение клетки с выигрышем
    private Matrix scene;
    private final List<Persecutor> persecutors = new ArrayList<>();
    private Player player;

    /*Размер поля, количество препятствий и количество врагов вводятся в программу с помощью параметров командной строки
    (их наличие гарантируется):
    $ java -jar game.jar --enemiesCount=10 --wallsCount=10 --size=30 --profile=production*/

    //функционал чтения из файла и парсинг параметров реализовать отдельно.
    public Scene(int sizeScene, int countsWall, int countsPersecutor) {
        scene = Generation.generationScene(sizeScene, countsWall, countsPersecutor);
        player = new Player(scene, sizeScene);

        for (int i = 0; i < countsPersecutor; i++) {
            persecutors.add(new Persecutor(scene, sizeScene));
        }
    }

    public void start() {
        scene.printMatrix();
    }
}
