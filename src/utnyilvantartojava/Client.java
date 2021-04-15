package utnyilvantartojava;

import java.util.ArrayList;
import java.util.List;

//Ez egy helyszínt reprezentál(egy gépet a clientnumber lesz a gépszám ez lesz elsődleges kulcs )
public class Client implements IDbObject {
    private String client;
    private String clientNumber;
    private String type;
    private  String factoryNumber;
    private int zipCode;
    private String city;
    private String address;
    private boolean exist;
    private int maintenancePerYear;
    private String field;

    public Client(String client, String clientNumber, String type, String factoryNumber, int zipCode, String city, String address, boolean exist, int maintenancePerYear, String field) {
        this.client = client;
        this.clientNumber = clientNumber;
        this.type = type;
        this.factoryNumber = factoryNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.address = address;
        this.exist = exist;
        this.maintenancePerYear = maintenancePerYear;
        this.field = field;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFactoryNumber() {
        return factoryNumber;
    }

    public void setFactoryNumber(String factoryNumber) {
        this.factoryNumber = factoryNumber;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean getExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public int getMaintenancePerYear() {
        return maintenancePerYear;
    }

    public void setMaintenancePerYear(int maintenancePerYear) {
        this.maintenancePerYear = maintenancePerYear;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public DoubleList doubleList() {
        DoubleList list = new DoubleList();
        list.add("client",this.client);
        list.add("clientNumber",this.clientNumber);
        list.add("type",this.type);
        list.add("factoryNumber",this.factoryNumber);
        list.add("zipCode",this.zipCode);
        list.add("city",this.city);
        list.add("address",this.address);
        list.add("exist",this.exist);
        list.add("maintenancePerYear",this.maintenancePerYear);
        list.add("field",this.field);
        return list;
    }
}
