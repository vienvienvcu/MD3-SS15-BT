package ra.md3_ss15_projectmini.model.seviceImpl;

import ra.md3_ss15_projectmini.model.dao.ICategoriesDAO;
import ra.md3_ss15_projectmini.model.daoImpl.CategoriesImpl;
import ra.md3_ss15_projectmini.model.entity.Categories;
import ra.md3_ss15_projectmini.model.service.ICategoriesService;

import java.util.List;

public class CategoriesServiceImpl implements ICategoriesService<Categories,Integer> {
    private ICategoriesDAO<Categories,Integer> categoriesDAO = new CategoriesImpl();
    @Override
    public List<Categories> searchCategoriesByName(String name) {
        return categoriesDAO.searchCategoriesByName(name);
    }

    @Override
    public List<Categories> findAll() {
        return categoriesDAO.findAll();
    }

    @Override
    public boolean save(Categories categories) {
        return categoriesDAO.save(categories);
    }

    @Override
    public boolean update(Categories categories) {
        return categoriesDAO.update(categories);
    }

    @Override
    public boolean delete(Integer id) {
        return categoriesDAO.delete(id);
    }

    @Override
    public Categories getById(Integer id) {
        return categoriesDAO.getById(id);
    }
}
