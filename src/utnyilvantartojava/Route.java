/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utnyilvantartojava;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Tamas
 */
public class Route {

    private  StringProperty datum = new SimpleStringProperty();    
    private  StringProperty indulas = new SimpleStringProperty();
    private  StringProperty erkezes = new SimpleStringProperty();
    private  StringProperty ugyfel = new SimpleStringProperty();
    private  StringProperty tavolsag = new SimpleStringProperty();

    public Route() {
        this.datum=new SimpleStringProperty("");
        this.indulas=new SimpleStringProperty("");
        this.erkezes=new SimpleStringProperty("");
        this.ugyfel=new SimpleStringProperty("");
        this.tavolsag=new SimpleStringProperty("");
    }
     public Route(String dat,String ind,String erk,String ugyf,String tav){
         this.datum=new SimpleStringProperty(dat);
         this.indulas=new SimpleStringProperty(ind);
         this.erkezes=new SimpleStringProperty(erk);
         this.ugyfel=new SimpleStringProperty(ugyf);
         this.tavolsag=new SimpleStringProperty(tav);
    }

    public String getTavolsag() {
        return tavolsag.get();
    }

    public void setTavolsag(String value) {
        tavolsag.set(value);
    }

    public StringProperty tavolsagProperty() {
        return tavolsag;
    }

    public String getUgyfel() {
        return ugyfel.get();
    }

    public void setUgyfel(String value) {
        ugyfel.set(value);
    }

    public StringProperty ugyfelProperty() {
        return ugyfel;
    }

    public String getErkezes() {
        return erkezes.get();
    }

    public void setErkezes(String value) {
        erkezes.set(value);
    }

    public StringProperty erkezesProperty() {
        return erkezes;
    }

    public String getIndulas() {
        return indulas.get();
    }

    public void setIndulas(String value) {
        indulas.set(value);
    }

    public StringProperty indulasProperty() {
        return indulas;
    }
     public String getDatum() {
        return datum.get();
    }

    public void setDatum(String value) {
        datum.set(value);
    }

    public StringProperty datumProperty() {
        return datum;
    }
}
