<%@ page import="java.sql.*, Application.DBUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Student Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card shadow-lg">
                <div class="card-header bg-success text-white text-center">
                    <h3>Welcome to the Student Dashboard</h3>
                </div>
                <div class="card-body">
                    <h5 class="text-primary mb-4">Submit Feedback for Faculty</h5>
                    
                    <form action="submitFeedback" method="post">

                        <!-- Faculty selection -->
                        <div class="mb-3">
                            <label for="facultyId" class="form-label">Select Faculty:</label>
                            <select name="facultyId" class="form-select" required>
                                <option value="">--Select--</option>
                                <%
                                    String studentDept = (String) session.getAttribute("department");
                                    try (Connection conn = DBUtil.getConnection()) {
                                        String sql = "SELECT id, name FROM faculty WHERE department = ?";
                                        PreparedStatement ps = conn.prepareStatement(sql);
                                        ps.setString(1, studentDept);
                                        ResultSet rs = ps.executeQuery();
                                        while (rs.next()) {
                                %>
                                    <option value="<%= rs.getInt("id") %>"><%= rs.getString("name") %></option>
                                <%
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                %>
                            </select>
                        </div>

                        <!-- Feedback Questions -->
                        <div class="mb-3">
                            <label class="form-label">1. Character:</label>
                            <select name="characterRating" class="form-select" required>
                                <option value="">--Select--</option>
                                <option value="1">1 - Poor</option>
                                <option value="2">2 - Fair</option>
                                <option value="3">3 - Good</option>
                                <option value="4">4 - Very Good</option>
                                <option value="5">5 - Excellent</option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">2. Teaching Skills:</label>
                            <select name="teachingSkills" class="form-select" required>
                                <option value="">--Select--</option>
                                <option value="1">1 - Poor</option>
                                <option value="2">2 - Fair</option>
                                <option value="3">3 - Good</option>
                                <option value="4">4 - Very Good</option>
                                <option value="5">5 - Excellent</option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">3. Communication Skills:</label>
                            <select name="communicationSkills" class="form-select" required>
                                <option value="">--Select--</option>
                                <option value="1">1 - Poor</option>
                                <option value="2">2 - Fair</option>
                                <option value="3">3 - Good</option>
                                <option value="4">4 - Very Good</option>
                                <option value="5">5 - Excellent</option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">4. Punctuality:</label>
                            <select name="punctuality" class="form-select" required>
                                <option value="">--Select--</option>
                                <option value="1">1 - Poor</option>
                                <option value="2">2 - Fair</option>
                                <option value="3">3 - Good</option>
                                <option value="4">4 - Very Good</option>
                                <option value="5">5 - Excellent</option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">5. Subject Knowledge:</label>
                            <select name="subjectKnowledge" class="form-select" required>
                                <option value="">--Select--</option>
                                <option value="1">1 - Poor</option>
                                <option value="2">2 - Fair</option>
                                <option value="3">3 - Good</option>
                                <option value="4">4 - Very Good</option>
                                <option value="5">5 - Excellent</option>
                            </select>
                        </div>

                        <!-- Additional Comments -->
                        <div class="mb-3">
                            <label for="comments" class="form-label">Additional Comments:</label>
                            <textarea name="comments" class="form-control" rows="4"></textarea>
                        </div>

                        <button type="submit" class="btn btn-success w-100">Submit Feedback</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
