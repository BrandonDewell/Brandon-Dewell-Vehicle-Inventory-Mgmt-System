package model;

/** This class inherits from the Part class and produces InHouse objects. */
public class InHouse extends Part{
    private int machineId;

    /** This method is a constructor for an InHouse object.
     It uses the super method to use the Part class' Part constructor and applies an additional int parameter.
     @param id The id to be constructed.
     @param name The name to be constructed.
     @param price The price to be constructed.
     @param stock The stock to be constructed.
     @param min The min to be constructed.
     @param max The max to be constructed.
     @param machineId The machineId to be constructed.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** This method gets the machineId.
     @return Returns the machineId.
     */
    public int getMachineId() {
        return machineId;
    }

    /** This method sets the machineId.
     @param machineId Sets the machineId.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
