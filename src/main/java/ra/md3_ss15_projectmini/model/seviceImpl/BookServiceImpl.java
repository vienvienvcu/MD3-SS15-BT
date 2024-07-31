package ra.md3_ss15_projectmini.model.seviceImpl;

import ra.md3_ss15_projectmini.model.dao.IBookDAO;
import ra.md3_ss15_projectmini.model.daoImpl.BookDaoImpl;
import ra.md3_ss15_projectmini.model.entity.Book;
import ra.md3_ss15_projectmini.model.service.IBookService;

import java.util.List;

public class BookServiceImpl implements IBookService<Book,Integer>{
    private IBookDAO <Book,Integer> bookDao = new BookDaoImpl();
    @Override
    public List<Book> getBooksByName(String name) {
        return bookDao.getBooksByName(name);
    }

    @Override
    public boolean getBooksByCategories(Integer id ){
        return  bookDao.getBooksByCategories(id);
    }

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public boolean save(Book book) {
        return bookDao.save(book);
    }

    @Override
    public boolean update(Book book) {
        return bookDao.update(book);
    }

    @Override
    public boolean delete(Integer id) {
        return bookDao.delete(id);
    }

    @Override
    public Book getById(Integer id) {
        return bookDao.getById(id);
    }
}
