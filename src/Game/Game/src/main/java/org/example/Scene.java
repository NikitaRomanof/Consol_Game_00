package org.example;


import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


public class Scene {

    // 0 - значение свободной клетки
    // 1 - значение препятствия в клетке
    // 2 - значение игрока
    // 3 - значение преследователей
    // 4 - значение клетки с выигрышем
    private final Matrix scene;
    private final List<Persecutor> persecutors;
    private final Player player;
    private final int cordHeightWin;
    private final int cordWidthWin;
    private final int sizeScene;

    private char emptyCellChar;
    private char wallChar;
    private char playerChar;
    private char persecutorChar;
    private char winCellChar;

    private String emptyCellColor;
    private String wallCharColor;
    private String playerCharColor;
    private String persecutorCharColor;
    private String winCellCharColor;



    private final static String CONFIG = "/config.conf";



    //функционал чтения из файла и парсинг параметров реализовать отдельно.
    private Scene(int sizeScene, int countsWall, int countsPersecutor) {
        this.sizeScene = sizeScene;
        scene = Generation.generationScene(sizeScene, countsWall, countsPersecutor);
        player = new Player(scene, sizeScene);
        persecutors = new ArrayList<>();
        for (int i = 0; i < countsPersecutor; i++) {
            persecutors.add(new Persecutor(scene, sizeScene));
        }
        int[] winCords = Generation.putWinPoint(scene, sizeScene);
        cordHeightWin = winCords[0];
        cordWidthWin = winCords[1];

        Generation.putWalls(scene, sizeScene, countsWall);
    }

    public static Scene getNewScene(int sizeScene, int countsWall, int countsPersecutor) {
        Scene scene = new Scene(sizeScene, countsWall, countsPersecutor);
        while (!scene.findPath()) {
            scene = getNewScene(sizeScene, countsWall, countsPersecutor);
        }
        scene.backEmpty();
        return scene;
    }

    private void backEmpty() {
        for (int i = 0; i < sizeScene; i++) {
            for (int j = 0; j < sizeScene; j++) {
                if (scene.getOneCell(i, j) == -1) scene.setOneCell(i, j, 0);
            }
        }
    }

    private boolean findPath() {

        if(!Generation.checkAround(scene, cordHeightWin, cordWidthWin, 2, sizeScene)) {
            return true;
        }
        int playerCordHeight = player.getCordHeight();
        int playerCordWidth = player.getCordWidth();

        if (playerCordHeight + 1 < sizeScene &&
                scene.getOneCell(playerCordHeight + 1, playerCordWidth) == 0)
            scene.setOneCell(playerCordHeight + 1, playerCordWidth, -1);

        if (playerCordWidth + 1 < sizeScene &&
                scene.getOneCell(playerCordHeight, playerCordWidth + 1) == 0)
            scene.setOneCell(playerCordHeight, playerCordWidth + 1, -1);

        if (playerCordHeight - 1 >= 0 &&
                scene.getOneCell(playerCordHeight - 1, playerCordWidth) == 0)
            scene.setOneCell(playerCordHeight - 1, playerCordWidth, -1);

        if (playerCordWidth - 1 >= 0 &&
                scene.getOneCell(playerCordHeight, playerCordWidth - 1) == 0)
            scene.setOneCell(playerCordHeight, playerCordWidth - 1, -1);


        int flagCheck = 0;
        while (check() > flagCheck) {
            flagCheck = check();
            searchValid();
            if (!Generation.checkAround(scene, cordHeightWin, cordWidthWin, -1, sizeScene))
                return true;
        }
        return false;
    }

    private int check(){
        int countPath = 0;
        for(int i = 0; i < sizeScene; i++){
            for (int j = 0; j < sizeScene; j++){
                if (scene.getOneCell(i, j) == -1)
                    countPath++;
            }
        }
        return countPath;
    }

    private void searchValid() {
        for (int y = 0; y < sizeScene; y++){
            for (int x = 0; x < sizeScene; x++){
                if(y + 1 < sizeScene && scene.getOneCell(y + 1, x) == -1 && scene.getOneCell(y, x) == 0)
                    scene.setOneCell(y, x, -1);
                if(x + 1 < sizeScene && scene.getOneCell(y, x + 1) == -1 && scene.getOneCell(y, x) == 0)
                    scene.setOneCell(y, x, -1);
                if(y - 1 > 0 && scene.getOneCell(y - 1, x) == -1 && scene.getOneCell(y, x) == 0)
                    scene.setOneCell(y, x, -1);
                if(x - 1 > 0 && scene.getOneCell(y, x - 1) == -1 && scene.getOneCell(y, x) == 0)
                    scene.setOneCell(y, x, -1);
            }
        }
    }

