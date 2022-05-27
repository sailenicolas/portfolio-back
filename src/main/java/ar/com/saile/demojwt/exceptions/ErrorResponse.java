package ar.com.saile.demojwt.exceptions;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {

    public ErrorResponse(String message, List<String> details) {

        super();
        this.message = message;
        this.details = details;
    }

    private String message;

    private List<String> details;
}
