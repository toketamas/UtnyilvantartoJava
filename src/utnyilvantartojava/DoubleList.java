package utnyilvantartojava;

import java.util.ArrayList;
import java.util.List;

public class DoubleList extends ArrayList{
    List<List> list;
    public DoubleList(){
        list=new ArrayList<>();
    }


    public void add(Object element1,Object element2){
        List<Object> elist=new ArrayList<>();
        elist.add(element1);
        elist.add(element2);
        list.add(elist);
    }

    public List get(int number){
        return list.get(number);
    }

    @Override
    public  int size(){
        return list.size();
    }
}