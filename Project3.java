/*
 * FileName: Project3.java
 * Author: Eric Weber
 * Date: 10/29/2020
 * Purpose: Draw two types of shapes, Ovals and Rectangles, to a JPanel using Graphics object. 
 * */



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;
//import java.awt.Rectangle;


public class Project3 extends JFrame implements ActionListener {
  
  private JLabel shapeTypeLbl, fillTypeLbl, colorLbl, widthLbl, heightLbl, xCoordLbl, yCoordLbl;
  private JTextField widthFld, heightFld, xCoordFld, yCoordFld;
  private JComboBox shapeTypeCombo, fillTypeCombo, colorCombo;
  private JButton drawButton;
  JPanel rightDrawingPan;
  Drawing drawPanel;
  
  public Project3() {
    
    //  Setup labels, textFields, and button. 
    shapeTypeLbl = new JLabel("Shape Type");
    fillTypeLbl = new JLabel("Fill Type");
    colorLbl = new JLabel("Color");
    widthLbl = new JLabel("Width");
    heightLbl = new JLabel("Height");
    xCoordLbl = new JLabel("x coordinate");
    yCoordLbl = new JLabel("y coordinate");
    widthFld = new JTextField(10);
    heightFld = new JTextField(10);
    xCoordFld = new JTextField(10);
    yCoordFld = new JTextField(10);
    drawButton = new JButton("Draw");
    drawButton.addActionListener(this);
    
    //Setup combo boxes
    String[] typeStr = {"Rectangle", "Oval"};
    String[] fillStr = {"Hollow", "Solid"};
    String[] colorStr = {"Black", "Red", "Orange", "Yellow", "Green", "Blue", "Magenta"};
    shapeTypeCombo = new JComboBox(typeStr);
    fillTypeCombo = new JComboBox(fillStr);
    colorCombo = new JComboBox(colorStr);
    
    //  Setup the GUI
    setTitle("Geometric Drawing");
    setSize(500,400);
    setLocationRelativeTo(null);  //centers the Frame when launched. 
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new FlowLayout());
    
    //Start the layout, with all the options on the left. 
    JPanel leftOptionsPan = new JPanel();
    leftOptionsPan.setLayout(new GridLayout(7, 2, 2, 2));  // 7 rows. 2 cols. 2 spaces...
    leftOptionsPan.add(shapeTypeLbl);
    leftOptionsPan.add(shapeTypeCombo);
    leftOptionsPan.add(fillTypeLbl);
    leftOptionsPan.add(fillTypeCombo);
    leftOptionsPan.add(colorLbl);
    leftOptionsPan.add(colorCombo);
    leftOptionsPan.add(widthLbl);
    leftOptionsPan.add(widthFld);
    leftOptionsPan.add(heightLbl);
    leftOptionsPan.add(heightFld);
    leftOptionsPan.add(xCoordLbl);
    leftOptionsPan.add(xCoordFld);
    leftOptionsPan.add(yCoordLbl);
    leftOptionsPan.add(yCoordFld);
    
    // THE DRAWING JPANEL
    rightDrawingPan = new JPanel();
    
    rightDrawingPan.setLayout(new GridLayout(1,1,1,1)); //only one thing in the layout. 
    TitledBorder rightDrawingPanTitle; 
    rightDrawingPanTitle = BorderFactory.createTitledBorder("Shape Drawing");
    rightDrawingPan.setBorder(rightDrawingPanTitle);
    
    drawPanel = new Drawing();
    rightDrawingPan.add(drawPanel);
    
    // create a container panel to set the two panels side by side. 
    JPanel topContainer = new JPanel();
    topContainer.setLayout(new GridLayout(1,2));
    topContainer.add(leftOptionsPan);
    topContainer.add(rightDrawingPan);
    add(topContainer);
    
