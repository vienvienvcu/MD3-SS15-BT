package ra.md3_ss15_projectmini.model.daoImpl;

import ra.md3_ss15_projectmini.model.dao.IBookDAO;
import ra.md3_ss15_projectmini.model.entity.Book;
import ra.md3_ss15_projectmini.model.until.ConnectionDA;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements IBookDAO<Book,Integer> {
    @Override
    public List<Book> getBooksByName(String name) {
        Connection connection = null;
        CallableStatement callableStatement = null;
         List<Book> bookSearch = new ArrayList<>();
         try {
             connection = ConnectionDA.openConnection();
             callableStatement = connection.prepareCall("{call pro_searchBookByName(?)}");
             callableStatement.setString(1, name);
             ResultSet resultSet = callableStatement.executeQuery();
             while (resultSet.next()) {
                 Book book = new Book();
                 book.setId(resultSet.getInt("id"));
                 book.setCategory_id(resultSet.getInt("category_id"));
                 book.setName(resultSet.getString("name"));
                 book.setImage(resultSet.getString("image"));
                 book.setPrice(resultSet.getDouble("price"));
                 book.setStock(resultSet.getInt("stock"));
                 book.setTotalPages(resultSet.getInt("totalPages"));
                 book.setYearCreated(resultSet.getInt("yearCreated"));
                 book.setAuthor(resultSet.getString("author"));
                 book.setStatus(resultSet.getBoolean("status"));
                 bookSearch.add(book);
             }

         }catch (Exception e){
             e.printStackTrace();

         }finally {
             ConnectionDA.closeConnection(connection, callableStatement);
         }

        return bookSearch;
    }

    @Override
    public boolean getBooksByCategories(Integer id) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        boolean result = false;
//        Điều này đảm bảo rằng nếu có lỗi hoặc không có kết quả từ cơ sở dữ liệu, phương thức sẽ trả về false.
        try {
            connection = ConnectionDA.openConnection();
            callableStatement =connection.prepareCall("{call pro_hasBookIsCategories(?)}");
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                int bookCount = resultSet.getInt("bookCount");
                result = (bookCount >0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ConnectionDA.closeConnection(connection, callableStatement);
        }

        return result;
    }

    @Override
    public List<Book> findAll() {
        Connection connection = null;
        CallableStatement callableStatement = null;
        List<Book> booklist = null;
        try {
           connection = ConnectionDA.openConnection();
           callableStatement = connection.prepareCall("{call pro_getAllBook()}");
//           THUC HIEN procedure
           ResultSet resultSet = callableStatement.executeQuery();
           booklist = new ArrayList<>();
           while (resultSet.next()) {
               Book book = new Book();
               book.setId(resultSet.getInt("id"));
               book.setCategory_id(resultSet.getInt("category_id"));
               book.setName(resultSet.getString("name"));
               book.setImage(resultSet.getString("image"));
               book.setPrice(resultSet.getDouble("price"));
               book.setStock(resultSet.getInt("stock"));
               book.setTotalPages(resultSet.getInt("totalPages"));
               book.setYearCreated(resultSet.getInt("yearCreated"));
               book.setAuthor(resultSet.getString("author"));
               book.setStatus(resultSet.getBoolean("status"));
               booklist.add(book);
           }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ConnectionDA.closeConnection(connection, callableStatement);
        }
        return booklist;
    }

    @Override
    public boolean save(Book book) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        boolean result = true;
        try {
            connection = ConnectionDA.openConnection();

            callableStatement = connection.prepareCall("{call pro_insertBook(?,?,?,?,?,?,?,?,?,?)}");
//            thuc hien set gia tri cac tham so vao
            callableStatement.setInt(1, book.getCategory_id());
            callableStatement.setString(2, book.getName());
            callableStatement.setString(3, book.getImage());
            callableStatement.setDouble(4, book.getPrice());
            callableStatement.setInt(5, book.getStock());
            callableStatement.setInt(6, book.getTotalPages());
            callableStatement.setInt(7, book.getYearCreated());
            callableStatement.setString(8, book.getAuthor());
            callableStatement.setBoolean(9,book.isStatus());
            callableStatement.registerOutParameter(10, java.sql.Types.INTEGER);  // Đăng ký tham số đầu ra
            callableStatement.execute();
            // Lấy ID của sách mới chèn
            int newBookId = callableStatement.getInt(10);

            // Gọi stored procedure để chèn ảnh
            CallableStatement insertImageStatement = connection.prepareCall("{call pro_insertImage(?, ?)}");
            insertImageStatement.setString(1, book.getImage());  // Sử dụng ảnh của sách
            insertImageStatement.setInt(2, newBookId);
            insertImageStatement.execute();


        }catch (Exception e){
            result = false;

            e.printStackTrace();

        }finally {
            ConnectionDA.closeConnection(connection, callableStatement);
        }
        return result;
    }

    @Override
    public boolean update(Book book) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        boolean result = true;
        try {
            connection = ConnectionDA.openConnection();
            callableStatement = connection.prepareCall("{call pro_updatetBook(?,?,?,?,?,?,?,?,?,?)}");
            callableStatement.setInt(1, book.getId());
            callableStatement.setInt(2, book.getCategory_id());
            callableStatement.setString(3, book.getName());
            callableStatement.setString(4, book.getImage());
            callableStatement.setDouble(5, book.getPrice());
            callableStatement.setInt(6, book.getStock());
            callableStatement.setInt(7, book.getTotalPages());
            callableStatement.setInt(8, book.getYearCreated());
            callableStatement.setString(9, book.getAuthor());
            callableStatement.setBoolean(10, book.isStatus());
            // Thực hiện stored procedure
            callableStatement.executeUpdate();
        }catch (Exception e){
            result = false;
            e.printStackTrace();
        }finally {
            ConnectionDA.closeConnection(connection, callableStatement);
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
            callableStatement = connection.prepareCall("{call pro_deleteBook(?)}");
            callableStatement.setInt(1,id);
            // Thực hiện stored procedure
            int rowsAffected = callableStatement.executeUpdate();
            // Nếu số hàng bị ảnh hưởng lớn hơn 0, xem như xóa thành công
            if (rowsAffected > 0) {
                result = true;
            }
        }catch (Exception e){

            e.printStackTrace();
        }finally {
            ConnectionDA.closeConnection(connection, callableStatement);
        }

        return result;
    }

    @Override
    public Book getById(Integer id) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        Book bookPro = null;
        try {
            connection = ConnectionDA.openConnection();
            callableStatement = connection.prepareCall("{call pro_getBookById(?)}");
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                bookPro = new Book();
                bookPro.setId(resultSet.getInt("id"));
                bookPro.setCategory_id(resultSet.getInt("category_id"));
                bookPro.setName(resultSet.getString("name"));
                bookPro.setImage(resultSet.getString("image"));
                bookPro.setPrice(resultSet.getDouble("price"));
                bookPro.setStock(resultSet.getInt("stock"));
                bookPro.setTotalPages(resultSet.getInt("totalPages"));
                bookPro.setYearCreated(resultSet.getInt("yearCreated"));
                bookPro.setAuthor(resultSet.getString("author"));
                bookPro.setStatus(resultSet.getBoolean("status"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ConnectionDA.closeConnection(connection, callableStatement);
        }

        return bookPro;
    }
}
