package com.ninjaTurtles.champtheatre.service;

public interface ForgotPasswordService {
    void sendPasswordResetEmail(String email);
    String generateResetToken();
    String generateResetLink(String resetToken);
    void resetPassword( String resetToken, String newPassword);
    boolean isValidResetToken(String resetToken);
}
