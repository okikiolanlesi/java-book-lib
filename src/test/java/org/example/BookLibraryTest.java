package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Optional;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

public class BookLibraryTest {
    private BookLibrary bookLib;
    String testBook = "testBook";


    @BeforeEach
    public void setup(){
        bookLib = new BookLibrary();
        bookLib.addBook(testBook);
    }

    @Test
    public void testAddBook(){
        bookLib.addBook("testBook2");
        assertTrue(bookLib.books.containsKey("testBook2"));
    }
    @Test
    public void testBorrowBookWhenBookHasAlreadyBeenTaken(){
        bookLib.borrowBook(new Teacher(testBook));
        assertEquals(bookLib.borrowBook(new Teacher(testBook)), "book taken");
    }

    @Test
    public void testBorrowBookWhenBookDoesNotExist(){
        String fakeBook = "non existent book";
        assertEquals(bookLib.borrowBook(new Teacher(fakeBook)), "We dont have " + fakeBook + " in our library");
    }

    @Test
    public void testBorrowBookWhenBookIsAvailable(){
        assertEquals(bookLib.borrowBook(new Teacher(testBook)), "Book borrowed out to: Teacher");
    }

    @Test
    public void testBorrowBookFromPriorityQueue(){
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

        assertEquals(Optional.ofNullable(bookLib.books.get("book 1")), Optional.of(0) );
        assertEquals(Optional.ofNullable(bookLib.books.get("book 2")), Optional.of(1) );
    }

    @Test
    public void throwsExceptionsWhenPriorityQueueIsEmpty(){
        Comparator<Borrower> borrowerPriorityComparator = (s1, s2) -> {
            return s1.getPriority() - s2.getPriority();
        };
        PriorityQueue<Borrower> borrowerPriorityQueue = new PriorityQueue<>(borrowerPriorityComparator);

        assertThrows(InvalidParameterException.class, () -> bookLib.borrowBooksFromQueue(borrowerPriorityQueue));
    }

    @Test
    public void testIfPriorityQueueWorks(){
        Comparator<Borrower> borrowerPriorityComparator = (s1, s2) -> {
            return s1.getPriority() - s2.getPriority();
        };

        PriorityQueue<Borrower> borrowerPriorityQueue = new PriorityQueue<>(borrowerPriorityComparator);

        borrowerPriorityQueue.add(new JuniorStudent("book 1"));
        borrowerPriorityQueue.add(new SeniorStudent("book 1"));
        borrowerPriorityQueue.add(new Teacher("book 2"));
        borrowerPriorityQueue.add(new SeniorStudent("book 3"));
        borrowerPriorityQueue.add(new JuniorStudent("book 1"));
        borrowerPriorityQueue.add(new Teacher("book 1"));

        assertEquals(borrowerPriorityQueue.remove().getClass().getSimpleName(), "Teacher");
        assertEquals(borrowerPriorityQueue.remove().getClass().getSimpleName(), "Teacher");
        assertEquals(borrowerPriorityQueue.remove().getClass().getSimpleName(), "SeniorStudent");
        assertEquals(borrowerPriorityQueue.remove().getClass().getSimpleName(), "SeniorStudent");
        assertEquals(borrowerPriorityQueue.remove().getClass().getSimpleName(), "JuniorStudent");
        assertEquals(borrowerPriorityQueue.remove().getClass().getSimpleName(), "JuniorStudent");

    }
}
