package tw.noah.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.noah.demo.entity.Book;

@Repository
public interface BookDao extends JpaRepository<Book, String> {

}
