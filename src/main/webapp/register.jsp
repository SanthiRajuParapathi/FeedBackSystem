<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script>
        function toggleFields() {
            const role = document.getElementById("role").value;
            document.getElementById("admissionField").style.display = (role === "student") ? "block" : "none";
            document.getElementById("passwordField").style.display = (role === "faculty") ? "block" : "none";
            document.getElementById("deptField").style.display = (role ? "block" : "none");
        }
    </script>
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow-lg" style="max-width: 500px; margin: auto;">
        <div class="card-header bg-primary text-white text-center">
            <h3>Create an Account</h3>
        </div>
        <div class="card-body">

            <form action="register" method="post">
                <div class="mb-3">
                    <label class="form-label">Name:</label>
                    <input type="text" name="name" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Email:</label>
                    <input type="email" name="email" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Register As:</label>
                    <select name="role" id="role" class="form-select" onchange="toggleFields()" required>
                        <option value="">--Select--</option>
                        <option value="student">Student</option>
                        <option value="faculty">Faculty</option>
                    </select>
                </div>

                <div class="mb-3" id="admissionField" style="display:none;">
                    <label class="form-label">Admission No:</label>
                    <input type="text" name="admissionNo" class="form-control">
                </div>

                <div class="mb-3" id="passwordField" style="display:none;">
                    <label class="form-label">Password:</label>
                    <input type="password" name="password" class="form-control">
                </div>

                <div class="mb-3" id="deptField" style="display:none;">
                    <label class="form-label">Department:</label>
                    <select name="department" class="form-select">
                        <option value="">--Select Department--</option>
                        <option value="CSE">CSE</option>
                        <option value="ECE">ECE</option>
                        <option value="EEE">EEE</option>
                        <option value="IT">IT</option>
                        <option value="MECH">MECH</option>
                        <option value="CIVIL">CIVIL</option>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary w-100">Register</button>
            </form>

            <% 
                String error = (String) request.getAttribute("error");
                if (error != null) { 
            %>
                <div class="alert alert-danger mt-3 text-center"><%= error %></div>
            <% } %>

            <hr>
            <p class="text-center">
                Already have an account? <a href="login.jsp">Login Here</a>
            </p>
            <p class="text-center text-muted"><b>Note:</b> You will receive an OTP on your email to complete registration.</p>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
