<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script>
        function updateLabel() {
            const role = document.getElementById("role").value;
            const label = document.getElementById("passLabel");

            if (role === "student") {
                label.innerText = "Admission No:";
            } else if (role === "faculty") {
                label.innerText = "Password:";
            } else {
                label.innerText = "Password / Admission No:";
            }
        }
    </script>
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card shadow-lg">
                <div class="card-header text-center bg-primary text-white">
                    <h4>Login</h4>
                </div>
                <div class="card-body">
                    <form action="login" method="post">
                        <div class="mb-3">
                            <label>Email:</label>
                            <input type="email" name="email" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label id="passLabel">Password / Admission No:</label>
                            <input type="text" name="password" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label>Login As:</label>
                            <select name="role" id="role" class="form-select" onchange="updateLabel()" required>
                                <option value="">--Select--</option>
                                <option value="student">Student</option>
                                <option value="faculty">Faculty</option>
                            </select>
                        </div>

                        <button type="submit" class="btn btn-primary w-100">Login</button>
                    </form>

                    <% String error = (String) request.getAttribute("error");
                       if (error != null) { %>
                        <p class="text-danger text-center mt-2"><%= error %></p>
                    <% } %>
                </div>
                <div class="card-footer text-center">
                    <small>Don't have an account? <a href="register.jsp">Sign Up</a></small>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
