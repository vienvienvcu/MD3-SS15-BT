<%--
  Created by IntelliJ IDEA.
  User: viennguyenthi
  Date: 2024/07/30
  Time: 21:07
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>List Categories</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 80%;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }



        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even){background-color: #EEEDEB}

        th {
            background-color: #FFC7ED;
            color: #2F3645;
            font-weight: 700;
            font-size: 18px;
        }

        input[type=text] {
            width: 300px;
            box-sizing: border-box;
            border: 1px solid #2F3645;
            border-radius: 10px;
            font-size: 16px;
            background-color: white;
            background-position: 10px 10px;
            background-repeat: no-repeat;
            padding: 12px 20px 12px 40px;
            transition: width 0.4s ease-in-out;
        }

        form {
            margin-top: 30px;
            margin-bottom: 20px;
        }
        h2{
            text-align: center;
            color: #304463;
        }

        .link{
            text-decoration: none;
            background-color: #304463;
            color: #FFC7ED;
            border: 1px solid #2F3645;
            padding: 10px 12px;
            text-align: center;
            display: inline-block;
            border-radius: 3px;
            font-weight: 800;
            font-size: 20px;
        }
        .link:hover {
            background-color: #7D8ABC;
            color: white;
        }


        .content{
            display: flex;
            justify-content: space-around;
            align-items: center;
        }
        .item {
            text-decoration: none;
            background-color: #304463;
            color: #FFC7ED;
            border: 1px solid #2F3645;
            padding: 8px 10px;
            text-align: center;
            display: inline-block;
            border-radius: 3px;
            font-weight: 800;
            font-size: 14px;
        }
        .item:hover{
            background-color: #7D8ABC;
            color: white;
        }
        .link.back{
            margin-top: 200px;
        }
        .link:hover{
            background-color: #7D8ABC;
            color: white;
        }
        .text {
            font-size: 20px;
            font-weight: 600;
        }
    </style>
    <script>
        function confirmDelete(id) {
            if (confirm('Bạn có chắc chắn muốn xóa danh mục này không?')) {
                window.location.href = 'CategoriesServlet?action=delete&id=' + id;
            }
        }
    </script>
</head>
<body>
<div class="content">
    <div><a href="views/category/categoriesCreateForm.jsp" class="link" > +Add Categories</a></div>
    <form action="CategoriesServlet" method="get">
        <input type="hidden" name="action" value="search"/>
        <input type="text" name="name" placeholder="Search by name"/>
        <button type="submit" class="link">Search</button>
    </form>
</div>
<div class="container">
    <h2>List Categories</h2>
    <p style="color: red" class="text">${error}</p>
    <p style="color: green"class="text">${message}</p>
    <p style="color: red" class="text">${errorMessage}</p>
    <table border="1">
        <thead>
        <tr>
            <th>ID</th>
            <th>Categories Name</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="category" items="${categories}">
            <tr>
                <td>${category.id}</td>
                <td>${category.name}</td>
                <td>${category.status ?"Active" : "Inactive"}</td>
                <td>
                    <a href="CategoriesServlet?action=edit&id=${category.id} " class="item">Edit</a>
                    <a href="javascript:confirmDelete(${category.id})" class="item">Delete</a>
                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="/index.jsp" class="link back"><< Home</a>
</div>
</body>
</html>


