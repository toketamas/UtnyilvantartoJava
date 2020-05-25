package utnyilvantartojava;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Tamas
 */
public class FileModel {

    ArrayList<String> list = new ArrayList<>();

    public ArrayList<String> readFile(String path) {

        File file = new File(path);
        try {
            Scanner read = new Scanner(file);
            while (read.hasNext()) {
                list.add(read.next());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileModel.class.getName()).log(Level.SEVERE, null, ex);
        }
       // list.sort(Comparator.naturalOrder());
        return list;
    }
}
