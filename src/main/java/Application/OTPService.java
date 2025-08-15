package Application;

import java.time.LocalDateTime;
import java.util.Random;

public class OTPService {

    private static final int OTP_LENGTH = 6;  // OTP length
    private static final int EXPIRY_DURATION_MINUTES = 10;  // OTP expiry duration

    // ✅ Generate a random OTP
    public static String generateOTP() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));  // 0 to 9
        }
        return otp.toString();
    }

    // ✅ Get expiry time (current time + 10 minutes)
    public static LocalDateTime getExpiryTime() {
        return LocalDateTime.now().plusMinutes(EXPIRY_DURATION_MINUTES);
    }

    // ✅ Check if OTP expired
    public static boolean isOTPExpired(LocalDateTime expiryTime) {
        return expiryTime.isBefore(LocalDateTime.now());
    }
}
