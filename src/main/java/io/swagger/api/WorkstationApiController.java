package io.swagger.api;

import io.swagger.log.LogService;

import io.swagger.model.Workstation;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.*;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import javax.servlet.http.HttpServletRequest;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-15T17:26:22.532+02:00[Europe/Berlin]")
@RestController
public class WorkstationApiController implements WorkstationApi {

    private static final Logger log = LoggerFactory.getLogger(WorkstationApiController.class);

    private final ObjectMapper objectMapper;


    @Autowired
    private WorkstationRepository workstationRepository;


    @Autowired
    private ApiUtils apiUtils;


    @Autowired
    private LogService logService;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public WorkstationApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Transactional
    public ResponseEntity<String> accessWorkstation(@NotNull @Parameter(in = ParameterIn.QUERY, description = "The workstation id", required = true, schema = @Schema()) @Valid @RequestParam(value = "workstation", required = true) String workstation, @NotNull @Parameter(in = ParameterIn.QUERY, description = "The password for login in clear text", required = true, schema = @Schema()) @Valid @RequestParam(value = "password", required = true) String password) {
        logService.log(request);

        apiUtils.simulateRandomDelay();
        if (apiUtils.shouldThrowException()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (apiUtils.getExceptionToThrow(request) != null) {
            return new ResponseEntity<>(apiUtils.getExceptionToThrow(request));
        }

        if (workstation != null && password != null && !workstation.isEmpty() && !password.isEmpty()) {
            if (workstationRepository.existsByWorkstation(workstation)) {
                return new ResponseEntity<>("Invalid workstation", HttpStatus.OK);
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
    public ResponseEntity<Workstation> createWorkstation(@Parameter(in = ParameterIn.DEFAULT, description = "Create a new Workstation", required = true, schema = @Schema()) @Valid @RequestBody Workstation body) {
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
                if (body.getId() != null && workstationRepository.existsById(body.getId())) {
                    return new ResponseEntity<>(workstationRepository.getOne(body.getId()), HttpStatus.CONFLICT);
                }
                workstationRepository.save(body);
                return new ResponseEntity<>(body, HttpStatus.CREATED);
            } catch (Exception e) {
                log.error("Error creating Workstation", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<Void> deleteWorkstation(@Parameter(in = ParameterIn.PATH, description = "Id of the Workstation to delete", required = true, schema = @Schema()) @PathVariable("workstationId") Long workstationId) {
        logService.log(request);

        apiUtils.simulateRandomDelay();
        if (apiUtils.shouldThrowException()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (apiUtils.getExceptionToThrow(request) != null) {
            return new ResponseEntity<>(apiUtils.getExceptionToThrow(request));
        }

        if (workstationId != null && !equals("")) {
            try {
                Workstation workstation = workstationRepository.findById(workstationId).orElse(null);
                if (workstation != null) {
                    workstationRepository.deleteById(workstationId);
                    return new ResponseEntity<>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                log.error("Error deleting Workstation", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<Workstation> retrieveWorkstation(@Parameter(in = ParameterIn.PATH, description = "Id of the Workstation to return", required = true, schema = @Schema()) @PathVariable("workstationId") Long workstationId) {
        logService.log(request);

        apiUtils.simulateRandomDelay();
        if (apiUtils.shouldThrowException()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (apiUtils.getExceptionToThrow(request) != null) {
            return new ResponseEntity<>(apiUtils.getExceptionToThrow(request));
        }

        if (workstationId != null && !equals("")) {
            try {
                Workstation workstation = workstationRepository.findById(workstationId).orElse(null);
                if (workstation != null) {
                    return new ResponseEntity<>(workstation, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                log.error("Error retrieving Workstation", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<Workstation> updateWorkstation(@Parameter(in = ParameterIn.DEFAULT, description = "Update an existent Workstation", required = true, schema = @Schema()) @Valid @RequestBody Workstation body) {
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
                Workstation existingWorkstation = workstationRepository.findById(body.getId()).orElse(null);
                if (existingWorkstation != null) {
                    existingWorkstation = body;
                    workstationRepository.save(body);
                    return new ResponseEntity<>(body, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                log.error("Error updating Workstation", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
