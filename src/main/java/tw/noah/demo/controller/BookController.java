package tw.noah.demo.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.noah.demo.service.BookService;

@Log4j2
@RestController
@RequestMapping("/apis/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all/{page}")
    public Page findAllBooks(@PathVariable(name = "page") int page) {
        return bookService.findAllBooks(PageRequest.of(page, 20));
    }

}