    public void start() {
        printScene();
        Scanner scan = new Scanner(System.in);

        while (true) {
            String line = scan.nextLine();
            if (line.equalsIgnoreCase("w")) {
                if (player.getCordHeight() - 1 >= 0 && (scene.getOneCell(player.getCordHeight() - 1, player.getCordWidth()) == 0 ||
                        scene.getOneCell(player.getCordHeight() - 1, player.getCordWidth()) == 4)) {
                    if (scene.getOneCell(player.getCordHeight() - 1, player.getCordWidth()) == 4) {
                        System.out.println("You are WIN!");
                        return;
                    }
                    scene.setOneCell(player.getCordHeight(), player.getCordWidth(), 0);
                    player.setCordHeight(player.getCordHeight() - 1);
                    scene.setOneCell(player.getCordHeight(), player.getCordWidth(), 2);
                    printScene();
                }

            } else if (line.equalsIgnoreCase("d")) {
                if (player.getCordWidth() + 1 < sizeScene && (scene.getOneCell(player.getCordHeight(), player.getCordWidth() + 1) == 0 ||
                        scene.getOneCell(player.getCordHeight(), player.getCordWidth() + 1) == 4)) {
                    if (scene.getOneCell(player.getCordHeight(), player.getCordWidth() + 1) == 4) {
                        System.out.println("You are WIN!");
                        return;
                    }
                    scene.setOneCell(player.getCordHeight(), player.getCordWidth(), 0);
                    player.setCordWidth(player.getCordWidth() + 1);
                    scene.setOneCell(player.getCordHeight(), player.getCordWidth(), 2);
                    printScene();
                }

            } else if (line.equalsIgnoreCase("a")) {

                if (player.getCordWidth() - 1 >= 0 && (scene.getOneCell(player.getCordHeight(), player.getCordWidth() - 1) == 0 ||
                        scene.getOneCell(player.getCordHeight(), player.getCordWidth() - 1) == 4)) {
                    if (scene.getOneCell(player.getCordHeight(), player.getCordWidth() - 1) == 4) {
                        System.out.println("You are WIN!");
                        return;
                    }
                    scene.setOneCell(player.getCordHeight(), player.getCordWidth(), 0);
                    player.setCordWidth(player.getCordWidth() - 1);
                    scene.setOneCell(player.getCordHeight(), player.getCordWidth(), 2);
                    printScene();
                }

            } else if (line.equalsIgnoreCase("s")) {
                if (player.getCordHeight() + 1 < sizeScene && (scene.getOneCell(player.getCordHeight() + 1, player.getCordWidth()) == 0 ||
                        scene.getOneCell(player.getCordHeight() + 1, player.getCordWidth()) == 4)) {
                    if (scene.getOneCell(player.getCordHeight() + 1, player.getCordWidth()) == 4) {
                        System.out.println("You are WIN!");
                        return;
                    }
                    scene.setOneCell(player.getCordHeight(), player.getCordWidth(), 0);
                    player.setCordHeight(player.getCordHeight() + 1);
                    scene.setOneCell(player.getCordHeight(), player.getCordWidth(), 2);
                    printScene();
                }

            } else if (line.equalsIgnoreCase("9")) {
                break;
            }
        }
        scan.close();
    }

    private void printScene() {
        readFromFileConfig();
        ColoredPrinter printer = new ColoredPrinter();
        for (int i = 0; i < sizeScene; i++) {
            for (int j = 0; j < sizeScene; j++) {
                if (scene.getOneCell(i, j) == 0) {
                    printer.print(emptyCellChar, Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(emptyCellColor));
                } else if (scene.getOneCell(i, j) == 1) {
                    printer.print(wallChar, Ansi.Attribute.NONE, Ansi.FColor.BLACK, Ansi.BColor.valueOf(wallCharColor));
                } else if (scene.getOneCell(i, j) == 2) {
                    printer.print(playerChar, Ansi.Attribute.NONE, Ansi.FColor.BLACK, Ansi.BColor.valueOf(playerCharColor));
                } else if (scene.getOneCell(i, j) == 3) {
                    printer.print(persecutorChar, Ansi.Attribute.NONE, Ansi.FColor.BLACK, Ansi.BColor.valueOf(persecutorCharColor));
                } else if (scene.getOneCell(i, j) == 4) {
                    printer.print(winCellChar, Ansi.Attribute.NONE, Ansi.FColor.BLACK, Ansi.BColor.valueOf(winCellCharColor));
                }
            }
            System.out.println();
        }
    }

    private void readFromFileConfig() {

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(Main.class.getResourceAsStream(CONFIG))))) {
            String line;
            while (reader.ready()) {
                line = reader.readLine();
                String name = line.split("=")[0].trim();
                if (name.equalsIgnoreCase("enemy.char")) {
                    persecutorChar = line.charAt(line.length()-1);
                } else if (name.equalsIgnoreCase("player.char")) {
                    playerChar = line.charAt(line.length()-1);
                } else if (name.equalsIgnoreCase("wall.char")) {
                    wallChar = line.charAt(line.length()-1);
                } else if (name.equalsIgnoreCase("goal.char")) {
                    winCellChar = line.charAt(line.length()-1);
                } else if (name.equalsIgnoreCase("empty.char")) {
                    emptyCellChar = line.charAt(line.length()-1);
                } else if (name.equalsIgnoreCase("enemy.color")) {
                    persecutorCharColor = line.split("=")[1].trim();
                } else if (name.equalsIgnoreCase("player.color")) {
                    playerCharColor = line.split("=")[1].trim();
                } else if (name.equalsIgnoreCase("wall.color")) {
                    wallCharColor = line.split("=")[1].trim();
                } else if (name.equalsIgnoreCase("goal.color")) {
                    winCellCharColor = line.split("=")[1].trim();
                } else if (name.equalsIgnoreCase("empty.color")) {
                    emptyCellColor = line.split("=")[1].trim();
                }
            }
        } catch (Exception e) {
            persecutorChar = 'X';
            playerChar = 'o';
            wallChar = '#';
            winCellChar = 'O';
            emptyCellChar = ' ';
            persecutorCharColor = "RED";
            playerCharColor = "GREEN";
            wallCharColor = "MAGENTA";
            winCellCharColor = "BLUE";
            emptyCellColor = "YELLOW";
        }
    }
}
