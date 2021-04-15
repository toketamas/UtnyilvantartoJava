package utnyilvantartojava;

import java.util.ArrayList;
import java.util.List;

public class DoubleList extends ArrayList {
    List<List> doubleList;

    public DoubleList() {
        doubleList = new ArrayList<>();
    }


    public void add(Object element1, Object element2) {
        List<Object> elist = new ArrayList<>();
        elist.add(element1);
        elist.add(element2);
        doubleList.add(elist);
    }

    public List get(int number) {
        return doubleList.get(number);
    }

    @Override
    public int size() {

        return doubleList.size();
    }

    public List getSimple(String firstOrSecond) {
        int fs;
        List<Object> list = new ArrayList<>();
        if (String.valueOf(firstOrSecond) == "first") {
            fs = 0;
        } else if (String.valueOf(firstOrSecond) == "second") {
            fs = 1;
        } else {
            throw new RuntimeException("Hibás érték! : " + firstOrSecond);
        }
        for (int i = 0; i < doubleList.size(); i++) {
            list.add(doubleList.get(i).get(fs));

        }
        return list;


    }
}