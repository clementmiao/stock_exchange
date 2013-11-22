/* Single Stock Exchange
 *   Class for representing orders.
 *
 * 
 */

import java.util.*;
import java.io.File;
import java.math.BigInteger;

public class Order {
    public static final char ADD = 'A';
    public static final char CANCEL = 'C';
    public static final char BUY = 'B';
    public static final char SELL = 'S';

    private char venue;
    private char type;
    private String ticker;
    private char book;
    private int shares;
    private int price;
    private long oref;
    private long time;

    // constructor: copy an existing order
    public Order(Order o) {
        this.venue = o.venue;
        this.ticker = o.ticker;
        this.type = o.type;
        this.book = o.book;
        this.shares = o.shares;
        this.price = o.price;
        this.oref = o.oref;
        this.time = o.time;
    }

    /* constructor: string
     * expected format: (comma separated)
     *   Venue: one character
     *   Ticker Symbol: string
     *   Type: limit order (A) or cancel(C)
     *   Book: Buy (B) or Sell (S)
     *   Number of Shares: integer
     *   Price: integer
     *   Order reference number: big integer
     */
    public Order(String line, long t) {
        Scanner scanner = new Scanner(line);
        scanner.useDelimiter(",");
        try {
            venue = scanner.next().charAt(0);
            ticker = scanner.next();
            type = scanner.next().charAt(0);
            book = scanner.next().charAt(0);
            shares = scanner.nextInt();
            price = scanner.nextInt();
            BigInteger bi = scanner.nextBigInteger();
            oref= bi.longValue();
            time = t;
        } catch (NoSuchElementException e) {
            System.out.println("Error in parsing line:" + line);
            System.exit(0);
        }
        scanner.close();
    }


    // constructor: construct an order from the pieces
    public Order(char venue, String ticker, char type, char book,
                 int shares, int price, long oref, long t) {
        this.venue = venue;
        this.ticker = ticker;
        this.type = type;
        this.book = book;
        this.shares = shares;
        this.price = price;
        this.oref = oref;
        this.time = t;
    }

    public char getVenue() {
        return venue;
    }

    public String getTicker() {
        return ticker;
    }

    public char getType() {
        return type;
    }

    public char getBook() {
        return book;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int newNumShares) {
	shares = newNumShares;
    }

    public int getPrice() {
        return price;
    }

    public long getOref() {
        return oref;
    }

    public long getTime() {
        return time;
    }

    public boolean isAddOrder() {
        return (type == ADD);
    }

    public boolean isCancelOrder() {
        return (type == CANCEL);
    }


    public boolean bookIsBuy() {
        return (book == BUY);
    }


    public boolean isOrefEqual(long oref) {
        return (this.oref == oref);
    }


    public String toString() {
        String s = this.venue + "," + this.ticker + "," + this.type + ","
            + this.book + "," + this.shares + ","
            + this.price + "," + this.oref;
        return s;
    }


    public static void main(String[] args) {
	// very simple test code
        Order o = new Order("I,AMGN,A,S,100,550000,1000", 0);
        System.out.println(o.isAddOrder());
        System.out.println(o.isCancelOrder());
        o = new Order("I,AMGN,C,S,100,550000,1000", 1);
        System.out.println(o.isAddOrder());
        System.out.println(o.isCancelOrder());
    }


}
