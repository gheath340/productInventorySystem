package com.example.project;


public class InHouse extends Part{
    private int machineId;

    public InHouse (int id, String name, double price, int stock, int min, int max, int machineId){
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * sets machine id
     * @param machineId
     */
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }

    /**
     * gets machine id
     * @return
     */
    public int getMachineId(){
        return this.machineId;
    }
}