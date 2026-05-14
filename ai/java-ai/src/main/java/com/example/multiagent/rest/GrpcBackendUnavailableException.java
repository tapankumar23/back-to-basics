package com.example.multiagent.rest;

public class GrpcBackendUnavailableException extends RuntimeException {

    public GrpcBackendUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
