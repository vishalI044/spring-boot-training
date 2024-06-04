package com.example.ecommerce.service.implement;

import com.example.ecommerce.service.UserService;
import com.example.ecommerce.utils.CommonHelper;
import com.example.ecommerce.utils.EncryptDecrypt;
import com.example.ecommerce.utils.constants.Constants;
import com.example.ecommerce.entity.UserDetails;
import com.example.ecommerce.repository.RedisRepository;
import com.example.ecommerce.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.Instant;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    RedisRepository redisRepository;

    @Autowired
    Constants constants;

    @Autowired
    CommonHelper commonHelper;

    @Override
    public ResponseEntity<String> createUser(UserDetails userDetailsRequest) {
        try {
            String hash = commonHelper.createHash(userDetailsRequest.phone_number);
            userDetailsRequest.setHash(hash);
            userDetailsRequest.setCreated_at(Timestamp.from(Instant.now()));
            userDetailsRequest.setCreated_by(userDetailsRequest.getUserName());
            userDetailsRepository.save(userDetailsRequest);
            return ResponseEntity.status(HttpStatus.OK).body("user created successfully");
        } catch (NoSuchAlgorithmException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @Override
    public ResponseEntity<String> login(UserDetails userDetailsRequest) {
        try {
            UserDetails userDetails = userDetailsRepository.findByUserNameAndPassword(userDetailsRequest.userName, userDetailsRequest.password);
            if (userDetails == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            String sessionId = EncryptDecrypt.encrypt(
                    userDetails.getName() + "|" +
                            userDetails.getUserName() + "|" +
                            userDetails.getHash()
            );
            redisRepository.save(constants.REDIS_SESSION_KEY, userDetails.getUserName(), sessionId);
            return ResponseEntity.status(HttpStatus.OK)
                            .header(constants.SESSION_ID, sessionId).body("logged in");
        } catch (Exception e) {
            e.fillInStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Override
    public ResponseEntity<String> logout(String sessionId) throws Exception {
        String userName = (EncryptDecrypt.decrypt(sessionId).split("\\|"))[1];
        redisRepository.delete(constants.REDIS_SESSION_KEY, userName);
        return ResponseEntity.status(HttpStatus.OK).body("logged out");
    }
}
