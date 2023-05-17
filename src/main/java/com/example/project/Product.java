package com.example.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product{
    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = FXCollections.observableArrayList();

    }

    /**
     * gets id
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * sets id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * get name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * sets name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets price
     * @return
     */
    public double getPrice() {
        return price;
    }

    /**
     * sets price
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * gets inventory level
     * @return
     */
    public int getStock() {
        return stock;
    }

    /**
     * sets inventory level
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * gets min level
     * @return
     */
    public int getMin() {
        return min;
    }

    /**
     * sets min level
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * gets max
     * @return
     */
    public int getMax() {
        return max;
    }

    /**
     * sets max
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * adds associated part to list
     * @param part
     */
    public void addAssociatedPart(Part part){
        this.associatedParts.add(part);
    }

    /**
     * deletes associated parts from list
     * @param selectedAssociatedPart
     * @return
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if (this.associatedParts.contains(selectedAssociatedPart)) {
            this.associatedParts.remove(selectedAssociatedPart);
            return true;}
        return false;
    }

    /**
     * gets associated parts list
     * @return
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return this.associatedParts;
    }
}