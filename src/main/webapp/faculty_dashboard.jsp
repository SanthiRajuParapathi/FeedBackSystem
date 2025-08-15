<%@ page import="java.sql.*, Application.DBUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Faculty Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow-lg">
        <div class="card-header bg-primary text-white text-center">
            <h3>Student Feedbacks (Anonymous)</h3>
        </div>
        <div class="card-body">

            <%
                Integer facultyIdObj = (Integer) session.getAttribute("facultyId");

                if (facultyIdObj == null) {
            %>
                <div class="alert alert-danger text-center">
                    You are not logged in. Please <a href="login.jsp" class="alert-link">login</a>.
                </div>
            <%
                } else {
                    int facultyId = facultyIdObj;
            %>

            <div class="table-responsive">
                <table class="table table-bordered table-striped text-center align-middle">
                    <thead class="table-secondary">
                        <tr>
                            <th>Character</th>
                            <th>Teaching Skills</th>
                            <th>Communication Skills</th>
                            <th>Punctuality</th>
                            <th>Subject Knowledge</th>
                            <th>Comments</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            try (Connection conn = DBUtil.getConnection()) {
                                String sql = "SELECT character_rating, teaching_skills, communication_skills, punctuality, subject_knowledge, comments FROM feedback WHERE faculty_id = ?";
                                PreparedStatement ps = conn.prepareStatement(sql);
                                ps.setInt(1, facultyId);
                                ResultSet rs = ps.executeQuery();

                                boolean hasFeedback = false;
                                while (rs.next()) {
                                    hasFeedback = true;
                        %>
                            <tr>
                                <td><%= rs.getInt("character_rating") %></td>
                                <td><%= rs.getInt("teaching_skills") %></td>
                                <td><%= rs.getInt("communication_skills") %></td>
                                <td><%= rs.getInt("punctuality") %></td>
                                <td><%= rs.getInt("subject_knowledge") %></td>
                                <td><%= rs.getString("comments") == null ? "-" : rs.getString("comments") %></td>
                            </tr>
                        <%
                                }
                                if (!hasFeedback) {
                        %>
                            <tr>
                                <td colspan="6" class="text-muted">No feedback submitted yet.</td>
                            </tr>
                        <%
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                        %>
                            <tr>
                                <td colspan="6" class="text-danger">Error loading feedbacks!</td>
                            </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>

            <%
                } // end else
            %>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
