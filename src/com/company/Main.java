package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void saveGame(String filePath, GameProgress gameProgress) {
        String[] split = filePath.split("/");
        try (FileOutputStream outputStream = new FileOutputStream(split[split.length-1], true);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(gameProgress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zipFiles(String fileZipPath, List<String> filePath) {
        String[] splitZip = fileZipPath.split("/");

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(splitZip[splitZip.length - 1])))
        {
            for (int i = 0; i < filePath.size(); i++) {
                FileInputStream fileInputStream = new FileInputStream(filePath.get(i));
                String[] splitPath = filePath.get(i).split("/");
                String fileName = splitPath[splitPath.length - 1];
                ZipEntry entry = new ZipEntry(fileName);
                zipOutputStream.putNextEntry(entry);
                byte[] buffer = new byte[fileInputStream.available()]; fileInputStream.read(buffer);
                zipOutputStream.write(buffer);
                zipOutputStream.closeEntry();
                fileInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        GameProgress gameProgress = new GameProgress(3, 4, 2, 3.5);
        GameProgress gameProgress1 = new GameProgress(1,1, 3, 10);
        GameProgress gameProgress2 = new GameProgress(5, 5,5,15);

        File saveFile = new File("D://NETOLOGY_PROGECTS//Games/savegames/save.dat");
        File saveFile1 = new File("D://NETOLOGY_PROGECTS//Games/savegames/save1.dat");
        File saveFile2 = new File("D://NETOLOGY_PROGECTS//Games/savegames/save2.dat");
        try {
            saveFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveGame(saveFile.getPath(), gameProgress);
        saveGame(saveFile1.getPath(), gameProgress1);
        saveGame(saveFile2.getPath(), gameProgress2);
        File saveZip = new File("D://NETOLOGY_PROGECTS//Games/savegames/save.zip");

        List<String> files = new ArrayList<>();
        files.add("D://NETOLOGY_PROGECTS//Games/savegames/save.dat");
        files.add("D://NETOLOGY_PROGECTS//Games/savegames/save1.dat");
        files.add("D://NETOLOGY_PROGECTS//Games/savegames/save2.dat");

        zipFiles(saveZip.getPath(), files);

        saveFile.delete();
        saveFile1.delete();
        saveFile2.delete();
    }
}
