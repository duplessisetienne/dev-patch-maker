package com.duplessis.etienne.patchmaker.model;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

//*
// V1.00.00    2019-12-27     E Du Plessis Initial Version
// */

@Service
public class Patch {

    private String patchNumber;
    private String  patchName;
    private String  patchDescription;
    private String  patchVersion;
    private ArrayList<String> procedure;
    private ArrayList<String> function;
    private ArrayList<String> type;
    private ArrayList<String> apex;




    public Patch() {
    }



    public Patch(String patchNumber, String patchName, String patchDescription, String patchVersion, ArrayList<String> procedure,ArrayList<String> function, ArrayList<String> type, ArrayList<String> apex) {
        this.patchNumber = patchNumber;
        this.patchName = patchName;
        this.patchDescription = patchDescription;
        this.patchVersion = patchVersion;
        this.procedure = procedure;
        this.function = function;
        this.type = type;
        this.apex = apex;
    }

    public String getPatchNumber() {
        return patchNumber;
    }

    public void setPatchNumber(String patchNumber) {
        this.patchNumber = patchNumber;
    }

    public String getPatchName() {
        return patchName;
    }

    public void setPatchName(String patchName) {
        this.patchName = patchName;
    }

    public String getPatchDescription() {
        return patchDescription;
    }

    public void setPatchDescription(String patchDescription) {
        this.patchDescription = patchDescription;
    }

    public String getPatchVersion() {
        return patchVersion;
    }

    public void setPatchVersion(String patchVersion) {
        this.patchVersion = patchVersion;
    }

    public ArrayList<String> getProcedure() {
        return procedure;
    }

    public void setProcedure(ArrayList<String> procedure) {
        this.procedure = procedure;
    }


    public ArrayList<String> getFunction() {
        return function;
    }

    public void setFunction(ArrayList<String> function) {
        this.function = function;
    }

    public ArrayList<String> getType() {
        return type;
    }

    public void setType(ArrayList<String> type) {
        this.type = type;
    }

    public ArrayList<String> getApex() {
        return apex;
    }

    public void setApex(ArrayList<String> apex) {
        this.apex = apex;
    }



    @Override
    public String toString() {
        return "Patch{" +
                "patchNumber=" + patchNumber +
                ", patchName='" + patchName + '\'' +
                ", patchDescription='" + patchDescription + '\'' +
                ", patchVersion='" + patchVersion + '\'' +
                '}';
    }
}
