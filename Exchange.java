/* Stock Exchange for a single stock 
 *
 * Main class for the exchange
 *
 * Clement Miao
 */

import java.util.*;
import java.io.File;

public class Exchange {
    public static final char VENUE = 'I';
    public static final String TICKER = "AMGN";

    //public static int time;
    public Book sellBook;
    public Book buyBook;
    
    /* Constructor */
    public Exchange() {
        //this.time = 0;
        this.buyBook = new Book('B');
        this.sellBook = new Book('S'); 
    }


    /* processOrder:
     *   process the specified order
     */
    private void processOrder(Order o) {
        int price = o.getPrice();
        if (o.isAddOrder()) { 
            if (o.bookIsBuy()) {
                if (sellBook.findMatchingOrder(price) != null) {
                    Order match = sellBook.findMatchingOrder(price);
                    //once match found, reduce shares
                    int sharesReduced = sellBook.reduce(match.getOref(), o.getShares());
                    o.setShares(o.getShares() - sharesReduced);
                    //recursion to see if the order can be fulfilled by other elements of the book
                    if (sharesReduced != 0) {
                        outputRecord(match, 'E', sharesReduced);
                        if (o.getShares() != 0)
                            processOrder(o);    
                    } else {
                        //if no shares were reduced, means no match, so we add it to the book
                        buyBook.add(o);
                        outputRecord(o, 'A', o.getShares());
                    }
                        
                } else {
                    buyBook.add(o);
                    outputRecord(o, 'A', o.getShares());
                }
            } else {
                //same thing as for above BUY process
                if (buyBook.findMatchingOrder(price) != null) {
                    Order match = buyBook.findMatchingOrder(price);
                    int sharesReduced = buyBook.reduce(match.getOref(), o.getShares());
                    o.setShares(o.getShares() - sharesReduced);
                    if (sharesReduced != 0) {
                        outputRecord(match, 'E', sharesReduced);
                        if (o.getShares() != 0)
                            processOrder(o);    
                    } else {
                        buyBook.add(o);
                        outputRecord(o, 'A', o.getShares());
                    }
                } else {
                    sellBook.add(o);
                    outputRecord(o, 'A', o.getShares());
                }
            }
        } else if (o.isCancelOrder()) {
            if (o.bookIsBuy()) {
                int sharesCanceled = buyBook.reduce(o.getOref(), o.getShares());
                outputRecord(o, 'C', sharesCanceled);
            } else {
                int sharesCanceled = sellBook.reduce(o.getOref(), o.getShares());
                outputRecord(o, 'C', sharesCanceled);
            }
        }
    }


    /* outputRecord:
     *  Order o: the order to use for the base information
     *  int numShares: the number of shares to reported
     *  String type: ORDER.ADD for an add record, ORDER.CANCEL for a cancel record 
            or 'E' for an execute record.
     */
    public static void outputRecord(Order o, char type, int numShares) {
        System.out.println( o.getVenue() + "," + o.getTicker() + "," + type + ","
                            + o.getBook() + "," + numShares + ","
                            + o.getPrice() + "," + o.getOref());
    }


    /* run:
     *  Read the orders from the specified file and process them. 
     */
    private void run(String f) {
        int time = 0;

        Scanner scanner = null;

        // attempt to load file
        try {
            scanner = new Scanner(new File(f), "UTF-8");
        } catch (NullPointerException e) {
            System.out.print("Bad file name.");
            System.exit(0);
        } catch (java.io.FileNotFoundException e) {
            System.out.println("File " + f + " not found.");
            System.exit(0);
        }
        
        try {
            while (scanner.hasNextLine()) {
                Order o = new Order(scanner.nextLine(), time);
                if (!o.getTicker().equals(TICKER) || (o.getVenue() != VENUE)) {
                    System.out.printf("Skipping bad order at time %d: %s\n", time, o);
                } else {
                    processOrder(o);
                }
                time = time + 1;
            }
        } finally {
            // ensure the underlying stream is always closed
            scanner.close();
        }
    }


    public static void main(String[] args) {
        String fileName = "";
        if (args.length == 1) {
            fileName = args[0];
        } else {
            System.out.println("usage: java Exchange <fileName>");
            System.exit(0);
        }    
        Exchange e = new Exchange();
        e.run(fileName);
    }

}
