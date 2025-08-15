package Application;
import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import Application.DBUtil;

public class VerifyOTPServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String enteredOTP = request.getParameter("otp");
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("verifyEmail");

        if (email == null || email.isEmpty()) {
            response.sendRedirect("register.jsp");
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            String selectSql = "SELECT * FROM pending_users WHERE email = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setString(1, email);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                String storedOTP = rs.getString("otp");
                LocalDateTime expiryTime = rs.getTimestamp("otp_expiry").toLocalDateTime();

                if (expiryTime.isBefore(LocalDateTime.now())) {
                    request.setAttribute("error", "OTP expired. Please register again.");
                    request.getRequestDispatcher("verify_otp.jsp").forward(request, response);
                    return;
                }

                if (!enteredOTP.equals(storedOTP)) {
                    request.setAttribute("error", "Invalid OTP. Please try again.");
                    request.getRequestDispatcher("verify_otp.jsp").forward(request, response);
                    return;
                }

                // ✅ OTP Verified → Move to correct table
                String role = rs.getString("role");
                String name = rs.getString("name");

                if ("student".equals(role)) {
                    String admissionNo = rs.getString("admission_no");
                    String department = rs.getString("department");

                    String insertStudent = "INSERT INTO students (name, email, admission_no, department) VALUES (?, ?, ?, ?)";
                    PreparedStatement studentStmt = conn.prepareStatement(insertStudent);
                    studentStmt.setString(1, name);
                    studentStmt.setString(2, email);
                    studentStmt.setString(3, admissionNo);
                    studentStmt.setString(4, department);
                    studentStmt.executeUpdate();

                } else if ("faculty".equals(role)) {
                    String password = rs.getString("password");
                    String department = rs.getString("department");

                    String insertFaculty = "INSERT INTO faculty (name, email, password, department) VALUES (?, ?, ?, ?)";
                    PreparedStatement facultyStmt = conn.prepareStatement(insertFaculty);
                    facultyStmt.setString(1, name);
                    facultyStmt.setString(2, email);
                    facultyStmt.setString(3, password);
                    facultyStmt.setString(4, department);
                    facultyStmt.executeUpdate();
                }

                // ✅ Delete from pending_users
                String deleteSql = "DELETE FROM pending_users WHERE email = ?";
                PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
                deleteStmt.setString(1, email);
                deleteStmt.executeUpdate();

                session.removeAttribute("verifyEmail");
                session.setAttribute("message", "Email verified successfully. You can now log in.");
                response.sendRedirect("login.jsp");

            } else {
                request.setAttribute("error", "User not found or already verified. Please register again.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("verify_otp.jsp").forward(request, response);
        }
    }
}
