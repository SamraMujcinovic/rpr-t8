package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FileListModel {
    private ObservableList<String> putevi = FXCollections.observableArrayList();
    private StringProperty trenutniPut = new SimpleStringProperty();

    public ObservableList<String> getPutevi() {
        return putevi;
    }

    public void setPutevi(ObservableList<String> putevi) {
        this.putevi = putevi;
    }

    public String getTrenutniPut() {
        return trenutniPut.get();
    }

    public StringProperty trenutniPutProperty() {
        return trenutniPut;
    }

    public void setTrenutniPut(String trenutniPut) {
        this.trenutniPut.set(trenutniPut);
    }

    public void addPut(String put){
        putevi.add(put);
    }

    public void deletePutevi(){
        putevi.remove(0,putevi.size());
        trenutniPut.set("");
    }
}
