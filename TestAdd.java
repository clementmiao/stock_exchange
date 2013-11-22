// Clement Miao
import java.util.*;
public class TestAdd {
    public static void test (Book expected, Book start, Order o) {
        start.add(o);
        if (!expected.equals(start)) {
            if (expected.getOrders().size() != start.getOrders().size()) {
                System.out.println("Books are of different size. Expected size " + expected.getOrders().size()
                + ", but got size " + start.getOrders().size());
            }
            else {
                for (int i = 0; i < expected.getOrders().size(); i++) {
                    if (!expected.getOrders().get(i).equals(start.getOrders().get(i))) {
                        System.out.println("Failure at position " + i + " Expected " 
                        + expected.getOrders().get(i) + ", but got " + start.getOrders().get(i));
                    }
                }
            }
        }
    }
    
    public static void main(String[] args) {
        Book s = new Book('S');
        ArrayList<Order> list = new ArrayList<Order>();
        Order a0 = new Order("I,AMGN,A,S,100,550000,1000",0);
        list.add(a0);
        //test adding to empty book
        Book expected0 = new Book(list, 'S');
        test(expected0, s, a0);
        
        // testing higher price to not empty sell book
        Order a1 = new Order("I,AMGN,A,S,50,560000,1010",1);
        list.add(a1);
        
        Book expected1 = new Book(list, 'S');
        test(expected1, s, a1);
        
        // testing lower price to not empty sell book
        Order a2 = new Order("I,AMGN,A,S,50,540000,1020",2);
        list.add(0, a2);
        
        Book expected2 = new Book(list, 'S');
        test(expected2, s, a2);
        
        // testing same price, different time middle
        Order a3 = new Order("I,AMGN,A,S,50,550000,1030",3);
        list.add(2, a3);
        
        Book expected3 = new Book(list, 'S');
        test(expected3, s, a3);
        
        // testing same price, different time on end
        Order a4 = new Order("I,AMGN,A,S,50,560000,1040",4);
        list.add(a4);
        
        Book expected4 = new Book(list, 'S');
        test(expected4, s, a4);
        
        
        
        
        // same tests for buy book
        Book b = new Book('B');
        ArrayList<Order> listB = new ArrayList<Order>();
        Order b0 = new Order("I,AMGN,A,B,100,550000,1000",0);
        listB.add(b0);
        //test adding to empty book
        Book expectedB0 = new Book(listB, 'B');
        test(expectedB0, b, b0);
        
        // testing higher price to not empty book
        Order b1 = new Order("I,AMGN,A,B,50,560000,1010",1);
        listB.add(0,b1);
        
        Book expectedB1 = new Book(listB, 'B');
        test(expectedB1, b, b1);
        
        // testing lower price to not empty book
        Order b2 = new Order("I,AMGN,A,B,50,540000,1020",2);
        listB.add(b2);
        
        Book expectedB2 = new Book(listB, 'B');
        test(expectedB2, b, b2);
        
        // testing same price, different time middle
        Order b3 = new Order("I,AMGN,A,B,50,550000,1030",3);
        listB.add(2, b3);
        
        Book expectedB3 = new Book(listB, 'B');
        test(expectedB3, b, b3);
        
        // testing same price, different time on end
        Order b4 = new Order("I,AMGN,A,B,50,540000,1040",4);
        listB.add(b4);
        
        Book expectedB4 = new Book(listB, 'B');
        test(expectedB4, b, b4);
        
        
        
        
        
    }
}
