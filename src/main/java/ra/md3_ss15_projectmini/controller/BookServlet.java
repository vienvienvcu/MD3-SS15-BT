package ra.md3_ss15_projectmini.controller;

import jdk.jfr.Category;
import ra.md3_ss15_projectmini.model.entity.Book;
import ra.md3_ss15_projectmini.model.entity.Categories;
import ra.md3_ss15_projectmini.model.service.IBookService;
import ra.md3_ss15_projectmini.model.service.ICategoriesService;
import ra.md3_ss15_projectmini.model.service.IGenericService;
import ra.md3_ss15_projectmini.model.seviceImpl.BookServiceImpl;
import ra.md3_ss15_projectmini.model.seviceImpl.CategoriesServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "BookServlet", value = "/BookServlet")
@MultipartConfig(
        fileSizeThreshold   = 1024 * 1024 * 1,  // 1 MB
        maxFileSize         = 1024 * 1024 * 10, // 10 MB
        maxRequestSize      = 1024 * 1024 * 15 // 15 MB
)
public class BookServlet extends HttpServlet {
    private IBookService<Book,Integer> bookService = new BookServiceImpl();
    private ICategoriesService<Categories,Integer> categoriesService = new CategoriesServiceImpl();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "add":
                formCreateBook(request,response);
                break;
            case "edit":
                formEditBook(request,response);
                break;
            case "delete":
                break;
            case "search":
                break;
            case "sort":
                break;
            default:
                bookList(request,response);
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
                insertBook(request,response);
                break;
            case "edit":
                break;
            default:
                bookList(request,response);
                break;
        }
    }

    private void bookList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Book> bookList = bookService.findAll();
        request.setAttribute("bookList", bookList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/book/bookList.jsp");
        dispatcher.forward(request,response);
    }

    private void formCreateBook(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/book/createBook.jsp");
        request.setAttribute("categories", categoriesService.findAll());
        dispatcher.forward(request,response);
    }

    private void insertBook(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
       int categoryId = Integer.parseInt(request.getParameter("category_id"));
       String name = request.getParameter("name");
       String image = request.getParameter("image");
       double price = Double.parseDouble(request.getParameter("price"));
       int stock = Integer.parseInt(request.getParameter("stock"));
       int totalPages = Integer.parseInt(request.getParameter("totalPages"));
       int yearCreated = Integer.parseInt(request.getParameter("yearCreated"));
       String author = request.getParameter("author");
       boolean status = Boolean.parseBoolean(request.getParameter("status"));
        // Tạo đối tượng User và lưu vào cơ sở dữ liệu
        Book newBook = new Book();
        newBook.setCategory_id(categoryId);
        newBook.setName(name);
        newBook.setPrice(price);
        newBook.setStock(stock);
        newBook.setTotalPages(totalPages);
        newBook.setYearCreated(yearCreated);
        newBook.setAuthor(author);
        newBook.setStatus(status);
        // Xử lý file
        String pathForImage = getServletContext().getRealPath("/images");
        File file = new File(pathForImage);
        if (!file.exists()) {
            file.mkdirs(); // Tạo thư mục nếu chưa tồn tại
        }

        for (Part part : request.getParts()) {
            if (part.getName().equals("image")) {
                newBook.setImage(part.getSubmittedFileName());
                part.write(pathForImage + File.separator + part.getSubmittedFileName());
            } else if (part.getName().equals("subImage")) {
                newBook.getListImage().add(part.getSubmittedFileName());
                part.write(pathForImage + File.separator + part.getSubmittedFileName());
            }
        }
        boolean result = bookService.save(newBook);
        if (result) {
            bookList(request,response);
            request.setAttribute("message", "added book successfully");
        }else {
            request.setAttribute("message", "failed to save book");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/book/createBook.jsp");
        dispatcher.forward(request,response);
    }

    private void formEditBook(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        Book book = bookService.getById(id);
        request.setAttribute("bookUpdate", book);
        request.setAttribute("categories", categoriesService.findAll());
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/book/editBookForm.jsp");
        dispatcher.forward(request,response);
    }


    public void destroy() {
    }
}