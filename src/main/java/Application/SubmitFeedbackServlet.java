package Application;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import Application.EmailService; // ✅ Import EmailService

public class SubmitFeedbackServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ✅ 1. Get feedback form data
        int facultyId = Integer.parseInt(request.getParameter("facultyId"));
        int characterRating = Integer.parseInt(request.getParameter("characterRating"));
        int teachingSkills = Integer.parseInt(request.getParameter("teachingSkills"));
        int communicationSkills = Integer.parseInt(request.getParameter("communicationSkills"));
        int punctuality = Integer.parseInt(request.getParameter("punctuality"));
        int subjectKnowledge = Integer.parseInt(request.getParameter("subjectKnowledge"));
        String comments = request.getParameter("comments");

        // ✅ 2. Get logged-in student details from session
        HttpSession session = request.getSession();
        int studentId = (int) session.getAttribute("studentId");
        String studentName = (String) session.getAttribute("studentName");

        try (Connection conn = DBUtil.getConnection()) {
            // ✅ 3. Insert feedback into database
            String sql = "INSERT INTO feedback (student_id, faculty_id, character_rating, teaching_skills, communication_skills, punctuality, subject_knowledge, comments) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            ps.setInt(2, facultyId);
            ps.setInt(3, characterRating);
            ps.setInt(4, teachingSkills);
            ps.setInt(5, communicationSkills);
            ps.setInt(6, punctuality);
            ps.setInt(7, subjectKnowledge);
            ps.setString(8, comments);
            ps.executeUpdate();

            // ✅ 4. Get Faculty Email & Name for Notification
            String facultyEmail = null;
            String facultyName = null;

            String facultySql = "SELECT name, email FROM faculty WHERE id = ?";
            PreparedStatement facultyPs = conn.prepareStatement(facultySql);
            facultyPs.setInt(1, facultyId);
            ResultSet facultyRs = facultyPs.executeQuery();
            if (facultyRs.next()) {
                facultyName = facultyRs.getString("name");
                facultyEmail = facultyRs.getString("email");
            }

            // ✅ 5. Send Email Notification to Faculty
            if (facultyEmail != null) {
                try {
                    EmailService.sendFeedbackNotificationToFaculty(facultyEmail, facultyName, studentName);
                    System.out.println("✅ Email sent to Faculty: " + facultyEmail);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("⚠ Failed to send email to faculty.");
                }
            }

            // ✅ 6. Redirect to Thank You Page
            response.sendRedirect("thankyou.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Feedback submission failed", e);
        }
    }
}
