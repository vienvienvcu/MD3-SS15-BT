package ra.md3_ss15_projectmini.model.daoImpl;

import ra.md3_ss15_projectmini.model.dao.ICategoriesDAO;
import ra.md3_ss15_projectmini.model.entity.Categories;
import ra.md3_ss15_projectmini.model.until.ConnectionDA;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriesImpl implements ICategoriesDAO<Categories,Integer> {
    @Override
    public List<Categories> searchCategoriesByName(String name) {
        Connection connection = null;
       CallableStatement callableStatement = null;
       List<Categories> categoriesSearch = new ArrayList<>();
       try {
           connection = ConnectionDA.openConnection();
           callableStatement = connection.prepareCall("{call searchCategoriesByName(?)}");
           callableStatement.setString(1, name);
           ResultSet resultSet = callableStatement.executeQuery();
           while (resultSet.next()) {
               Categories categories = new Categories();
               categories.setId(resultSet.getInt("id"));
               categories.setName(resultSet.getString("name"));
               categories.setStatus(resultSet.getBoolean("status"));
               categoriesSearch.add(categories);
           }

       }catch (Exception e){
           e.printStackTrace();
       }finally {
           ConnectionDA.closeConnection(connection, callableStatement);
       }

        return categoriesSearch;
    }

    @Override
    public List<Categories> findAll() {
        Connection connection = null;
        CallableStatement callableStatement = null;
        List<Categories> categoriesList =null;
        try {
            connection = ConnectionDA.openConnection();
            callableStatement = connection.prepareCall("{call pro_getAllCategories()}");
            ResultSet resultSet = callableStatement.executeQuery();
            categoriesList = new ArrayList<>();
            while (resultSet.next()) {
                Categories categories = new Categories();
                categories.setId(resultSet.getInt("id"));
                categories.setName(resultSet.getString("name"));
                categories.setStatus(resultSet.getBoolean("status"));
                categoriesList.add(categories);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ConnectionDA.closeConnection(connection,callableStatement);
        }
        return categoriesList;
    }

    @Override
    public boolean save(Categories categories) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        boolean result = true;
        try {
            connection = ConnectionDA.openConnection();
            callableStatement = connection.prepareCall("{call pro_insertCategories(?,?)}");
            // Thiết lập giá trị cho các tham số của stored procedure
            callableStatement.setString(1, categories.getName());
            callableStatement.setBoolean(2, categories.isStatus());
            // Thực thi stored procedure để chèn dữ liệu
            callableStatement.executeUpdate();
        }catch (Exception e){
            result = false;
            e.printStackTrace();
        }finally {
            ConnectionDA.closeConnection(connection,callableStatement);
        }
        return result;
    }

    @Override
    public boolean update(Categories categories) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        boolean result = true;
        try {
            connection = ConnectionDA.openConnection();
            callableStatement = connection.prepareCall("{call pro_updateCategories(?,?,?)}");
            callableStatement.setInt(1, categories.getId());
            callableStatement.setString(2, categories.getName());
            callableStatement.setBoolean(3, categories.isStatus());
            callableStatement.executeUpdate();
        }catch (Exception e){
            result = false;
            e.printStackTrace();
        }finally {
            ConnectionDA.closeConnection(connection,callableStatement);
        }
        return result;
    }

    @Override
    public boolean delete(Integer id) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        boolean result = false;
        try {
            connection = ConnectionDA.openConnection();
            callableStatement = connection.prepareCall("{call pro_deleteCategories(?)}");
            callableStatement.setInt(1, id);
            // Thực hiện stored procedure
            int rowsAffected = callableStatement.executeUpdate();
            // Nếu số hàng bị ảnh hưởng lớn hơn 0, xem như xóa thành công
            if (rowsAffected > 0) {
                result = true;
            }
        }catch (Exception e){
           e.printStackTrace();
        }finally {
            ConnectionDA.closeConnection(connection,callableStatement);
        }
        return result;
    }

    @Override
    public Categories getById(Integer id) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        Categories categoriesPro = null;
        try {
            connection = ConnectionDA.openConnection();
            callableStatement = connection.prepareCall("{call pro_getCategoriesById(?)}");
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            categoriesPro = new Categories();
            while (resultSet.next()) {
                categoriesPro.setId(resultSet.getInt("id"));
                categoriesPro.setName(resultSet.getString("name"));
                categoriesPro.setStatus(resultSet.getBoolean("status"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ConnectionDA.closeConnection(connection,callableStatement);
        }
        return categoriesPro;
    }

}
