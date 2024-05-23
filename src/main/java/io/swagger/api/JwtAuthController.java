package io.swagger.api;

import io.swagger.configuration.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class JwtAuthController {

    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping("/token")
    public ResponseEntity<String> getToken(@RequestParam String username, @RequestParam String password) {
        if (authenticate(username, password)) {
            String token = jwtTokenService.generateToken(username);
            return ResponseEntity.ok(token);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{ \"error\": \"Invalid credentials\" }");
        }
    }

    private boolean authenticate(String username, String password) {
        return "mgrangier0".equals(username) && "yourpassword".equals(password);
    }
}
