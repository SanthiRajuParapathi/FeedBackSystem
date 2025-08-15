package Application;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import Application.EmailService;
import Application.OTPService;

public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String role = request.getParameter("role");
        String department = request.getParameter("department"); // Only for faculty
        String admissionNo = request.getParameter("admissionNo"); // Only for students
        String password = request.getParameter("password"); // Faculty password

        // ✅ Generate OTP and expiry
        String otp = OTPService.generateOTP();
        LocalDateTime expiry = OTPService.getExpiryTime();

        try (Connection conn = DBUtil.getConnection()) {
            // ✅ Save to `pending_users` table for email verification
            String sql = "INSERT INTO pending_users (name, email, password, role, department, admission_no, otp, otp_expiry) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password != null ? password : admissionNo);
            ps.setString(4, role);
            ps.setString(5, department); 
            ps.setString(6, admissionNo);
            ps.setString(7, otp);
            ps.setObject(8, expiry);

            ps.executeUpdate();

            // ✅ Send OTP Email
            EmailService.sendOTP(email, otp);

            // ✅ Save email in session to verify OTP
            HttpSession session = request.getSession();
            session.setAttribute("verifyEmail", email);

            // ✅ Redirect to OTP verification page
            response.sendRedirect("verify_otp.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Registration failed! Try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
