<%--
  Created by IntelliJ IDEA.
  User: viennguyenthi
  Date: 2024/07/31
  Time: 9:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Product</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .container {
            max-width: 500px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
        }
        form {
            display: flex;
            flex-direction: column;
        }
        label{
            margin-bottom: 10px;
        }
        input[type="text"],
        input[type="number"],
        input[type="checkbox"] {
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"] {

            background-color: #304463;
            color: #FFC7ED;
            border: 1px solid #2F3645;
            padding: 6px 12px;
            text-align: center;
            display: inline-block;
            border-radius: 10px;
            font-weight: 800;
            font-size: 16px;
        }
        input[type="submit"]:hover {
            background-color: #7D8ABC;
            color: white;
        }
        .btn-group{
            margin-bottom: 20px;

        }
        .btn-group label{
            background-color: #304463;
            color: #FFC7ED;
            border: 1px solid #2F3645;
            padding: 6px 12px;
            text-align: center;
            display: inline-block;
            border-radius: 10px;
            font-weight: 800;
            font-size: 16px;
        }
        .btn-group label:hover{
            background-color: #7D8ABC;
            color: white;
        }
        label{
            color: #304463;
            font-size: 18px;
            font-weight: 700;
        }
        .btn-group p{
            color: #304463;
            font-size: 18px;
            font-weight: 700;
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
            margin-top: 20px;
        }
        .link:hover{
            background-color: #7D8ABC;
            color: white;
        }

        #category_id{
            padding: 8px;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Create New Book</h2>
    <form action="/BookServlet" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="add"/>
        <input type="hidden" name="id" value="id">

        <label for="category_id">Category:</label>
        <select id="category_id" name="category_id" required>
            <c:forEach var="category" items="${categories}">
                <option value="${category.id}">${category.name}</option>
            </c:forEach>
        </select>

        <label for="name">Book Name:</label>
        <input type="text" id="name" name="name" required>

        <label for="image">Book Image (URL):</label>
        <input type="file" id="image" name="image" required>

        <label for="subImages">Sub Book Images (URLs):</label>
        <input type="file" id="subImages" name="subImages[]" multiple required>

        <label for="price">Price:</label>
        <input type="number" id="price" name="price" step="0.01" required>

        <label for="stock">Stock:</label>
        <input type="number" id="stock" name="stock" required>

        <label for="totalPages">Total Page:</label>
        <input type="number" id="totalPages" name="totalPages" required>

        <label for="yearCreated">Year Created:</label>
        <input type="number" id="yearCreated" name="yearCreated" required>

        <label for="author">Author:</label>
        <input type="text" id="author" name="author" required>

        <div class="btn-group">
            <p>Status:</p>
            <label for="active">
                <input type="radio" name="status" id="active" value="true" required style="display: none"> Active
            </label>
            <label for="inactive">
                <input type="radio" name="status" id="inactive" value="false" required style="display: none"> Inactive
            </label>
        </div>
        <input type="submit" value="add" name="action">
    </form>
    <a href="/BookServlet" class="link"><< Back</a>
</div>

</body>
</html>