    //the add the bottom button and make it all visible
    JPanel bottomButtonPan = new JPanel();
    bottomButtonPan.add(drawButton);
    add(bottomButtonPan);
    setVisible(true);
  }  // End of Project3 Constructor. 
  
  
  //  event handler for the buttons
  public void actionPerformed(ActionEvent event) {
    Object whatWasClicked = event.getSource();
    if (drawButton == whatWasClicked) {
      System.out.println("we're in draw button handling");
      //get the string user input
      String shapeTypeIn, fillTypeIn, colorIn;
      shapeTypeIn = shapeTypeCombo.getSelectedItem().toString();
      fillTypeIn = fillTypeCombo.getSelectedItem().toString();
      colorIn = colorCombo.getSelectedItem().toString();
      
      //  try getting the user input and check for bad (non-integer) input... 
      int widthIn, heightIn, xCoordIn, yCoordIn;
      try {
        widthIn = Integer.parseInt(widthFld.getText());
        heightIn = Integer.parseInt(heightFld.getText());
        xCoordIn = Integer.parseInt(xCoordFld.getText());
        yCoordIn = Integer.parseInt(yCoordFld.getText());
      } 
      catch (NumberFormatException exc) {
        //  Display error message if exception thrown from trying to parse relevant integers...
        JOptionPane.showMessageDialog(this,
          "A non-integer value was entered for Width, Height, x coord, or y coord.",
          "Please enter integers only.",
          JOptionPane.ERROR_MESSAGE);
        return;  //exit the method because we can't compute anything with bad input. 
      }
      
      //Now. Create the Shape with proper attributes based on user input. 
      Shape newShape;
      Rectangle rect = new Rectangle(xCoordIn,yCoordIn,widthIn,heightIn);
      if (shapeTypeIn == "Oval") {
        newShape = new Oval(rect, converStringToColor(colorIn), fillTypeIn);
      } else {
        newShape = new Rectangular(rect, converStringToColor(colorIn), fillTypeIn);
      }
      
      //the created shape object should be passed to the drawShape method of the Drawing class
      //and the drawing class should create a JPanel which is added to the JPAnel container
      
      //drawPanel = new Drawing();
      
      try {
        drawPanel.drawShape(newShape);
      }
      catch (OutsideBounds exc) {
        //  Display error message if exception thrown: OutsideBounds
        JOptionPane.showMessageDialog(this,
          "Your shape is too large or being drawn in negative coordinates.",
          "Please reduce size under 200x200",
          JOptionPane.ERROR_MESSAGE);
        return;  //exit the method because we can't compute anything with bad input.
      }
      
      //rightDrawingPan.add(drawPanel);
      //drawPanel.setVisible(true);
      
      //if exception on the above, catch the OutsideBounds error and display it as with the above. 
      
      
      
    }
  }  // End of actionPerformed method. 
  
  public Color converStringToColor(String colorStr) {
    switch (colorStr.toLowerCase()) {
      case "black":
        return Color.BLACK;
      case "red":
        return Color.RED;
      case "orange":
        return Color.ORANGE;
      case "yellow":
        return Color.YELLOW;
      case "green":
        return Color.GREEN;
      case "blue":
        return Color.BLUE;
      case "magenta":
        return Color.MAGENTA;
    }
    return Color.RED;
  }
  
  //  main()
  public static void main(String[] args) {
    Project3 project = new Project3();
    System.out.println("Started.");
    
    //initialize numberOfCreatedShapes as 0. 
    
    //GENERATE THE GUI...
  }
}  //  End of Project3 Class



//=========================================================================================

// abstract class Shape extends the predefined Java class Rectangle 
// two instance variables: color, and whether it's solid or hollow
// should contain a class/static variable that keeps track of how many shapes have been created
// 3 instance methods, 1 class method, 1 abstract method

abstract class Shape extends Rectangle {
  public Color shapeColor;
  public String solidOrHollow; 
  public static int numberOfCreatedShapes = 0; 
  
  // constructor 
  public Shape(Rectangle rect, Color shapeColor, String solidOrHollow) {
    this.shapeColor = shapeColor;
    this.solidOrHollow = solidOrHollow;
    //... the Rectangle object should define the dimensions and position of the shape
    super.x = rect.x;
    super.y = rect.y;
    super.width = rect.width;
    super.height = rect.height;
    //... should also update the number of shapes created so far
    numberOfCreatedShapes++;
  }
  
  public Graphics setColor(Graphics graphic1) {
    //sets the color for the next draw operation to the color of the current shape
    graphic1.setColor(shapeColor);
    return graphic1;
  }
  
