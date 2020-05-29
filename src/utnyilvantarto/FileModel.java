package utnyilvantarto;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileModel {


    ArrayList<String> list = new ArrayList<String>() ;


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





