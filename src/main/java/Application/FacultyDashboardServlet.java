package Application;
import java.io.*;
import java.sql.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class FacultyDashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int facultyId = Integer.parseInt(request.getParameter("facultyId")); 

        try (Connection conn = DBUtil.getConnection()) {
            // Fetch all feedback details
            String feedbackSql = "SELECT s.name AS student_name, f.character_rating, f.teaching_skills, f.communication_skills, f.punctuality, f.subject_knowledge, f.comments " +
                                 "FROM feedback f JOIN students s ON f.student_id = s.id WHERE f.faculty_id = ?";
            PreparedStatement ps = conn.prepareStatement(feedbackSql);
            ps.setInt(1, facultyId);
            ResultSet rs = ps.executeQuery();

            List<Map<String, String>> feedbackList = new ArrayList<>();
            while (rs.next()) {
                Map<String, String> fb = new HashMap<>();
                fb.put("student_name", rs.getString("student_name"));
                fb.put("character_rating", rs.getString("character_rating"));
                fb.put("teaching_skills", rs.getString("teaching_skills"));
                fb.put("communication_skills", rs.getString("communication_skills"));
                fb.put("punctuality", rs.getString("punctuality"));
                fb.put("subject_knowledge", rs.getString("subject_knowledge"));
                fb.put("comments", rs.getString("comments"));
                feedbackList.add(fb);
            }

            // Fetch average ratings
            String avgSql = "SELECT AVG(character_rating) AS avg_character, AVG(teaching_skills) AS avg_teaching, " +
                            "AVG(communication_skills) AS avg_communication, AVG(punctuality) AS avg_punctuality, " +
                            "AVG(subject_knowledge) AS avg_subject_knowledge FROM feedback WHERE faculty_id = ?";
            PreparedStatement avgPs = conn.prepareStatement(avgSql);
            avgPs.setInt(1, facultyId);
            ResultSet avgRs = avgPs.executeQuery();

            Map<String, Double> averages = new HashMap<>();
            if (avgRs.next()) {
                averages.put("avg_character", avgRs.getDouble("avg_character"));
                averages.put("avg_teaching", avgRs.getDouble("avg_teaching"));
                averages.put("avg_communication", avgRs.getDouble("avg_communication"));
                averages.put("avg_punctuality", avgRs.getDouble("avg_punctuality"));
                averages.put("avg_subject_knowledge", avgRs.getDouble("avg_subject_knowledge"));
            }

            request.setAttribute("feedbackList", feedbackList);
            request.setAttribute("averages", averages);
            request.getRequestDispatcher("faculty_dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error fetching faculty dashboard data", e);
        }
    }
}
