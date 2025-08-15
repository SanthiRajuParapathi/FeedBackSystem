<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Feedback Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(to right, #6a11cb, #2575fc);
            color: white;
            text-align: center;
            padding-top: 100px;
            font-family: Arial, sans-serif;
        }
        .container {
            background: rgba(255, 255, 255, 0.1);
            padding: 40px;
            border-radius: 15px;
            max-width: 500px;
            margin: auto;
            box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.3);
        }
        h1 {
            font-weight: bold;
            margin-bottom: 20px;
        }
        .btn-custom {
            width: 100%;
            margin-top: 10px;
            padding: 12px;
            font-size: 18px;
            font-weight: bold;
            border-radius: 8px;
        }
        footer {
            position: fixed;
            bottom: 10px;
            width: 100%;
            color: #ddd;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Feedback Management System</h1>
        <p>Welcome! Please select an option below to continue.</p>
        <a href="login.jsp" class="btn btn-light btn-custom">🔑 Login</a>
        <a href="register.jsp" class="btn btn-warning btn-custom">📝 Register</a>
    </div>

    <footer>
        &copy; 2025 College Feedback System | All Rights Reserved
    </footer>
</body>
</html>
