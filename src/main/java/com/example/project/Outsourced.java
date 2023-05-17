package com.example.project;

public class Outsourced extends Part{

    private String companyName;

    public Outsourced (int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * sets company name
     * @param companyName1
     */
    public void setCompanyName(String companyName1){
        this.companyName = companyName;
    }

    /**
     * gets company name
     * @return
     */
    public String getCompanyName(){
        return this.companyName;
    }
}