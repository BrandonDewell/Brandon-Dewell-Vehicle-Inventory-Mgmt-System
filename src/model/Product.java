package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class creates Products. */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** This method is a constructor for Products.
     @param id The id to be constructed.
     @param name The name to be constructed.
     @param price The price to be constructed.
     @param stock The stock to be constructed.
     @param min The min to be constructed.
     @param max The max to be constructed.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** This method gets the id.
     @return Returns the id
     */
    public int getId() {
        return id;
    }

    /** This method sets the id.
     @param id Sets the id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /** This method gets the name.
     @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /** This method sets the name.
     @param name Sets the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /** This method gets the price.
     @return Returns the price.
     */
    public double getPrice() {
        return price;
    }

    /** This method sets the price.
     @param price Sets the price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /** This method gets the stock.
     @return Returns the stock.
     */
    public int getStock() {
        return stock;
    }

    /** This method sets the stock.
     @param stock Sets the stock.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** This method gets the min.
     @return Returns the min.
     */
    public int getMin() {
        return min;
    }

    /** This method sets the min.
     @param min Sets the min.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** This method gets the max.
     @return Returns the max.
     */
    public int getMax() {
        return max;
    }

    /** This method sets the max.
     @param max Sets the max.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /** This method adds a part to the associatedParts observable list.
     @param part A part to be added to an observable list.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /** This method deletes an associated part from a product.
     It checks the id of a part against all of the ids in the associatedParts observable list of Part objects.
     If a match is found the part is removed from the observable list.
     @param selectedAssociatedPart The selected part to be checked against the associatedParts observable list for a match and if found will be deleted.
     @return Returns a boolean of false if no match is found.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        for(Part p : getAllAssociatedParts())
        {
            if(p.getId() == selectedAssociatedPart.getId())
                return getAllAssociatedParts().remove(p);
        }
        return false;
    }

    /** This method gets the associatedParts observable list of Parts.
     @return Returns the associatedParts observable list of Part objects.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
