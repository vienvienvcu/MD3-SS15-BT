package ra.md3_ss15_projectmini.controller;

import ra.md3_ss15_projectmini.model.entity.Book;
import ra.md3_ss15_projectmini.model.entity.Categories;
import ra.md3_ss15_projectmini.model.service.IBookService;
import ra.md3_ss15_projectmini.model.service.ICategoriesService;
import ra.md3_ss15_projectmini.model.seviceImpl.BookServiceImpl;
import ra.md3_ss15_projectmini.model.seviceImpl.CategoriesServiceImpl;

import java.io.*;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "CategoriesServlet", value = "/CategoriesServlet")
public class CategoriesServlet extends HttpServlet {

    private ICategoriesService<Categories,Integer> categoriesService = new CategoriesServiceImpl();
    private IBookService<Book,Integer> bookService = new BookServiceImpl();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "add":
                formCreateCategories(request,response);
                break;
            case "edit":
                formEditCategories(request,response);
                break;
            case "delete":
                deleteCategories(request,response);
                break;
            case "search":
                searchCategories(request,response);
                break;
            case "sort":
                break;
            default:
                listCategories(request,response);
                break;
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "add":
                insertCategories(request, response);
                break;
            case "edit":
                editCategories(request, response);
                break;
            default:
                listCategories(request,response);
                break;
        }
    }

    private void listCategories(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Categories> categoriesList = categoriesService.findAll();
        request.setAttribute("categories", categoriesList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/category/categoriesList.jsp");
        dispatcher.forward(request,response);

    }

    private void formCreateCategories(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/category/categoriesCreateForm.jsp");
        dispatcher.forward(request,response);
    }

    private void insertCategories(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Lấy giá trị của các tham số từ yêu cầu HTTP
       String name = request.getParameter("name");
       boolean status = Boolean.parseBoolean(request.getParameter("status"));
        // Tạo đối tượng Categories và thiết lập các thuộc tính
       Categories categories = new Categories();
       categories.setName(name);
       categories.setStatus(status);
        // Gọi phương thức save của categoriesService để lưu danh mục vào cơ sở dữ liệu
       boolean result = categoriesService.save(categories);
        // Kiểm tra kết quả lưu dữ liệu
        if (result) {
            // Nếu lưu thành công, gọi phương thức listCategories để hiển thị danh sách danh mục
         listCategories(request,response);
        }else {
            // Nếu lưu thất bại, thiết lập thông báo lỗi và chuyển hướng về trang tạo danh mục
            request.setAttribute("errorMessage", "Failed to add categories.");
            try {
             // Chuyển hướng tới trang tạo danh mục với thông báo lỗi
                request.getRequestDispatcher("views/category/categoriesCreateForm.jsp").forward(request, response);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void formEditCategories(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        Categories existingCategories  = categoriesService.getById(id);
        request.setAttribute("categoryUpdate", existingCategories);
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/category/editCategoriesForm.jsp");
        dispatcher.forward(request,response);
    }

    private void editCategories(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        boolean status = Boolean.parseBoolean(request.getParameter("status"));
        Categories categoryUpdate = categoriesService.getById(id);
        categoryUpdate.setName(name);
        categoryUpdate.setStatus(status);
        categoriesService.update(categoryUpdate);
        listCategories(request,response);
    }

    private void deleteCategories(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        // Kiểm tra category has book không
        boolean hasBook = bookService.getBooksByCategories(id);
        if (hasBook) {
            // Nếu có học sinh, hiển thị thông báo lỗi
            request.setAttribute("error", "=>> Categories cannot be delete because it has book.");
        }else {
            categoriesService.delete(id);
            request.setAttribute("message", "=>> Categories deleted successfully.");
        }
        request.setAttribute("categories", categoriesService.findAll());
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/category/categoriesList.jsp");
        dispatcher.forward(request,response);

    }

    private void searchCategories(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        List<Categories> categorySearch;
        categorySearch = categoriesService.searchCategoriesByName(name);
        if (categorySearch.isEmpty()) {
            request.setAttribute("errorMessage", "Category does not exist.");
        }else {
            request.setAttribute("categories", categorySearch);
        }
        request.setAttribute("categories", categoriesService.findAll());
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/category/categoriesList.jsp");
        dispatcher.forward(request,response);
    }

    public void destroy() {
    }
}