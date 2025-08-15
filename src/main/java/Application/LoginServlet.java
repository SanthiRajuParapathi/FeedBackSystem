package Application;
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password"); // Admission No for students, password for faculty
        String role = request.getParameter("role");

        System.out.println("=== LOGIN ATTEMPT ===");
        System.out.println("Email: " + email);
        System.out.println("Password/AdmissionNo: " + password);
        System.out.println("Role: " + role);

        try (Connection conn = DBUtil.getConnection()) {
            if ("student".equals(role)) {
                // ✅ Now also retrieving department
                String sql = "SELECT id, name, department FROM students WHERE email = ? AND admission_no = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int studentId = rs.getInt("id");
                    String studentName = rs.getString("name");
                    String department = rs.getString("department"); // ✅ NEW

                    System.out.println("Student Login Success → ID: " + studentId +
                                       " Name: " + studentName +
                                       " Dept: " + department);

                    HttpSession session = request.getSession();
                    session.setAttribute("studentId", studentId);
                    session.setAttribute("studentName", studentName);
                    session.setAttribute("department", department); // ✅ NEW

                    response.sendRedirect("dashboard.jsp");
                    return;
                } else {
                    System.out.println("Student Login Failed → No matching record found.");
                }

            } else if ("faculty".equals(role)) {
                String sql = "SELECT id, name FROM faculty WHERE email = ? AND password = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int facultyId = rs.getInt("id");
                    String facultyName = rs.getString("name");

                    System.out.println("Faculty Login Success → ID: " + facultyId +
                                       " Name: " + facultyName);

                    HttpSession session = request.getSession();
                    session.setAttribute("facultyId", facultyId);
                    session.setAttribute("facultyName", facultyName);

                    response.sendRedirect("FacultyDashboardServlet?facultyId=" + facultyId);
                    return;
                } else {
                    System.out.println("Faculty Login Failed → No matching record found.");
                }
            }

            // If neither student nor faculty login successful
            request.setAttribute("error", "Invalid email, password/admission no, or role.");
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Login failed", e);
        }
    }
}
