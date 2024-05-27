package jh.park.screenback.controller;

import jakarta.servlet.http.HttpServletResponse;
import jh.park.screenback.dto.IdTokenRequestDto;
import jh.park.screenback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity LoginWithGoogleOauth2(@RequestBody IdTokenRequestDto requestBody, HttpServletResponse response) {
        String authToken = userService.loginOAuthGoogle(requestBody);
        final ResponseCookie cookie = ResponseCookie.from("AUTH-TOKEN", authToken)
                .httpOnly(true)
                .maxAge(7 * 24 * 3600)
                .path("/")
                .secure(false)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/private/home")
    public Map<String, Object> home(@AuthenticationPrincipal OAuth2User oauth2User) {
        String name = Optional.ofNullable((String) oauth2User.getAttribute("name")).orElse("Unknown User");
        String email = Optional.ofNullable((String) oauth2User.getAttribute("email")).orElse("Unknown Email");

        return Map.of(
                "name", name,
                "email", email
        );
    }
}
