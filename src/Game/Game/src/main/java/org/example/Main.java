package org.example;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main(String[] args) {

        /*Размер поля, количество препятствий и количество врагов вводятся в программу с помощью параметров командной строки
    (их наличие гарантируется):
    $ java -jar game.jar --enemiesCount=2 --wallsCount=10 --size=6 --profile=production*/

        if (args.length == 4) {
            try {
                int countPersecutors = Integer.parseInt(args[0].split("=")[1]);
                int countWalls = Integer.parseInt(args[1].split("=")[1]);
                int size = Integer.parseInt(args[2].split("=")[1]);
                String mod = args[3].split("=")[1];
                if (mod.equalsIgnoreCase("production")) {
                    Scene test1 = Scene.getNewScene(size, countWalls, countPersecutors);
                    test1.start();
                } else if (mod.equalsIgnoreCase("development")) {

                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else {
            System.out.println("Incorrect count argument");
        }
    }
}
