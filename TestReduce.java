// Clement Miao

public class TestReduce {
    public static void test(Book expectedBook, Book actualBook, long oref, int numShares, int expectedOutput) {
        int actualOutput = actualBook.reduce(oref, numShares);
        if (actualOutput != expectedOutput) {
            System.out.println("Failed reduction! Expected " + expectedOutput + " but got " + actualOutput);
        }
        if (!expectedBook.equals(actualBook)) {
            if (expectedBook.getOrders().size() != actualBook.getOrders().size()) {
                System.out.println("Error! List of orders are not of the same size. Expected size "  + expectedBook.getOrders().size() + ", but got size " + actualBook.getOrders().size());
            } else {
                for (int i = 0; i < actualBook.getOrders().size(); i++) {
                    Order actualElement = expectedBook.getOrders().get(i);
                    Order expectedElement = actualBook.getOrders().get(i);  
                if (!(expectedElement.getShares() == actualElement.getShares())) {
                    System.out.println("Error in book! expected " + expectedElement + " but got " + actualElement);
                }
            }    
            }
        }
    }

    public static void main(String[] args) {
            Book actualBook = new Book('S');
            Book expectedBook = new Book('S');
            //test that if there is no match of oref, reduction will be 0
            test(expectedBook, actualBook, 5123, 100, 0);
            Order a0 = new Order("I,AMGN,A,S,100,550000,1000",0);
            actualBook.add(a0);
            Order a1 = new Order("I,AMGN,A,S,50,550000,1000",0);
            expectedBook.add(a1);
            //test that it will find a single order, and reduce by the correct amount
            test(expectedBook, actualBook, 1000, 50, 50);
            Order b0 = new Order("I,AMGN,A,S,100,540000,1001",1);
            Order b1 = new Order("I,AMGN,A,S,20,540000,1001",1);
            actualBook.add(b0);
            expectedBook.add(b1);
            //test that if there are two similar orders of different prices with the one you want lower (hence earlier in the list), it will find the correct price
            test(expectedBook, actualBook, 1001, 80, 80);
            Order c0 = new Order("I,AMGN,A,S,100,560000,1002",2);
            Order c1 = new Order("I,AMGN,A,S,90,560000,1002",2);
            actualBook.add(c0);
            expectedBook.add(c1);
            //test that if there are two similar orders of different prices with the one you want higher (hencer later in the list), it will find the correct price
            test(expectedBook, actualBook, 1002, 10, 10);
            //test that if there is match of oref but reduction of 0, it will return 0 and change nothing
            test(expectedBook, actualBook, 1000, 0, 0);
            Order d0 = new Order("I,AMGN,A,S,100,500000,1003", 3);
            actualBook.add(d0);
            // test that if you ask to reduce more than the number of shares, will only go down till 0, and then remove it
            test(expectedBook, actualBook, 1003, 150, 100);

    }
}

