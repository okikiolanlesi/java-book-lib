package org.example;

import java.util.HashMap;

public abstract class Borrower {

    HashMap<String, Integer> borrowedBooks = new HashMap<String, Integer>();
    protected Integer priority;
    String bookToBeBorrowed;

    public void borrowBook(String book){
        if(borrowedBooks.get(book) == null){
            borrowedBooks.put(book, 1);
        }else {
            borrowedBooks.put(book, borrowedBooks.get(book) + 1);
        }
    }

    public abstract Integer getPriority();
}

class Teacher extends  Borrower{
    private Integer priority = 1;
    public Teacher(String bookToBeBorrowed){
        this.bookToBeBorrowed = bookToBeBorrowed;
    }
    public Integer getPriority(){
        return this.priority;
    }
}

class SeniorStudent extends  Borrower{
    private Integer priority = 2;
    public SeniorStudent(String bookToBeBorrowed){
        this.bookToBeBorrowed = bookToBeBorrowed;
    }
    public Integer getPriority(){
        return this.priority;
    }

}

class JuniorStudent extends Borrower{
    private Integer priority = 3;
    public JuniorStudent(String bookToBeBorrowed){
        this.bookToBeBorrowed = bookToBeBorrowed;
    }
    public Integer getPriority(){
        return this.priority;
    }
}

