package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class BookLibrary {
    HashMap<String, Integer> books = new HashMap<String, Integer>();
    private static final Logger logger = LogManager.getLogger(BookLibrary.class);

    public void addBook(String book){
        if(books.get(book) == null){
            books.put(book, 1);
        }else {
            books.put(book, books.get(book) + 1);
        }
    }

    public String borrowBook(Borrower borrower){
        String book = borrower.bookToBeBorrowed;
        if(books.get(book) == null ){
            logger.error("We dont have " + book + " in our library");
            return "We dont have " + book + " in our library";
        } else if ( books.get(book) == 0) {
            logger.info("book taken");
            return "book taken";
        } else {
            borrower.borrowBook(book);
            books.put(book, books.get(book) - 1);
            logger.info("Book borrowed out to: " + borrower.getClass().getSimpleName());

            return "Book borrowed out to: " + borrower.getClass().getSimpleName();
        }
    }

    public void borrowBooksFromQueue(PriorityQueue<Borrower> borrowers) throws InvalidParameterException{
        if(borrowers.size() < 1){
            throw new InvalidParameterException("Priority queue must not be empty");
        }
        while(!borrowers.isEmpty()){
            borrowBook(borrowers.remove());
        }
    }
    public static void main(String[] args) {
        BookLibrary bookLib = new BookLibrary();

        bookLib.addBook("book 1");
        bookLib.addBook("book 2");
        bookLib.addBook("book 3");
        bookLib.addBook("book 1");
        bookLib.addBook("book 2");
        bookLib.addBook("book 1");


        Comparator<Borrower> borrowerPriorityComparator = (s1, s2) -> {
            return s1.getPriority() - s2.getPriority();
        };

        PriorityQueue<Borrower> borrowerPriorityQueue = new PriorityQueue<>(borrowerPriorityComparator);

        borrowerPriorityQueue.add(new Teacher("book 1"));
        borrowerPriorityQueue.add(new Teacher("book 2"));
        borrowerPriorityQueue.add(new JuniorStudent("book 1"));
        borrowerPriorityQueue.add(new SeniorStudent("book 1"));
        borrowerPriorityQueue.add(new SeniorStudent("book 3"));
        borrowerPriorityQueue.add(new JuniorStudent("book 1"));


        try{
            bookLib.borrowBooksFromQueue(borrowerPriorityQueue);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}