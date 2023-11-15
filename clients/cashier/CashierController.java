package clients.cashier;


import middle.StockException;

/**
 * The Cashier Controller
 * @author M A Smith (c) June 2014
 */

public class CashierController
{
  private CashierModel model = null;
  private CashierView  view  = null;

  /**
   * Constructor
   * @param model The model 
   * @param view  The view from which the interaction came
   */
  public CashierController( CashierModel model, CashierView view )
  {
    this.view  = view;
    this.model = model;
  }

  /**
   * Check interaction from view
   *
   * @param pn   The product number to be checked
   * @param text
   */
  public void doCheck(String pn, String quan)
  {
    model.doCheck(pn, quan);
  }

   /**
   * Buy interaction from view
   */
  public void doBuy()
  {
    model.doBuy();
  }
  
   /**
   * Bought interaction from view
   */
  public void doBought()
  {
    model.doBought();
  }

  public void doRemove(String pn, String quan) throws StockException {
    model.doRemove(pn, quan);
  }
}
