package model;

/** This class inherits from the Part class and produces Outsourced objects. */
public class Outsourced extends Part{
    private String companyName;

    /** This method is a constructor for an Outsourced object.
     It uses the super method to use the Part class' Part constructor and applies an additional String parameter.
     @param id The id to be constructed.
     @param name The name to be constructed.
     @param price The price to be constructed.
     @param stock The stock to be constructed.
     @param min The min to be constructed.
     @param max The max to be constructed.
     @param companyName The companyName to be constructed.
     */
    public Outsourced (int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** This method gets the companyName.
     @return Returns the companyName.
     */
    public String getCompanyName() {
        return companyName;
    }

    /** This method sets the companyName.
     @param companyName Sets the companyName.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}