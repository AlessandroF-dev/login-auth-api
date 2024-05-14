package br.com.loginauthapi.utils;

public enum ConfigurationEnum {
    LOCAL_URL("http://localhost:4200"),
    POST_METHOD("POST"),
    GET_METHOD("GET"),
    DELETE_METHOD("DELETE"),
    PUT_METHOD("PUT");

    private final String value;

    ConfigurationEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
