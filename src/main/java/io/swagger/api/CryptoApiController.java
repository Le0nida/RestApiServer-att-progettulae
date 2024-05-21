package io.swagger.api;

import io.swagger.log.LogService;

import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.model.Crypto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import javax.transaction.Transactional;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-15T17:26:22.532+02:00[Europe/Berlin]")
@RestController
public class CryptoApiController implements CryptoApi {

    private static final Logger log = LoggerFactory.getLogger(CryptoApiController.class);

    private final ObjectMapper objectMapper;


    @Autowired
    private CryptoRepository cryptoRepository;


    @Autowired
    private ApiUtils apiUtils;


    @Autowired
    private LogService logService;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public CryptoApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Transactional
    public ResponseEntity<Crypto> createCrypto(@Parameter(in = ParameterIn.DEFAULT, description = "Create a new Crypto", required=true, schema=@Schema()) @Valid @RequestBody Crypto body) {
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
       if (body.getId() != null && cryptoRepository.existsById(body.getId())) {
           return new ResponseEntity<>(cryptoRepository.getOne(body.getId()), HttpStatus.CONFLICT);
       }
        cryptoRepository.save(body);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    } catch (Exception e) {
        log.error("Error creating Crypto", e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<Void> deleteCrypto(@Parameter(in = ParameterIn.PATH, description = "Id of the Crypto to delete", required=true, schema=@Schema()) @PathVariable("cryptoId") Long cryptoId) {
        logService.log(request);

       apiUtils.simulateRandomDelay();
       if (apiUtils.shouldThrowException()) {
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
       if (apiUtils.getExceptionToThrow(request) != null) {
            return new ResponseEntity<>(apiUtils.getExceptionToThrow(request));
       }

if (cryptoId != null && !equals("")) {
    try {
        Crypto crypto = cryptoRepository.findById(cryptoId).orElse(null);
        if (crypto != null) {
            cryptoRepository.deleteById(cryptoId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } catch (Exception e) {
        log.error("Error deleting Crypto", e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<Crypto> retrieveCrypto(@Parameter(in = ParameterIn.PATH, description = "Id of the Crypto to return", required=true, schema=@Schema()) @PathVariable("cryptoId") Long cryptoId) {
        logService.log(request);

       apiUtils.simulateRandomDelay();
       if (apiUtils.shouldThrowException()) {
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
       if (apiUtils.getExceptionToThrow(request) != null) {
            return new ResponseEntity<>(apiUtils.getExceptionToThrow(request));
       }

if (cryptoId != null && !equals("")) {
    try {
        Crypto crypto = cryptoRepository.findById(cryptoId).orElse(null);
        if (crypto != null) {
            return new ResponseEntity<>(crypto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } catch (Exception e) {
        log.error("Error retrieving Crypto", e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<String> transfer(@NotNull @Parameter(in = ParameterIn.QUERY, description = "The sender address" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "sender_address", required = true) String senderAddress,@NotNull @Parameter(in = ParameterIn.QUERY, description = "The receiver address" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "receiver_address", required = true) String receiverAddress,@NotNull @Parameter(in = ParameterIn.QUERY, description = "The amount to transfer" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "amount", required = true) String amount,@NotNull @Parameter(in = ParameterIn.QUERY, description = "The currency type" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "currency", required = true) String currency) {
        logService.log(request);

       apiUtils.simulateRandomDelay();
       if (apiUtils.shouldThrowException()) {
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
       if (apiUtils.getExceptionToThrow(request) != null) {
            return new ResponseEntity<>(apiUtils.getExceptionToThrow(request));
       }

if (receiverAddress != null && senderAddress != null && amount != null && currency != null) {

            if ("".equals(currency)) {
                return new ResponseEntity<String>("Currency can't be empty", HttpStatus.BAD_REQUEST);
            } else if ("".equals(amount)) {
                return new ResponseEntity<String>("Amount can't be empty", HttpStatus.BAD_REQUEST);
            } else if ("".equals(receiverAddress) || receiverAddress.contains("1") || receiverAddress.contains("0")) {
                return new ResponseEntity<String>("Receiver address is not valid", HttpStatus.BAD_REQUEST);
            } else if ("".equals(senderAddress) || senderAddress.contains("1")  || senderAddress.contains("0")) {
                return new ResponseEntity<String>("Sender address is not valid", HttpStatus.BAD_REQUEST);
            }

            if (senderAddress.contains("23") && senderAddress.contains("e")) {
                return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
            }
            else {
                return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<Crypto> updateCrypto(@Parameter(in = ParameterIn.DEFAULT, description = "Update an existent Crypto", required=true, schema=@Schema()) @Valid @RequestBody Crypto body) {
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
        Crypto existingCrypto = cryptoRepository.findById(body.getId()).orElse(null);
        if (existingCrypto != null) {
            existingCrypto = body;
            cryptoRepository.save(body);
            return new ResponseEntity<>(body, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } catch (Exception e) {
        log.error("Error updating Crypto", e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
