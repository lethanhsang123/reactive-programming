package com.sanglt.sec03;

import com.sanglt.models.sec03.Book;
import com.sanglt.models.sec03.Library;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Lec05Collection {

    private static final Logger log = LoggerFactory.getLogger(Lec05Collection.class);

    public static void main(String[] args) {

        var book = Book.newBuilder()
                .setAuthor("Author 1")
                .setTitle("Title 1")
                .setPublicationYear(1)
                .build();

        var book2 = Book.newBuilder()
                .setAuthor("Author 2")
                .setTitle("Title 2")
                .setPublicationYear(2)
                .build();

        var book3 = Book.newBuilder()
                .setAuthor("Author 1")
                .setTitle("Title 1")
                .setPublicationYear(1)
                .build();

        var library = Library.newBuilder().setName("Library 1").addAllBooks(List.of(book, book2, book3)).build();

        log.info("Library: {}", library);

    }

}
