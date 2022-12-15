package tw.noah.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tw.noah.demo.entity.Book;

public interface BookService {

    Page<Book> findAllBooks(Pageable pageable);
}
