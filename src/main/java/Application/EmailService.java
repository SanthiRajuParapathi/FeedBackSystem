package Application;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailService {
    // Email configuration (Use your Gmail + App Password)
    private static final String FROM_EMAIL = "techwingeventmanagement@gmail.com";
    private static final String EMAIL_PASSWORD = "nien ufar anin skuz"; // Your Gmail App Password
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final int SMTP_PORT = 587;

    // Singleton mail session
    private static Session session;

    static {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, EMAIL_PASSWORD);
            }
        });
    }

    /** ✅ 1. Send OTP Email (Used for Registration Verification) */
    public static void sendOTP(String toEmail, String otp) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("Your OTP for Feedback System");

        String content = "Dear User,\n\n"
            + "Your OTP for email verification is: " + otp + "\n\n"
            + "This OTP is valid for 10 minutes.\n\n"
            + "Regards,\nFeedback Management Team";

        message.setText(content);
        Transport.send(message);
    }

    /** ✅ 2. Send Password Reset Email (Optional if you implement Forgot Password) */
    public static void sendPasswordResetEmail(String toEmail, String resetLink) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("Password Reset Request");

        String content = "Dear User,\n\n"
            + "Click the link below to reset your password:\n"
            + resetLink + "\n\n"
            + "This link will expire in 30 minutes.\n\n"
            + "Regards,\nFeedback Management Team";

        message.setText(content);
        Transport.send(message);
    }

    /** ✅ 3. Send Password Changed Confirmation (Optional) */
    public static void sendPasswordChangedConfirmation(String toEmail) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("Password Changed Successfully");

        String content = "Dear User,\n\n"
            + "Your password has been changed successfully.\n"
            + "If you did not make this change, please contact admin immediately.\n\n"
            + "Regards,\nFeedback Management Team";

        message.setText(content);
        Transport.send(message);
    }

    /** ✅ 4. Send Event Registration Confirmation (Optional for Event System) */
    public static void sendEventRegistrationEmail(String toEmail, String eventName) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("Event Registration Confirmation");

        String content = "Dear User,\n\n"
            + "You have successfully registered for the event: " + eventName + "\n\n"
            + "Regards,\nFeedback Management Team";

        message.setText(content);
        Transport.send(message);
    }

    /** ✅ 5. Send Feedback Notification to Faculty (NEWLY ADDED) */
    public static void sendFeedbackNotificationToFaculty(String toEmail, String facultyName, String studentName) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("New Feedback Received");

        String content = "Dear " + facultyName + ",\n\n"
            + "You have received new feedback from the student: " + studentName + ".\n"
            + "Please check your faculty dashboard to view the details.\n\n"
            + "Regards,\nFeedback Management Team";

        message.setText(content);
        Transport.send(message);
    }
}

