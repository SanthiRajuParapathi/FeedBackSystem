<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Verify OTP</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
            text-align: center;
        }
        input[type="text"], input[type="submit"] {
            width: 90%;
            padding: 8px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        p {
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Email Verification</h2>
        <p>Please enter the OTP sent to your email</p>

        <form action="verifyOTP" method="post">
            <input type="text" name="otp" placeholder="Enter OTP" required>
            <input type="submit" value="Verify OTP">
        </form>

        <form action="resendOTP" method="get">
            <input type="submit" value="Resend OTP">
        </form>

        <% 
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <p style="color:red;"><%= error %></p>
        <% } %>

        <% 
            String message = (String) request.getAttribute("message");
            if (message != null) {
        %>
            <p style="color:green;"><%= message %></p>
        <% } %>
    </div>
</body>
</html>
