package com.sanglt.sec03;

import com.sanglt.models.sec03.Book;
import com.sanglt.models.sec03.Library;

import java.util.List;

public class Lec05Collection {

    public static void main(String[] args) {

        // create books
        var book1 = Book.newBuilder()
                .setTitle("Harry potter - part 1")
                .setAuthor("j k rowling")
                .setPublicationYear(1997)
                .build();
        var book2 = book1.toBuilder().setTitle("harry potter - part 2").setPublicationYear(1998).build();
        var book3 = book2.toBuilder().setTitle("harry potter - part 3").setPublicationYear(1999).build();

        var library = Library.newBuilder()
//                .setName("fantasy library")
//                .addBooks(book1)
//                .addBooks(book2)
//                .addBooks(book3)
//                .addAllBooks(List.of(book1, book2, book3))
                .build();

        System.out.println("Library: " + library.getName());
        System.out.println(library.getName());
    }

}
