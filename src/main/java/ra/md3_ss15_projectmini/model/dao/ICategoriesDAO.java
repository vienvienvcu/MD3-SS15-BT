package ra.md3_ss15_projectmini.model.dao;

import java.util.List;

public interface ICategoriesDAO<T,V> extends IGenericDAO<T,V> {
    List<T> searchCategoriesByName(String name);
}
