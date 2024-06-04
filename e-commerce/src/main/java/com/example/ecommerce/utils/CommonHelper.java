package com.example.ecommerce.utils;

import com.example.ecommerce.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Configuration
public class CommonHelper {

    @Autowired
    RedisRepository redisRepository;

    public String createHash(String phoneNumber) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest((phoneNumber).getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte hashByte : hashBytes) {
            String hex = Integer.toHexString(0xff & hashByte);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public String getRedisDetails(String sessionKey, String sessionId) {
        Object redisDetails = redisRepository.findById(sessionKey, sessionId);

        // Assuming redisDetails is already a String
        if (redisDetails instanceof String) {
            return (String) redisDetails;
        }
        return null;
    }
}
