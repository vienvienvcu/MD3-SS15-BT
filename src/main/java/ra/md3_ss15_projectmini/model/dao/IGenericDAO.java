package ra.md3_ss15_projectmini.model.dao;

import java.util.List;

public interface IGenericDAO<T,V> {
    List<T> findAll();
    boolean save(T t);
    boolean update(T t);
    boolean delete(V id);
    T getById(V id);

}
