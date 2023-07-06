package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/** This class manipulates Parts and Products and their observable lists. */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();

    /** This static method adds a newPart to the allParts observable list of Part objects.
     @param newPart A newPart to be added to the allParts observable list.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /** This static method adds a newProduct to the allProducts observable list of Product objects.
     @param newProduct A newProduct to be added to the allProducts observable list.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /** This static method uses a for loop to look up a Part in the allParts observable list.
     @param partId An int that is checked against the Parts in the allParts observable list.
     @return Returns the Part p in the observable list when matching the entered partId, otherwise returns null.
     */
    public static Part lookupPart(int partId) {
        ObservableList<Part> allParts = getAllParts();

        for(int i = 0; i < allParts.size(); i++){
            Part p = allParts.get(i);

            if(p.getId() == partId){
                return p;
            }
        }
        return null;
    }

    /** This static method uses a for loop to look up a Product in an observable list.
     @param productId An int that is checked against the Products in the allProducts observable list.
     @return Returns the Product pr in the observable list when matching the entered productId, otherwise returns null.
     */
    public static Product lookupProduct(int productId) {
        ObservableList<Product> allProducts = getAllProducts();

        for(int i = 0; i < allProducts.size(); i++){
            Product pr = allProducts.get(i);

            if(pr.getId() == productId){
                return pr;
            }
        }
        return null;
    }

    /** This static method uses an enhanced for loop to check if the String parameter is contained in the name of the Parts in an observable list.
     @param partName A String that is checked if it is contained in the name of the Parts in the allParts observable list.
     @return Returns the namedParts observable list after checking the entered partName and if found added to namedParts.
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        ObservableList<Part> allParts = getAllParts();

        for(Part p : allParts){                     //temporary variable (p) used in each iteration, : the list name to look through (allParts).
            if(p.getName().contains(partName)){     //looking to see if the string that we passed in (partName) is contained in the name of the part (p) that is in the observableList.  this returns a boolean for yes it is contained or no it is not contained.
                namedParts.add(p);                  // we only care about the yes scenario, so if it is, we add p to our namedParts observableList.  then it iterates through the rest of the observablelist to see if the partName is in the other items in
                                                    // the observableList too and if so also adds them to the temporary newParts observablelist.
            }
        }
        return namedParts;
    }

    /** This static method uses an enhanced for loop to check if the String parameter is contained in the name of the Products in an observable list.
     @param productName A String that is checked if it is contained in the name of the Products in the allProducts observable list.
     @return Returns the namedProducts observable list after checking the entered productName and if found added to namedProducts.
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = getAllProducts();

        for(Product pr : allProducts){
            if(pr.getName().contains(productName)){
                namedProducts.add(pr);
            }
        }
        return namedProducts;
    }

    /** This static method updates a filtered part by calling a getter for the filteredParts observable list and setting a new index and part.
     @param index The index of the part.
     @param selectedPart The part that is to be replaced via the set method.
     */
    public static void updateFilteredPart(int index, Part selectedPart) {  // 1st parameter is for the object you want to replace, the 2nd parameter is the thing you are replacing it with.
        getAllFilteredParts().set(index, selectedPart);
    }

    /** This static method updates a part in the allParts observableList based on its index.
     @param index The index of the part.
     @param selectedPart The part that is to be replaced via the set method.
     */
    public static void updatePart(int index, Part selectedPart) {  // 1st parameter is for the object you want to replace, the 2nd parameter is the thing you are replacing it with.
        Inventory.getAllParts().set(index, selectedPart);
    }

    /** This static method updates up a product by calling the lookupProduct method to go through the allProducts observableList based on its index.
     It then removes the matched product at that index and adds a product in its place.
     @param index The index of the product.
     @param newProduct The product that is to be replaced via the set method.
     */
    public static void updateProduct(int index, Product newProduct){
        Product pr = Inventory.lookupProduct(index);
        allProducts.remove(pr);
        Inventory.addProduct(newProduct);
    }

    /** This static method uses an enhanced for loop to find a match in the allParts observable list and then removes the previously selected part if a match to it is found, otherwise it returns false.
     @param selectedPart The part to be checked for a match in the observable list.
     @return Returns false if no match is found.
     */
    public static boolean deletePart(Part selectedPart) {
        for(Part p : getAllParts())
        {
            if(p.getId() == selectedPart.getId())
                return getAllParts().remove(p);
        }
        return false;
    }

    /** This static method deletes a product by checking if the allAssociatedParts observable list is empty and if so it uses an enhanced for loop to find a match in the allProducts observable list.
     It then removes the matched product from the allProducts observable list, otherwise it returns false after displaying an ERROR pop up window that tells the user there are associated parts with the product that need removed first.
     @param selectedProduct The product that is checked for a match in the allProducts observable list.
     @return Returns false if no match is found.
     */
    public static boolean deleteProduct(Product selectedProduct){
        if(selectedProduct.getAllAssociatedParts().isEmpty()) {
            for (Product pr : getAllProducts())
            {
                if (pr.getId() == selectedProduct.getId())
                    return allProducts.remove(pr);
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please remove any associated parts with this product using the Modify button.");
            alert.setTitle("ERROR");
            alert.setHeaderText("Cannot delete a product with an associated part.");
            Optional<ButtonType> result = alert.showAndWait();
        }
        return false;
    }

    /** This static method gets the allParts observable list of Part objects.
     @return Returns the allParts observable list of Part objects.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** This static method gets the allProducts observable list of Product objects.
     @return Returns the allProducts observable list of Product objects.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /** This static method gets the filteredParts observable list of Part objects.
     @return Returns the filteredParts observable list of Part objects.
     */
    public static ObservableList<Part> getAllFilteredParts() {
        return filteredParts;
    }
}

