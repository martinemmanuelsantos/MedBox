package com.martinemmanuelsantos.medbox.database;

import java.io.Serializable;

/**
 * Created by nutel on 1/14/2017.
 */

public class Medication implements Serializable {

    long medicationID;
    String medicationName;
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
        return getMedicationName() + ", " +
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

    public long getMedicationID() {
        return medicationID;
    }

    public void setMedicationID(long medicationID) {
        this.medicationID = medicationID;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
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
