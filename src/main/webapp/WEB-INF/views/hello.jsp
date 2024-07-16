<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>hello </title>
</head>
<body>
    <p>data(EL문법) : ${myData}</p>
    <p>data(jstl문법 - java코드) : <%
        String getData = (String)request.getAttribute("myData");
        out.print(getData);
    %></p>


    <h1>회원가입</h1>
    <form action="/hello/servlet/jsp/post" method="post">
        <div>
            <label for="name">name</label>
            <input type="text" name="name" id="name">
        </div>
        <div>

            <label for="email">email</label>
            <input type="text" name="email" id="email">
        </div>

        <div>
            <label for="password">password</label>
            <input type="text" name="password" id="password">
        </div>

        <button type="submit">제출</button>
    </form>
</body>
</html>