package utnyilvantartojava;

public class Clients {
    private String city;
    private String client;
    private String address;
    private String clientnumber;
    private String field;
    private int exist;

    public Clients(String city, String client, String address, String clientnumber, String field, int exist) {
        this.city = city;
        this.client = client;
        this.address = address;
        this.clientnumber = clientnumber;
        this.field = field;
        this.exist = exist;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClientnumber() {
        return clientnumber;
    }

    public void setClientnumber(String clientnumber) {
        this.clientnumber = clientnumber;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public int getExist() {
        return exist;
    }

    public void setExist(int exist) {
        this.exist = exist;
    }
}
