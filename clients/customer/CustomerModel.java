package clients.customer;

import catalogue.BetterBasket;
import catalogue.Product;
import debug.DEBUG;
import middle.MiddleFactory;
import middle.StockException;
import middle.StockReader;

import javax.swing.*;
import java.util.Observable;

/**
 * Implements the Model of the customer client
 * @author  Mike Smith University of Brighton
 * @version 1.0
 */
public class CustomerModel extends Observable
{
  private BetterBasket      theBasket  = null;          // Bought items

  private StockReader     theStock     = null;
  private ImageIcon       thePic       = null;

  /*
   * Construct the model of the Customer
   * @param mf The factory to create the connection objects
   */
  public CustomerModel(MiddleFactory mf)
  {
    try                                          //
    {
      theStock = mf.makeStockReader();           // Database access
    } catch ( Exception e )
    {
      DEBUG.error("""
              CustomerModel.constructor
              Database not created?
              %s
              """, e.getMessage() );
    }
    theBasket = makeBasket();                    // Initial Basket
  }

  /**
   * return the Basket of products
   * @return the basket of products
   */
  public BetterBasket getBasket()
  {
    return theBasket;
  }

  /**
   * Check if the product is in Stock
   * @param productNum The product number
   */
  public void doCheck(String productNum )
  {
    theBasket.clear();                          // Clear s. list
    String theAction = "";
    // Product being processed
    String pn = productNum.trim();                    // Product no.
    int    amount  = 1;                         //  & quantity
    try
    {
      if ( theStock.exists(pn) )              // Stock Exists?
      {                                         // T
        Product pr = theStock.getDetails(pn); //  Product
        if ( pr.getQuantity() >= amount )       //  In stock?
        {
          theAction =                           //   Display
            String.format( "%s : %7.2f (%2d) ", //
              pr.getDescription(),              //    description
              pr.getPrice(),                    //    price
              pr.getQuantity() );               //    quantity
          pr.setQuantity( amount );             //   Require 1
          thePic = theStock.getImage(pn);     //    product
        } else {                                //  F
          theAction =                           //   Inform
            pr.getDescription() +               //    product not
            " not in stock" ;                   //    in stock
        }
      } else {                                  // F
        theAction =                             //  Inform Unknown
          "Unknown product number " + pn;       //  product number
      }
    } catch( StockException e )
    {
      DEBUG.error("CustomerClient.doCheck()\n%s",
      e.getMessage() );
    }
    setChanged(); notifyObservers(theAction);
  }

  /**
   * Clear the products from the basket
   */
  public void doClear()
  {
    String theAction;
    theAction = "Enter Product Number";       // Set display
    thePic = null;                            // No picture
    setChanged(); notifyObservers(theAction);
  }

  /**
   * Return a picture of the product
   * @return An instance of an ImageIcon
   */
  public ImageIcon getPicture()
  {
    return thePic;
  }

  /**
   * Make a new Basket
   * @return an instance of a new Basket
   */
  protected BetterBasket makeBasket()
  {
    return new BetterBasket();
  }
}

