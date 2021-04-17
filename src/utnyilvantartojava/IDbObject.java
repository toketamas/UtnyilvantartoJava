package utnyilvantartojava;

import java.util.List;

public interface IDbObject {
    public List<Object> doubleList();
    public int colmnsInDb();
    public void dbUpdate();
    public void dbInsert();
    public List<String> keysFromDoubleList();
    public List<Object> valuesFromDoubleList();

}
