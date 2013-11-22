/* Stock Exchange for a single stock 
 *
 * Class for representing books
 *
 * Clement Miao
*/

import java.util.*;

public class Book {
  
	public ArrayList<Order> orders;
	public char type;
	private static final char BUY = 'B';
    private static final char SELL = 'S';

    //book constructor
	public Book(char type) {
		this.orders = new ArrayList<Order>();
		this.type = type;
	}
	// constructor for a list to be passed in for test cases
	public Book(ArrayList<Order> list, char type) {
	    orders = list;
	    this.type = type;
	}

	/* findMatching order:
     *   return the matching order with the best price, if one exists
     *   return null if a matching order does not exist.
     */
    public Order findMatchingOrder(int price) {
	    if (orders.size() != 0) {
	    	if (type == SELL) {
	    		if (orders.get(0).getPrice() <= price)
	    			return orders.get(0);
	    	}  else {
	    		if (orders.get(0).getPrice() >= price)
	    			return orders.get(0);
	    	}	
    	return null;
    	}
    	return null;	    	
    }


    /* add:
     *   add the order to this book
     */
    public void add(Order o) {
    	int price = o.getPrice();
    	if (orders.size() != 0) {
    		if (o.bookIsBuy()) {
    		    //check if it is the worst price in the list, skipping looping if possible
    			if (price < orders.get(orders.size() - 1).getPrice()) {
    				orders.add(o);
    				return;
    			} else if (price == orders.get(orders.size() - 1).getPrice() && o.getTime() > orders.get(orders.size() - 1).getTime()) {
    				orders.add(o);
    				return;
    			}
    		} else {
    			if (price > orders.get(orders.size() - 1).getPrice()) {
    				orders.add(o);
    				return;
    			} else if (price == orders.get(orders.size() - 1).getPrice() && o.getTime() > orders.get(orders.size() - 1).getTime()) {
    				orders.add(o);
    				return;
    			}
    		}
    		//loop through to find the right spot for this order
    		for (int i = 0; i < orders.size(); i++) {
	    		if (o.bookIsBuy()) {
	    			if (price > orders.get(i).getPrice()) {
	    				orders.add(i, o);
	    				return;
	    			} else if (price == orders.get(i).getPrice()) {
	    				if (o.getTime() < orders.get(i).getTime()) {
	    					orders.add(i, o);
	    					return;
	    				}
	    			}
	    		} else {
	    			if (price < orders.get(i).getPrice()) {
	    				orders.add(i, o);
	    				return;
	    			} else if (price == orders.get(i).getPrice()) {
	    				if (o.getTime() < orders.get(i).getTime()) {
	    					orders.add(i, o);
	    					return;
	    				}	
	    			}
	    		}	
	    	}		
    	} else {
    	    //if the list is empty, just add the element
    		orders.add(o);
    		return;
    	}
    }


    /* reduce:
     *   Find the order with the specified oref, reduce the
     *   number of shares in the order by numShares, and then 
     *   remove the order from the book if it has now zero shares.
     *   Return the number of shares actually reduced.
     */
    public int reduce(long oref, int numShares) {
    	int sharesReduced = 0;
    	for (int i = 0; i < orders.size(); i++) {
    		Order currentOrder = orders.get(i);	
    		//find the order you are trying to match
    		if (currentOrder.isOrefEqual(oref)) {
    			int currentShares = currentOrder.getShares();
    			int sharesRemaining = Math.max(0, currentShares - numShares);
    			sharesReduced = currentShares - sharesRemaining;
    			currentOrder.setShares(sharesRemaining);
    			//once the shares hit 0, remove the order
    			if (currentOrder.getShares() == 0) {
    				orders.remove(i);
    			}
    			break;
    		}
    	}
    	return sharesReduced;
    }
    
    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void print() {
    	for (int i = 0; i < orders.size(); i++) {
    		Order o = orders.get(i);
    		System.out.println("element " + i + ": type: " + o.getType() + " book: " + o.getBook() + " Shares: " + o.getShares() + " price: " + o.getPrice() + " Oref: " + o.getOref() + " time: " + o.getTime());
    	}
    }
}
