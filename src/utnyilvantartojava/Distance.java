package utnyilvantartojava;

public class Distance {
    private String clientid1;
    private String clientid2;
    private int distance;

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


}
