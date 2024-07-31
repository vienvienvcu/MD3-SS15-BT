package ra.md3_ss15_projectmini.model.dao;

import java.util.List;

public interface IBookDAO<T,V> extends IGenericDAO<T,V> {
    List<T> getBooksByName(String name);
    boolean getBooksByCategories(Integer id);
}
