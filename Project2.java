/*
 * FileName: Project2.java
 * Author: Eric Weber
 * Date: 10/20/2020
 * Purpose: construct a form in which a vehicle’s details and type 
 * are entered, and the sales tax is calculated from this criteria.

 * */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;


public class Project2 extends JFrame implements ActionListener {
  
  Automobile[] autoArray; 
  
  private JLabel modelLabel, salesPriceLabel, mpgLabel, weightLabel; 
  private JButton computeTaxButton, clearFieldButton, displayReportButton; 
  private JTextField modelInput, salesPriceInput, mpgInput, weightInput, salesTaxOutput;
  private JRadioButton hybridRadio, electricRadio, otherRadio;
  
  //  Constructor
  public Project2() {
    
    //  initialize the automobile array for 5 vehicles, all currently null.  
    autoArray = new Automobile[5];
    //  System.out.println(autoArray[1]); => null
    //autoArray acts as a stack: new items are placed from 0 to 4. 
    //... if full, items are shifted downward and the new item is placed at 4
    
    
    //  Setup labels, textFields, and buttons. 
    modelLabel      = new JLabel("Make and Model");
    salesPriceLabel = new JLabel("Sales Price");
    mpgLabel        = new JLabel("Miles per Gallon");
    weightLabel     = new JLabel("Weight in Pounds");
    
    
    modelInput      = new JTextField(12);  //text field of 10 columns
    //modelInput.setBounds(100,20,165,25);
    salesPriceInput = new JTextField(12);
    mpgInput        = new JTextField(10);
    weightInput     = new JTextField(10);
    salesTaxOutput  = new JTextField(10); 
    salesTaxOutput.setEditable(false);
    
    //  Setup buttons with actionListener
    computeTaxButton = new JButton("Compute Sales Tax");
    computeTaxButton.addActionListener(this);
    clearFieldButton = new JButton("Clear Fields");
    clearFieldButton.addActionListener(this);
    displayReportButton = new JButton("Display Report");
    displayReportButton.addActionListener(this);
    
    hybridRadio = new JRadioButton("Hybrid");
    electricRadio = new JRadioButton("Electric");
    otherRadio = new JRadioButton("Other");
    ButtonGroup radioButtons = new ButtonGroup();
    radioButtons.add(hybridRadio);
    radioButtons.add(electricRadio);
    radioButtons.add(otherRadio);
    //ButtonGroup is not a GUI construct, it makes sure only one is selected at a time.  
    
    
    //  Setup the GUI
    setTitle("Automobile Sales Tax Calculator");
    setSize(500,300);
    setLocationRelativeTo(null);  //centers the Frame when launched. 
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new FlowLayout());
    
