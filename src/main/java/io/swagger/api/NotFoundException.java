package io.swagger.api;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-15T17:26:22.532+02:00[Europe/Berlin]")
public class NotFoundException extends ApiException {
    private final int code;
    public NotFoundException (int code, String msg) {
        super(code, msg);
        this.code = code;
    }
}
