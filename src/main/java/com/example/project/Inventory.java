package com.example.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * adds part to list
     * @param newPart
     */
    public static void addPart (Part newPart) {
        allParts.add(newPart);
    }

    /**
     * adds product to list
     * @param newProduct
     */
    public static void addProduct (Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * looks up part by id
     * @param partId
     * @return
     */
    public static Part lookupPart (int partId) {
        for (int i = 0; i < allParts.size(); i++) {
            Part temp = allParts.get(i);
            if (temp.getId() == partId) {
                return temp;
            }
        }
        return null;
    }

    /**
     * looks up product by id
     * @param productId
     * @return
     */
    public static Product lookupProduct (int productId) {
        for (int i = 0; i < allProducts.size(); i++) {
            Product temp = allProducts.get(i);
            if (temp.getId() == productId) {
                return temp;
            }
        }
        return null;
    }

    /**
     * looks up parts by name
     * @param partName
     * @return
     */
    public static ObservableList<Part> lookupPart (String partName) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part p : allParts){
            if(p.getName().contains(partName)){
                namedParts.add(p);
            }
        }
        return namedParts;
    }

    /**
     * looks up products by name
     * @param productName
     * @return
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(Product p : allProducts){
            if(p.getName().contains(productName)){
                namedProducts.add(p);
            }
        }
        return namedProducts;
    }

    /**
     * updates a given part
     * @param index
     * @param selectedPart
     */
    public static void updatePart (int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * updates a given product
     * @param index
     * @param newProduct
     */
    public static void updateProduct (int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * deletes given part
     * @param selectedPart
     * @return
     */
    public static boolean deletePart (Part selectedPart) {
        if (allParts.contains((selectedPart))) {
            allParts.remove(selectedPart);
            return true;
        }
        return false;
    }

    /**
     * deletes given product
     * @param selectedProduct
     * @return
     */
    public static boolean deleteProduct (Product selectedProduct) {
        if (allProducts.contains((selectedProduct))) {
            allProducts.remove(selectedProduct);
            return true;
        }
        return false;
    }

    /**
     * gets list of all parts
     * @return
     */
    public static ObservableList<Part> getAllParts () {
        return allParts;
    }

    /**
     * gets list of all products
     * @return
     */
    public static ObservableList<Product> getAllProducts () {
        return allProducts;
    }


}