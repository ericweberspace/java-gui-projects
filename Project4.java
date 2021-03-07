/*
 * FileName: Project4.java
 * Author: Eric Weber
 * Date: 11/15/2020
 * Purpose: Respond to three database operations from a user to edit a real estate database of property characteristics.
 * */


import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Project4  extends JFrame implements ActionListener {
  
  private JLabel transactionLbl, addressLbl, bedroomsLbl, sqFootageLbl, priceLbl;
  private JTextField transactionFld, addressFld, bedroomsFld, sqFootageFld, priceFld;
  private JComboBox processCombo, changeStatusCombo;
  private JButton processButton, changeButton;
  
  // Our database, with Integer key (transaction #), and Property Object value. 
  private TreeMap<Integer, Property> database= new TreeMap<Integer, Property>();
  
  public Project4() {
    //  Setup labels, textFields, and button. 
    transactionLbl = new JLabel("Transaction No:");
    addressLbl     = new JLabel("Address:");
    bedroomsLbl    = new JLabel("Bedrooms:");
    sqFootageLbl   = new JLabel("Square Footage:");
    priceLbl       = new JLabel("Price:");
    transactionFld = new JTextField(10);
    addressFld     = new JTextField(10);
    bedroomsFld    = new JTextField(10);
    sqFootageFld   = new JTextField(10);
    priceFld       = new JTextField(10);
    processButton  = new JButton("Process");       
    changeButton   = new JButton("Change Status"); 
    processButton.addActionListener(this);
    changeButton.addActionListener(this);
    
    //  Setup combo boxes
    String[] processStr = {"Insert", "Delete", "Find"};
    String[] statusStr  = {"FOR_SALE", "UNDER_CONTRACT", "SOLD"};
    processCombo        = new JComboBox(processStr);
    changeStatusCombo   = new JComboBox(statusStr);
    
    //  Setup the GUI
    setTitle("Real Estate Database");
    setSize(400,320);
    setLocationRelativeTo(null);  //centers the Frame when launched. 
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new FlowLayout());
    
    // Create a 7 row, 2 column GridLayout, populate with the proper Objects
    JPanel allFields = new JPanel();
    allFields.setLayout(new GridLayout(7,2,12,12));
    allFields.add(transactionLbl);  allFields.add(transactionFld);
    allFields.add(addressLbl);      allFields.add(addressFld);
    allFields.add(bedroomsLbl);     allFields.add(bedroomsFld);
    allFields.add(sqFootageLbl);    allFields.add(sqFootageFld);
    allFields.add(priceLbl);        allFields.add(priceFld);
    allFields.add(processButton);   allFields.add(processCombo);
    allFields.add(changeButton);    allFields.add(changeStatusCombo);
    add(allFields);
    setVisible(true);
  }
  
  // actionPerformed handles the button presses, and thus the application logic. 
  public void actionPerformed(ActionEvent event) {
    
    Object whatWasClicked = event.getSource();
    if (processButton == whatWasClicked) {
      // 1) Get String user input
      String addressIn, processIn; 
      addressIn = addressFld.getText();
      processIn = processCombo.getSelectedItem().toString();
      
      // 2) Get Integer user input, vet for non-integer input. 
      int transacIn, bedIn, sqFtIn, priceIn;
      try {
        transacIn = Integer.parseInt(transactionFld.getText());
        bedIn     = Integer.parseInt(bedroomsFld.getText());
        sqFtIn    = Integer.parseInt(sqFootageFld.getText());
        priceIn   = Integer.parseInt(priceFld.getText());
      } 
      catch (NumberFormatException exc) {
        //  Display error message if exception thrown from trying to parse relevant integers...
        JOptionPane.showMessageDialog(this,
          "A non-integer value was entered for the transaction No., bedrooms, sq. footage, or price fields.",
          "Please enter integers only.",
          JOptionPane.ERROR_MESSAGE);
        return;  //exit the method because we can't compute anything with bad input. 
      }
      
      // 3) Handle the operations: Insert, Delete and Find
      
      if (processIn == "Insert") {
        // 1) check if the transaction No. already exists. 
        if (database.containsKey(transacIn)) {  // if the key is in the database...
          JOptionPane.showMessageDialog(this,
          "Error: The entered Transaction No. already exists in the database; the entry cannot be inserted.",
          "Please use a unique Transaction No.",
          JOptionPane.ERROR_MESSAGE);
          return;  //exit the method because we can't compute anything with bad input.
        }
         
        // 2) Now that we've vetted input, initialize a Property object @ the Transaction # in the database. 
        database.put(transacIn, new Property(addressIn, bedIn, sqFtIn, priceIn));
        
        // 3) Display success feedback in JOptionPane:
        JOptionPane.showMessageDialog(this,
          "New Property Successfully Inserted at Transaction No: "+transacIn, "Successful Add",
          JOptionPane.INFORMATION_MESSAGE);
        
        //END OF INSERT LOGIC.
        
      } else {  // handling a find or delete operation...
        
        //1) check if the transaction # DOES NOT exist, if so, exit.  
        if (database.containsKey(transacIn) == false) {  // if the key is NOT in the database...
          JOptionPane.showMessageDialog(this,
          "Error: The entered Transaction No. does not exist in the database. ",
          "Please enter an existing Transaction No.",
          JOptionPane.ERROR_MESSAGE);
          return;  //exit the method because we can't compute anything with bad input.
        }
        
        if (processIn == "Find") {
          //Find the Property object value @ Transaction # in database, display results. 
          Property foundProp = database.get(transacIn);
          //System.out.println(foundProp.toString());
          JOptionPane.showMessageDialog(this,
          "Property Found at Transaction No: "+transacIn+"\nDetails:\n"+foundProp.toString(), 
              "Found!", JOptionPane.INFORMATION_MESSAGE);
          
        } else if (processIn == "Delete") {
          // Delete the key-value entry @ the Transaction # in database. Then Confirm. 
          database.remove(transacIn);
          JOptionPane.showMessageDialog(this,
          "Property Successfully DELETED at Transaction No: "+transacIn, "Successful Deletion",
                                        JOptionPane.INFORMATION_MESSAGE);
        }
      }  
      //END OF INSERT/FIND/DELETE LOGIC. 
      
    } else if (changeButton == whatWasClicked) {
      
      // 1) Get user input: selected status and the identifying transaction No. 
      String selectedStatus = changeStatusCombo.getSelectedItem().toString();
      int transacIn;
      try {
        transacIn = Integer.parseInt(transactionFld.getText());
      } 
      catch (NumberFormatException exc) {
        JOptionPane.showMessageDialog(this,
          "Error: A non-integer value was entered for the transaction No.", "Please enter integers only.",
          JOptionPane.ERROR_MESSAGE);
        return;  //exit the method because we can't compute anything with bad input. 
      }
      
      //change the status of the property associated with the transaction No. to the selected Status. 
      //maybe display a successful joptionpane too...
      
      // 1) Get the property value, edit it, and overwrite the entry. 
      Property foundProp = database.get(transacIn);
      //System.out.println(foundProp.toString());
      foundProp.changeState(Status.valueOf(selectedStatus));  
      //Enum.valueOf(String) => enum type. 
      database.put(transacIn, foundProp);
      
      //Optional JOptionPane for change status success...
      /*
      JOptionPane.showMessageDialog(this,
          "Property Status Updated at Transaction No: "+transacIn+"\nAt New Status: "+selectedStatus,
                                    "Thanks.",JOptionPane.INFORMATION_MESSAGE);
                                    */
    }
  }
  
  public static void main(String[] args) {
    Project4 project = new Project4();
    System.out.println("Started.");
  }
}  // END OF PROJECT4 CLASS. 


/* ================================================================================================================
 * ================================================================================================================
 */
class Property implements StateChangeable<Status> {
  private String address;
  private int bedrooms, sqFootage, price;
  private Status status; 
  
  // Constructor 
  public Property(String address, int bedrooms, int sqFootage, int price) {
    this.address = address;
    this.bedrooms = bedrooms;
    this.sqFootage = sqFootage; 
    this.price = price;
    changeState(Status.FOR_SALE);
  }
  
  public void changeState(Status state) {
    status = state;
  }
  
  @Override
  public String toString() {
    return "Address: "+address+"\nBedrooms: "+bedrooms+"\nSquare Footage: "+sqFootage+" sq. ft.\nPrice: $"+price+
      "\nStatus: "+status;
  }
}

interface StateChangeable<T extends Enum<T>> {
  abstract void changeState(T state);
}

enum Status {
  FOR_SALE, UNDER_CONTRACT, SOLD;
}