  public String getSolid() {
    return solidOrHollow;
  }
  
  public static int getNoOfShapes() {
    return numberOfCreatedShapes;
  }
  
  abstract Graphics draw(Graphics graphic1);  //subclasses will give the implementation. 
}  //  End of Shape class. 

//=========================================================================================
class Oval extends Shape {
  //  constructor 
  public Oval(Rectangle rect, Color shapeColor, String solidOrHollow) {
    super(rect, shapeColor, solidOrHollow);
  }
  
  @Override
  public Graphics draw(Graphics graphic1) {
    //draws the Oval object on the graphics object passed as a parameter....
    graphic1.setColor(shapeColor);
    System.out.println(solidOrHollow.toLowerCase());
    if (solidOrHollow.equals("Hollow")) {
      graphic1.drawOval(x,y,width,height);
      System.out.println("in hollow if branch");
    } else {
      graphic1.fillOval(x,y,width,height);
    }
    return graphic1;
  }
}  //  End of the Oval class. 

//=========================================================================================
class Rectangular extends Shape {
  //  constructor 
  public Rectangular(Rectangle rect, Color shapeColor, String solidOrHollow) {
    super(rect, shapeColor, solidOrHollow);
  }
  
  @Override
  public Graphics draw(Graphics graphic1) {
    //draws the Rectangular object on the Graphics object passed as a parameter. 
    graphic1.setColor(shapeColor);
    System.out.println(solidOrHollow.toLowerCase());
    if (solidOrHollow.equals("Hollow")) {
      graphic1.drawRect(x,y,width,height);
      System.out.println("in hollow if branch");
    } else {
      graphic1.fillRect(x,y,width,height);
    }
    return graphic1;
  }
}  //  End of Rectangular class. 

//=========================================================================================
class Drawing extends JPanel {
  //one instance variable that contains the shape being currently drawn. 
  //3 methods...
  public Shape currentShape; 
  
  @Override
  public void paintComponent(Graphics graphic1) {
    //draws the current shape on the graphics object 
    //should also draw the number of shapes that have been created thus far...
    //... in the upper left corner. 
    
    if (currentShape == null) return;
    
    super.paintComponent(graphic1);
    //call the shape's draw methods... 
    graphic1 = currentShape.draw(graphic1);
    //graphic1.drawRect(x,y,w,h);
    //g.setColor(Color.RED);
    //g.fillRect(squareX,squareY,squareW,squareH);
    
    //draw number of shapes in top left
    graphic1.setFont(new Font ("TimesRoman", Font.BOLD, 12));
    graphic1.drawString(Integer.toString(currentShape.numberOfCreatedShapes),10,10); 
  }
  
  @Override
  public Dimension getPreferredSize() {
    //returns a Dimension object (for the drawing panel) as 200px by 200px
    return (new Dimension(200,200));
  }
  
  public void drawShape(Shape shape1) throws OutsideBounds {
    //instance method. parameter: current shape to be drawn
    //first checks whether the shape will fit within the panel
    Dimension dim = getPreferredSize();
    if ((shape1.x < 0) || (shape1.y < 0)) {
      throw new OutsideBounds("Shape cannot be placed at negative coordinates.");
    }
    int shapeEndX = shape1.x + shape1.width;
    int shapeEndY = shape1.y + shape1.height;
    if ((shapeEndX > dim.getWidth()) || (shapeEndY > dim.getHeight())) {
      //if not, it throws an OutsideBounds exception. 
      throw new OutsideBounds("Shape will not fit inside the drawing bounds.");
    }
    
    //otherwise, it saves the shape to the currentShape instance variable. 
    currentShape = shape1;
    //it then calls repaint to cause that shape to be drawn. 
    //paintComponent();  what graphic would you pass to this??????
    repaint();
  }
  
}  //  End of Drawing class. 

//=========================================================================================
class OutsideBounds extends Exception {
  //defines a checked exception 
  
  //extends throwable? extends Exception? 
  //"throws x" on the method or class?
  public OutsideBounds(String errorMsg) {
    super(errorMsg);
  }
  //... throw new OutsideBounds("Shape is too large for drawing bounds.");
}  // End of OutsideBounds class. 
