package utnyilvantartojava;

import org.json.JSONObject;

import java.util.List;

public interface IDbObject {

    public List<Object> doubleList();
    public int columnsInDb();
    public void updateDb();
    public void insertDb();
    public List<String> keys();
    public List<Object> values();
    public String jsonPost();

}
