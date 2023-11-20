package catalogue;

import java.io.Serial;
import java.io.Serializable;
import java.util.Currency;
import java.util.Formatter;
import java.util.Locale;
import java.util.Objects;

/**
 * Write a description of class BetterBasket here.
 * 
 * @author  Connor Richardson
 * @version 1.0
 */
public class BetterBasket extends Basket implements Serializable
{
  @Serial
  private static final long serialVersionUID = 1L;
  private int    theOrderNum;          // Order number

  /**
   * Constructor for a basket which is
   *  used to represent a customer order/ wish list
   */
  public BetterBasket()
  {
    theOrderNum  = 0;
  }

  /**
   * Set the customers unique order number
   * Valid order Numbers 1 .. N
   * @param anOrderNum A unique order number
   */
  public void setOrderNum( int anOrderNum )
  {
    theOrderNum = anOrderNum;
  }

  /**
   * Returns the customers unique order number
   * @return the customers order number
   */
  public int getOrderNum()
  {
    return theOrderNum;
  }

  /**
   * Add a product to the Basket.
   * Product is appended to the end of the existing products
   * in the basket.
   * @param pr A product to be added to the basket
   * @return true if successfully adds the product
   */
  // Will be in the Java doc for Basket
  @Override
  public boolean add( Product pr )
  {
    for(Product pr2: this){
      if (pr.getProductNum().equals(pr2.getProductNum())){
        pr2.setQuantity(pr2.getQuantity()+pr.getQuantity());
        return true;
      }
    }


    return super.add( pr );     // Call add in ArrayList
  }

  public void rem(Product pr){
    for (Product pr2: this) {
      if (Objects.equals(pr2.getProductNum(), pr.getProductNum())){
        super.remove(pr2); //removes item in ArrayList
        return;
      }
    }
  }

  /**
   * Returns a description of the products in the basket suitable for printing.
   * @return a string description of the basket products
   */
  public String getDetails()
  {
    Locale uk = Locale.UK;
    StringBuilder sb = new StringBuilder(256);
    Formatter fr = new Formatter(sb, uk);
    String csign = (Currency.getInstance( uk )).getSymbol();
    double total = 0.00;
    if ( theOrderNum != 0 )
      fr.format( "Order number: %03d\n", theOrderNum );

    if ( this.size() > 0 )
    {
      for ( Product pr: this )
      {
        int number = pr.getQuantity();
        fr.format("%-7s",       pr.getProductNum() );
        fr.format("%-14.14s ",  pr.getDescription() );
        fr.format("(%3d) ",     number );
        fr.format("%s%7.2f",    csign, pr.getPrice() * number );
        fr.format("\n");
        total += pr.getPrice() * number;
      }
      fr.format("----------------------------\n");
      fr.format("Total                       ");
      fr.format("%s%7.2f\n",    csign, total );
      fr.close();
    }
    return sb.toString();
  }
}
