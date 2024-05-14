package br.com.loginauthapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

public record AuthResponse(
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String message,

        @JsonFormat(timezone = "America/Sao_Paulo", pattern = "yyyy-MM-dd HH:mm:ss.SSS")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Date requestDate
) {
}
