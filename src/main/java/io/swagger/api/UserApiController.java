package io.swagger.api;

import io.swagger.log.LogService;

import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-15T17:26:22.532+02:00[Europe/Berlin]")
@RestController
public class UserApiController implements UserApi {

    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

    private final ObjectMapper objectMapper;


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ApiUtils apiUtils;


    @Autowired
    private LogService logService;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UserApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Transactional
    public ResponseEntity<User> createUser(@Parameter(in = ParameterIn.DEFAULT, description = "Create a new User", required = true, schema = @Schema()) @Valid @RequestBody User body) {
        logService.log(request);

        apiUtils.simulateRandomDelay();
        if (apiUtils.shouldThrowException()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (apiUtils.getExceptionToThrow(request) != null) {
            return new ResponseEntity<>(apiUtils.getExceptionToThrow(request));
        }

        String accept = request.getHeader("Accept");
        if (accept != null && accept.equals("application/json") && body != null) {
            try {
                if (body.getId() != null && userRepository.existsById(body.getId())) {
                    return new ResponseEntity<>(userRepository.getOne(body.getId()), HttpStatus.CONFLICT);
                }
                userRepository.save(body);
                return new ResponseEntity<>(body, HttpStatus.CREATED);
            } catch (Exception e) {
                log.error("Error creating User", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<Void> deleteUser(@Parameter(in = ParameterIn.PATH, description = "Id of the User to delete", required = true, schema = @Schema()) @PathVariable("userId") Long userId) {
        logService.log(request);

        apiUtils.simulateRandomDelay();
        if (apiUtils.shouldThrowException()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (apiUtils.getExceptionToThrow(request) != null) {
            return new ResponseEntity<>(apiUtils.getExceptionToThrow(request));
        }

        if (userId != null && !equals("")) {
            try {
                User user = userRepository.findById(userId).orElse(null);
                if (user != null) {
                    userRepository.deleteById(userId);
                    return new ResponseEntity<>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                log.error("Error deleting User", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @Transactional
    public ResponseEntity<String> loginAdmin(@NotNull @Parameter(in = ParameterIn.QUERY, description = "The username for admin", required = true, schema = @Schema()) @Valid @RequestParam(value = "username", required = true) String username, @NotNull @Parameter(in = ParameterIn.QUERY, description = "The password for admin in clear text", required = true, schema = @Schema()) @Valid @RequestParam(value = "password", required = true) String password) {
        logService.log(request);

        apiUtils.simulateRandomDelay();
        if (apiUtils.shouldThrowException()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (apiUtils.getExceptionToThrow(request) != null) {
            return new ResponseEntity<>(apiUtils.getExceptionToThrow(request));
        }

        if (username != null && password != null && !username.isEmpty() && !password.isEmpty()) {
            if (!userRepository.existsByUsername(username)) {
                return new ResponseEntity<>("Invalid username", HttpStatus.OK);
            }
            // Non utilizzare valori casuali, ma valori derivati dalla password
            /*else if (password.contains("ì") && password.contains("°") && password.contains("e") && password.contains("\"") && password.contains("*")) {
                return new ResponseEntity<>("Access Denied", HttpStatus.UNAUTHORIZED);
            } else if (password.contains("ì%") && password.contains("g°") && password.contains("er") && password.contains("ù\"") && password.contains("?*")) {
                return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
            }*/ else {
                try {
                    // Leggi il contenuto della pagina HTML della dashboard
                    ClassPathResource htmlResource = new ClassPathResource("static/admin_dashboard.html");
                    String htmlContent = StreamUtils.copyToString(htmlResource.getInputStream(), StandardCharsets.UTF_8);
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.TEXT_HTML);
                    return new ResponseEntity<>(htmlContent, headers, HttpStatus.OK);
                } catch (IOException e) {
                    log.error("Error reading HTML file", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
                //return new ResponseEntity<>("Wrong Password", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<String> loginUser(@NotNull @Parameter(in = ParameterIn.QUERY, description = "The username for login", required = true, schema = @Schema()) @Valid @RequestParam(value = "username", required = true) String username, @NotNull @Parameter(in = ParameterIn.QUERY, description = "The password for login in clear text", required = true, schema = @Schema()) @Valid @RequestParam(value = "password", required = true) String password) {
        logService.log(request);

        apiUtils.simulateRandomDelay();
        if (apiUtils.shouldThrowException()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (apiUtils.getExceptionToThrow(request) != null) {
            return new ResponseEntity<>(apiUtils.getExceptionToThrow(request));
        }

        if (username != null && password != null && !username.isEmpty() && !password.isEmpty()) {
            if (userRepository.existsByUsername(username)) {
                return new ResponseEntity<>("Invalid username", HttpStatus.OK);
            }
            // Non utilizzare valori casuali, ma valori derivati dalla password
            else if (password.contains("ì") && password.contains("°") && password.contains("e") && password.contains("\"") && password.contains("*")) {
                return new ResponseEntity<>("Access Denied", HttpStatus.UNAUTHORIZED);
            } else if (password.contains("ì%") && password.contains("g°") && password.contains("er") && password.contains("ù\"") && password.contains("?*")) {
                return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
            } else {
                return new ResponseEntity<>("Wrong Password", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<Void> logoutUser() {
        logService.log(request);

        apiUtils.simulateRandomDelay();
        if (apiUtils.shouldThrowException()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (apiUtils.getExceptionToThrow(request) != null) {
            return new ResponseEntity<>(apiUtils.getExceptionToThrow(request));
        }

        return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
    }

    @Transactional
    public ResponseEntity<Resource> showAdminLoginPage() {
        ClassPathResource htmlResource = new ClassPathResource("static/admin_login.html");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<>(htmlResource, headers, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<User> retrieveUser(@Parameter(in = ParameterIn.PATH, description = "Id of the User to return", required = true, schema = @Schema()) @PathVariable("userId") Long userId) {
        logService.log(request);

        apiUtils.simulateRandomDelay();
        if (apiUtils.shouldThrowException()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (apiUtils.getExceptionToThrow(request) != null) {
            return new ResponseEntity<>(apiUtils.getExceptionToThrow(request));
        }

        if (userId != null && !equals("")) {
            try {
                User user = userRepository.findById(userId).orElse(null);
                if (user != null) {
                    return new ResponseEntity<>(user, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                log.error("Error retrieving User", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<User> updateUser(@Parameter(in = ParameterIn.DEFAULT, description = "Update an existent User", required = true, schema = @Schema()) @Valid @RequestBody User body) {
        logService.log(request);

        apiUtils.simulateRandomDelay();
        if (apiUtils.shouldThrowException()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (apiUtils.getExceptionToThrow(request) != null) {
            return new ResponseEntity<>(apiUtils.getExceptionToThrow(request));
        }

        String accept = request.getHeader("Accept");
        if (accept != null && accept.equals("application/json")) {
            try {
                User existingUser = userRepository.findById(body.getId()).orElse(null);
                if (existingUser != null) {
                    existingUser = body;
                    userRepository.save(body);
                    return new ResponseEntity<>(body, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                log.error("Error updating User", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
