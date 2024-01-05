package clients.cashier;

import catalogue.Basket;
import middle.MiddleFactory;
import middle.OrderProcessing;
import middle.StockException;
import middle.StockReadWriter;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;


/**
 * View of the model
 * @author  M A Smith (c) June 2014  
 */
public class CashierView implements Observer
{
  private static final int H = 350;       // Height of window pixels
  private static final int W = 400;       // Width  of window pixels
  
  private static final String CHECK  = "Check";
  private static final String BUY    = "Buy";
  private static final String BOUGHT = "Bought";
  private static final String REMOVE = "Remove";

  private final JLabel      theAction  = new JLabel();
  private final JTextField  theInput   = new JTextField();
  private final JTextField theQuanity = new JTextField();
  private final JTextArea   theOutput  = new JTextArea();

  private CashierController cont       = null;
  
  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param mf    Factor to deliver order and stock objects
   * @param x     x-coordinate of position of window on screen 
   * @param y     y-coordinate of position of window on screen  
   */
          
  public CashierView(  RootPaneContainer rpc,  MiddleFactory mf, int x, int y  )
  {
    try                                           // 
    {
      StockReadWriter theStock = mf.makeStockReadWriter();        // Database access
      OrderProcessing theOrder = mf.makeOrderProcessing();        // Process order
    } catch ( Exception e )
    {
      System.out.println("Exception: " + e.getMessage() );
    }
    Container cp         = rpc.getContentPane();    // Content Pane
    Container rootWindow = (Container) rpc;         // Root Window
    cp.setLayout(null);                             // No layout manager
    rootWindow.setSize( W, H );                     // Size of Window
    rootWindow.setLocation( x, y );

    Font f = new Font("Monospaced",Font.PLAIN,12);  // Font f is

    JButton theBtCheck = new JButton(CHECK);
    theBtCheck.setBounds( 16, 25+60*0, 80, 40 );    // Check Button
    theBtCheck.addActionListener(                   // Call back code
      e -> cont.doCheck( theInput.getText(), theQuanity.getText() ) );
    cp.add(theBtCheck);                           //  Add to canvas

    JButton theBtBuy = new JButton(BUY);
    theBtBuy.setBounds( 16, 25+60*1, 80, 40 );      // Buy button
    theBtBuy.addActionListener(                     // Call back code
      e -> cont.doBuy() );
    cp.add(theBtBuy);                             //  Add to canvas

    JButton theBtBought = new JButton(BOUGHT);
    theBtBought.setBounds( 16, 25+60*2, 80, 40 );   // brought Button
    theBtBought.addActionListener(                  // Call back code
      e -> cont.doBought() );
    cp.add(theBtBought);                          //  Add to canvas

    theAction.setBounds( 110, 25 , 270, 20 );       // Message area
    theAction.setText( "" );                        // Blank
    cp.add( theAction );                            //  Add to canvas

    theInput.setBounds( 110, 50, 270, 40 );         // Input Area
    theInput.setText("");                           // Blank
    cp.add( theInput );                             //  Add to canvas

    theQuanity.setBounds( 16, 25+60*3, 50, 40 );         // Quantity Area
    theQuanity.setText("1");                           // Default 1
    cp.add( theQuanity );

    JScrollPane theSP = new JScrollPane();
    theSP.setBounds( 110, 100, 270, 160 );          // Scrolling pane
    theOutput.setText( "" );                        //  Blank
    theOutput.setFont( f );                         //  Uses font  
    cp.add(theSP);                                //  Add to canvas
    theSP.getViewport().add( theOutput );           //  In TextArea
    rootWindow.setVisible( true );                  // Make visible
    theInput.requestFocus();                        // Focus is here


    JButton theBtRemove = new JButton(REMOVE);
    theBtRemove.setBounds( 16, 25+60*4, 80, 40 );   // remove Button
    theBtRemove.addActionListener(                  // Call back code
            e -> {
              try {
                cont.doRemove(theInput.getText(), theQuanity.getText());
              } catch (StockException ex) {
                throw new RuntimeException(ex);
              }
            });
    cp.add(theBtRemove);
  }

  /**
   * The controller object, used so that an interaction can be passed to the controller
   * @param c   The controller
   */

  public void setController( CashierController c )
  {
    cont = c;
  }

  /**
   * Update the view
   * @param modelC   The observed model
   * @param arg      Specific args 
   */
  @Override
  public void update( Observable modelC, Object arg )
  {
    CashierModel model  = (CashierModel) modelC;
    String      message = (String) arg;
    theAction.setText( message );
    Basket basket = model.getBasket();
    if ( basket == null )
      theOutput.setText( "Customers order" );
    else
      theOutput.setText( basket.getDetails() );
    
    theInput.requestFocus();               // Focus is here
  }

}
