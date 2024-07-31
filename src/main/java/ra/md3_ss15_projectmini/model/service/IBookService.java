package ra.md3_ss15_projectmini.model.service;

import ra.md3_ss15_projectmini.model.dao.IGenericDAO;

import java.util.List;

public interface IBookService <T,V> extends IGenericService <T,V>{
    List<T> getBooksByName(String name);

    boolean getBooksByCategories(Integer id);
}
