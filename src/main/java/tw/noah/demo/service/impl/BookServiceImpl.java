package tw.noah.demo.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tw.noah.demo.dao.BookDao;
import tw.noah.demo.entity.Book;
import tw.noah.demo.service.BookService;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public Page<Book> findAllBooks(Pageable pageable) {
        return bookDao.findAll(pageable);
    }
}
