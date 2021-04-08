package utnyilvantartojava;

import java.util.ArrayList;
import java.util.List;

public class Distance implements IDbObject {
    private String clientid1;
    private String clientid2;
    private int distance;

    public Distance(String clientid1, String clientid2) {
        this.clientid1 = clientid1;
        this.clientid2 = clientid2;
    }

    public Distance(String clientid1, String clientid2, int distance) {
        this.clientid1 = clientid1;
        this.clientid2 = clientid2;
        this.distance = distance;
    }

    public String getClientid1() {
        return clientid1;
    }

    public void setClientid1(String clientid1) {
        this.clientid1 = clientid1;
    }

    public String getClientid2() {
        return clientid2;
    }

    public void setClientid2(String clientid2) {
        this.clientid2 = clientid2;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }


    @Override
    public List<Object> getAll() {
        List<Object>list=new ArrayList<>();
        list.add(this.clientid1);
        list.add(this.clientid2);
        list.add(this.distance);
        return list;
    }
}
