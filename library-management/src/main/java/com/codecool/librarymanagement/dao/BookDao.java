package com.codecool.librarymanagement.dao;

import com.codecool.librarymanagement.model.generated.Book;
import com.codecool.librarymanagement.model.generated.detailed.DetailedBook;
import com.codecool.librarymanagement.service.BookApiService;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class BookDao {

    private final BookApiService bookApiService;
    private final List<Book> bookList = new ArrayList<>();
    private final List<DetailedBook> detailedBookList = new ArrayList<>();
    private final List<String> categories = new ArrayList<>(
            Arrays.asList("csharp", "java", "javascript", "actionscript", "ajax",
                    "angular", "android", "django", "fsharp", "gimp", "google",
                    "html5", "html", "linux", "lego", "python", "ruby", "sap", "xml")
    );

    public BookDao(BookApiService bookApiService) {
        this.bookApiService = bookApiService;
    }

    public void initialise() {
        for (String category : categories) {
            for (Book book : bookApiService.getBookByCategory(category)) {
                book.setCategory(category);
                bookList.add(book);
            }
        }
    }

    public void initializeDetailedBooks() {
        for (Book book : bookList) {
            detailedBookList.add(bookApiService.getDetailedBooksByIsbn(book.getIsbn13(), book.getCategory()));
        }
    }

    public List<DetailedBook> sortBooksByParameter(){

       /* Comparator<DetailedBook> compare = (DetailedBook book1, DetailedBook book2) ->
                book1.getTitle().compareTo(book2.getTitle());
        detailedBookList.sort(compare);
        return detailedBookList;*/
        return detailedBookList.stream()
                .sorted(Comparator.comparing(DetailedBook::getTitle))
                .collect(Collectors.toList());



    }

    public List<Book> getBookList() {
        return bookList;
    }

    public List<DetailedBook> getDetailedBookList() {
        return detailedBookList;
    }
}
