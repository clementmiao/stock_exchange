// Clement Miao

public class TestFindMatchingOrder {
    public static void test(Order expected, Book b, int price) {
        Order match = b.findMatchingOrder(price);
        System.out.println(match);
        System.out.println(expected);
        if (match != null && expected != null){
            if (!expected.equals(match)) {
                System.out.println("Failed! Expected " + expected + " but got " + match);
            }
        }
        
        else {
            if (match == null && expected != null) {
                System.out.println("Match is null when it shouldn't be");
            }
            
            else if (expected == null && match != null) {
                System.out.println("Expected null but got a value");
            }
        } 
        
    }

    public static void main(String[] args) {
        //our code for finding matches only checks the first element, so we are only using one
        //element in our testBook
        Book testBook = new Book('B');
        
        // testing for when the book is empty
        test(null,testBook,500000);
        
        //test for when it should find match
        Order testOrder = new Order("I,AMGN,A,B,100,500000,1000", 0);
        testBook.add(testOrder);
        test(testOrder, testBook, 500000);
        
        test(testOrder, testBook, 400000);
        
        //test for when it shouldn't find a match
        test(null, testBook, 600000);
    }
}
