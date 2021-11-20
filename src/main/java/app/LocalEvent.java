package app;

import javafx.beans.property.SimpleStringProperty;

public class LocalEvent {

    // Class LocalEvent will contain three private SimpleStringProperty variables
    // SerialNumber, Name, and Value.
    private SimpleStringProperty serialNumber;
    private SimpleStringProperty name;
    private SimpleStringProperty value;

    // The class will also have getter and setter methods for these variables.
    public String getSerialNumber() {return serialNumber.get();}

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = new SimpleStringProperty(serialNumber);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getValue() {
        return value.get();
    }

    public void setValue(String value) {
        this.value = new SimpleStringProperty(value);
    }

    // Then finally there will be a LocalEvent constructor with the three private variables.
    LocalEvent (String serialNumber, String name, String value) {
        this.serialNumber = new SimpleStringProperty(serialNumber);
        this.name = new SimpleStringProperty(name);
        this.value = new SimpleStringProperty(value);
    }
}
