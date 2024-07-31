package ra.md3_ss15_projectmini.model.service;

import java.util.List;

public interface ICategoriesService <T,V>extends IGenericService<T,V>{
    List<T> searchCategoriesByName(String name);
}
