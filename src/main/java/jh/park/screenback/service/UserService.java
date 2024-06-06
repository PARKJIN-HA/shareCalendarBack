package jh.park.screenback.service;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import jh.park.screenback.dto.IdTokenRequestDto;
import jh.park.screenback.dto.ReturnResponse;
import jh.park.screenback.model.Gantt;
import jh.park.screenback.model.User;
import jh.park.screenback.model.UserGroup;
import jh.park.screenback.repository.UserRepository;
import jh.park.screenback.security.JWTUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JWTUtils jwtUtils;
    private final GoogleIdTokenVerifier verifier;

    public UserService(@Value("${spring.security.oauth2.client.registration.google.client-id}") String clientId, UserRepository userRepository,
                       JWTUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        NetHttpTransport transport = new NetHttpTransport();
        GsonFactory jsonFactory = new GsonFactory();
        verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();
    }

    public User getUser(String jwtToken) {
        Long userId = jwtUtils.getUserIdFromToken(jwtToken);
        return userRepository.findById(userId).orElse(null);
    }

    public ReturnResponse loginOAuthGoogle(IdTokenRequestDto requestBody) {
        User user = verifyIDToken(requestBody.getIdToken());
        if (user == null) {
            System.out.println("Exception");
            throw new IllegalArgumentException();
        }
        user = createOrUpdateUser(user);
        ReturnResponse returnResponse = new ReturnResponse();
        returnResponse.setJwtToken(jwtUtils.createToken( user, false));
        returnResponse.setUser(user);
        return returnResponse;
    }

    public User createOrUpdateUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser == null) {
            userRepository.save(user);
            return user;
        }
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        userRepository.save(existingUser);
        return existingUser;
    }

    private User verifyIDToken(String code) {
        try {
            GoogleAuthorizationCodeTokenRequest tokenRequest = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    new GsonFactory(),
                    "https://oauth2.googleapis.com/token",
                    "711179595342-nkfve6rbulc846pmhdqteint0fch7jt5.apps.googleusercontent.com",
                    "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",
                    code,
                    "http://localhost:8080"
            );

            TokenResponse tokRes = tokenRequest.execute();

            GoogleIdToken idTokenObj = verifier.verify((String) tokRes.get("id_token"));
            if (idTokenObj == null) {
                return null;
            }
            GoogleIdToken.Payload payload = idTokenObj.getPayload();
            String userName = (String) payload.get("name");
            String email = payload.getEmail();

            User user = new User();
            user.setUsername(userName);
            user.setEmail(email);

            return user;
        } catch (GeneralSecurityException | IOException e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public List<UserGroup> findUserGroupByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getGroups();
    }
}