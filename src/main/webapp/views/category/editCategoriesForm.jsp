<%--
  Created by IntelliJ IDEA.
  User: viennguyenthi
  Date: 2024/07/31
  Time: 8:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Category</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 500px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 8px;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-top: 60px;
        }
        h2 {
            text-align: center;
            color: #304463;
        }
        form {
            display: flex;
            flex-direction: column;
        }
        label {
            margin-bottom: 10px;
            color: #304463;
            font-size: 18px;
            font-weight: 700;
        }
        input[type="text"],
        input[type="file"],
        input[type="radio"] {
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: #304463;
            color: #FFC7ED;
            border: 1px solid #2F3645;
            padding: 10px 20px;
            border-radius: 10px;
            font-weight: 800;
            font-size: 16px;
            cursor: pointer;
            text-align: center;
        }
        input[type="submit"]:hover {
            background-color: #7D8ABC;
            color: white;
        }
        .btn-group {
            margin-bottom: 20px;
        }
        .btn-group p {
            color: #304463;
            font-size: 18px;
            font-weight: 700;
        }
        .btn-group label {
            background-color: #304463;
            color: #FFC7ED;
            border: 1px solid #2F3645;
            padding: 6px 12px;
            display: inline-block;
            border-radius: 10px;
            font-weight: 800;
            font-size: 16px;
            margin-right: 10px;
            cursor: pointer;
        }
        .btn-group label:hover {
            background-color: #7D8ABC;
            color: white;
        }
        .message {
            text-align: center;
            margin-top: 20px;
            color: green;
        }
        .link {
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
            margin-top: 20px;
        }
        .link:hover {
            background-color: #7D8ABC;
            color: white;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Create New Category</h2>
    <form action="CategoriesServlet" method="post" >
        <input type="hidden" name="action" value="edit">
        <input type="hidden" name="id" value="${categoryUpdate.id}">

        <label for="name">Category Name:</label>
        <input type="text" id="name" name="name" value="${categoryUpdate.name}" required>


        <div class="btn-group">
            <p>Status :</p>
            <label for="active">Active</label>
            <input type="radio" name="status" id="active" value="true" ${categoryUpdate.status ? "checked" : ""}  style=" display: none">
            <label for="inactive">Inactive</label>
            <input type="radio" name="status" id="inactive" value="false" ${!categoryUpdate.status ? "checked" : ""}  style="display: none">
        </div>
        <input type="submit" value="add" name="action">
    </form>
</div>
<a href="/views/category/categoriesCreateForm.jsp" class="link"><< Back</a>
</body>
</html>



