package Application;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import Application.DBUtil;

public class ResendOTPServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("verifyEmail");

        if (email == null || email.isEmpty()) {
            response.sendRedirect("register.jsp");
            return;
        }

        String newOTP = OTPService.generateOTP();
        LocalDateTime newExpiry = OTPService.getExpiryTime();

        try (Connection conn = DBUtil.getConnection()) {
            // Update new OTP in pending_users table
            String sql = "UPDATE pending_users SET otp = ?, otp_expiry = ? WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newOTP);
            stmt.setObject(2, newExpiry);
            stmt.setString(3, email);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                // Send new OTP via email
                EmailService.sendOTP(email, newOTP);
                request.setAttribute("message", "A new OTP has been sent to your email.");
            } else {
                request.setAttribute("error", "Unable to resend OTP. Try registering again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
        }

        request.getRequestDispatcher("verify_otp.jsp").forward(request, response);
    }
}