    //  Setup the top panel for make and model, sales price input. 
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new GridLayout(2, 2, 2, 2));
    topPanel.add(modelLabel);
    topPanel.add(modelInput);
    topPanel.add(salesPriceLabel);
    topPanel.add(salesPriceInput);
    add(topPanel);
    
    //  Setup the middle panel for the Automobile type choosing 
    JPanel middlePanel = new JPanel();
    middlePanel.setLayout(new GridLayout(3, 3, 2, 2));
    middlePanel.add(hybridRadio);
    middlePanel.add(mpgLabel);
    middlePanel.add(mpgInput);
    
    middlePanel.add(electricRadio);
    middlePanel.add(weightLabel);
    middlePanel.add(weightInput);
    middlePanel.add(otherRadio);
    
    TitledBorder middlePanelTitle; 
    middlePanelTitle = BorderFactory.createTitledBorder("Automobile Type");
    middlePanel.setBorder(middlePanelTitle);
    add(middlePanel);
    
    //  Setup bottom panel with buttons and output
    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new GridLayout(2,2,4,4));
    bottomPanel.add(computeTaxButton);
    bottomPanel.add(salesTaxOutput);
    bottomPanel.add(clearFieldButton);
    bottomPanel.add(displayReportButton);
    add(bottomPanel);
    
    setVisible(true);
  }
  
  //  event handler for the buttons
  public void actionPerformed(ActionEvent event) {
    Object whatWasClicked = event.getSource();
    if (computeTaxButton == whatWasClicked) {
      
      String makeModelUser = modelInput.getText();
      int salesPriceUser = -1, mpgUser = -1, weightUser = -1; 
      
      
      //  try getting the user input and check for bad (non-integer) input... 
      try {
        salesPriceUser = Integer.parseInt(salesPriceInput.getText());
        mpgUser = Integer.parseInt(mpgInput.getText());
        weightUser = Integer.parseInt(weightInput.getText());
      } 
      catch (NumberFormatException exc) {
        //  Display error message if exception thrown from trying to parse relevant integers...
        JOptionPane.showMessageDialog(this,
          "A non-integer value was entered in the Sales Price, Miles per Gallon, or Weight in Pounds fields.",
          "Please enter integers only.",
          JOptionPane.ERROR_MESSAGE);
        return;  //exit the method because we can't compute anything with bad input. 
      }
      
      //add the input information to an object
      //store the object in the array
      //output the sales tax...
      
      Automobile temp; 
      if (hybridRadio.isSelected()) {
        temp = new Hybrid(makeModelUser, salesPriceUser, mpgUser);
      } else if (electricRadio.isSelected()) {
        temp = new Electric(makeModelUser, salesPriceUser, weightUser);
      } else {
        //assume it's not electric or a hybrid. just assume it's a regular automobile. 
        temp = new Automobile(makeModelUser, salesPriceUser);
      }
      
      //autoArray[0] = temp;
      
      //where in the autoArray do we put this object?
      //find the proper indice to put the new object, record in placeIndex
      int properIndice = -1;
      for (int ind = 0; ind < 5; ind++) {
        if (autoArray[ind] == null) {
          properIndice = ind;
        }
      }
      if (properIndice == -1) {
        //if no room in the array, make room by shifting all items down. 
        for (int j = 1; j < 5; j++) {
          autoArray[j-1] = autoArray[j];
        }
        properIndice = 4;
      }
      
      autoArray[properIndice] = temp;  //assign the object into the array
      
      //  print the sales tax to the textField
      salesTaxOutput.setText(String.valueOf(autoArray[properIndice].salesTax()));
      //System.out.println(autoArray[0].toString());
      
      
    } else if (clearFieldButton == whatWasClicked) {
      //modelInput, salesPriceInput, mpgInput, weightInput, salesTaxOutput
      modelInput.setText(""); 
      salesPriceInput.setText(""); 
      mpgInput.setText("");
      weightInput.setText("");
      salesTaxOutput.setText("");
      
    } else if (displayReportButton == whatWasClicked) {
      //  loop through autoArray indices, make sure not null, printline their toString() output. 
      //SHOWS RECENT AUTOMOBILES AT TOP. 0 = OLD, 4 = NEW
      System.out.println(" === New Report === ");
      for (int ind = 4; ind >= 0; ind--) {
        if (autoArray[ind] != null) {
          System.out.println(autoArray[ind].toString());
        }
      }
      System.out.println(" === END === ");
      
    } else {
      //something else happened
      System.out.println("Some action outside of the 3 buttons happened");
    }
  }
  
  //  main()
  public static void main(String[] args) {
    Project2 project = new Project2();
  }
}  //  End of Project2 class. 


//==========================================================================================================


class Automobile {
  
  public String makeAndModel; 
  public int purchasePrice; 
  
  public Automobile(String makeAndModel, int purchasePrice) {
    //Initialize the make and purchase price
    this.makeAndModel = makeAndModel;
    this.purchasePrice = purchasePrice;
  }
  
  public double salesTax() {
    //returns the base sales tax = 5% of the sale price
    return (purchasePrice * 0.05);
  }
  
  public String toString() {
    //string: containing make and model, sales price, and sales tax, appropriately labeled
    return "Make and Model: "+makeAndModel+"\nSales Price: $"+purchasePrice+"\nSales Tax: $"+salesTax()+"\n";
  }
}  //End of Automobile class. 

//============================================================================================================

class Electric extends Automobile {
  private int weightInLbs; 
  
  public Electric(String makeAndModel, int purchasePrice, int weight) {
    //  Initialize the make and purchase price
    super(makeAndModel, purchasePrice);
    weightInLbs = weight;
  }
  
  @Override
  public double salesTax() {
    //  returns the base sales tax minus discount based on weight
    int discount;
    if (weightInLbs < 3000) {
      discount = 200;
    } else {
      discount = 150;
    }
    return (super.salesTax() - discount);
  }
  
  @Override 
  public String toString() {
    //  makeAndModel, purchasePrice, salesTax, weight
    return super.toString()+"Electric Vehicle\nWeight: "+weightInLbs+" lbs."+"\n";
  }
}  //  End of Electric class. 

//====================================================================================================

class Hybrid extends Automobile {
  private int milesPerGallon; 
  
  public Hybrid(String makeAndModel, int purchasePrice, int milesPerGallon) {
    //  Initialize the make and purchase price and MPG
    super(makeAndModel, purchasePrice);
    this.milesPerGallon = milesPerGallon;
  }
  
  @Override
  public double salesTax() {
    //  returns the base sales tax minus discount based on milesPerGallon
    int discount;
    if (milesPerGallon < 40) {
      discount = 100;
    } else {
      discount = (2*(milesPerGallon - 40)) + 100;
    }
    return (super.salesTax() - discount);
  }
  
  @Override 
  public String toString() {
    //  makeAndModel, purchasePrice, salesTax, weight
    return super.toString()+"Hybrid Vehicle\nMPG: "+milesPerGallon+"\n";
  }
}  //  End of Hybrid class. 