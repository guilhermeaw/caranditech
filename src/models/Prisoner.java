package models;

import common.Identity;

import java.util.Date;

public class Prisoner extends Identity {
    private String name;
    private int cellId;
    private int prisonerTypeId;
    private Date enterDate;
    private Date exitDate;

    public Prisoner() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCellId() {
        return cellId;
    }

    public void setCellId(int cellId) {
        this.cellId = cellId;
    }

    public int getPrisonerTypeId() {
        return prisonerTypeId;
    }

    public void setPrisonerTypeId(int prisonerTypeId) {
        this.prisonerTypeId = prisonerTypeId;
    }

    public Date getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(Date enterDate) {
        this.enterDate = enterDate;
    }

    public Date getExitDate() {
        return exitDate;
    }

    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }
}
