package jh.park.screenback.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jh.park.screenback.dto.IdTokenRequestDto;
import jh.park.screenback.dto.ReturnResponse;
import jh.park.screenback.model.User;
import jh.park.screenback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> LoginWithGoogleOauth2(@RequestBody IdTokenRequestDto idTokenRequestDto, HttpServletResponse response) {
        ReturnResponse returnResponse = userService.loginOAuthGoogle(idTokenRequestDto);

        final ResponseCookie cookie = ResponseCookie.from("AUTH-TOKEN", returnResponse.getJwtToken())
                .httpOnly(true)
                .maxAge(7 * 24 * 3600)
                .path("/")
                .secure(false)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("username", returnResponse.getUser().getUsername());
        responseBody.put("email", returnResponse.getUser().getEmail());

        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate the JWT token by removing it from the client's cookies
        Cookie cookie = new Cookie("AUTH-TOKEN", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); // Immediately expire the cookie
        response.addCookie(cookie);

        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }

    @PostMapping("/userinfo")
    public ResponseEntity<User> getUserInfo(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String jwtToken = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("AUTH-TOKEN".equals(cookie.getName())) {
                    jwtToken = cookie.getValue();
                }
            }
        }

        if (jwtToken == null) {
            System.out.println("No JWT token found in cookies");
            return ResponseEntity.status(401).build();
        }
        User user;
        try {
            user = userService.getUser(jwtToken);
        } catch (Exception e) {
            System.out.println("Exception occurred while fetching user info: " + e.getMessage());
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(user);
    }
}
