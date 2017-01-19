package com.martinemmanuelsantos.medbox.models;

import java.io.Serializable;

/**
 * Created by nutel on 1/14/2017.
 */

public class Medication implements Serializable {

    long _ID;
    long icon;
    String name;
    boolean prescription;
    String doseType;
    long remainingSupply;
    boolean lowSupplyWarning;
    long lowSupplyValue;
    String methodTaken;
    long instruction;
    String notes;

    public Medication() {

    }

    //region Overrides

    @Override
    public String toString() {
        return getIcon() + ", " +
                getName() + ", " +
                isPrescription() + ", " +
                getDoseType() + ", " +
                getRemainingSupply() + ", " +
                isLowSupplyWarning() + ", " +
                getLowSupplyValue() + ", " +
                getMethodTaken() + ", " +
                getInstruction() + ", " +
                getNotes();
    }


    //endregion

    //region Getters and Setters


    public long getIcon() {
        return icon;
    }

    public void setIcon(long icon) {
        this.icon = icon;
    }

    public boolean isLowSupplyWarning() {
        return lowSupplyWarning;
    }

    public void setLowSupplyWarning(boolean lowSupplyWarning) {
        this.lowSupplyWarning = lowSupplyWarning;
    }

    public String getDoseType() {
        return doseType;
    }

    public void setDoseType(String doseType) {
        this.doseType = doseType;
    }

    public long getInstruction() {
        return instruction;
    }

    public void setInstruction(long instruction) {
        this.instruction = instruction;
    }

    public long getLowSupplyValue() {
        return lowSupplyValue;
    }

    public void setLowSupplyValue(long lowSupplyValue) {
        this.lowSupplyValue = lowSupplyValue;
    }

    public long get_ID() {
        return _ID;
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethodTaken() {
        return methodTaken;
    }

    public void setMethodTaken(String methodTaken) {
        this.methodTaken = methodTaken;
    }

    public boolean isPrescription() {
        return prescription;
    }

    public void setPrescription(boolean prescription) {
        this.prescription = prescription;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getRemainingSupply() {
        return remainingSupply;
    }

    public void setRemainingSupply(long remainingSupply) {
        this.remainingSupply = remainingSupply;
    }

    //endregion

}
