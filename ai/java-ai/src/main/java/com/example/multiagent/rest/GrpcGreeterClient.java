package com.example.multiagent.rest;

import com.example.multiagent.grpc.GreeterGrpc;
import com.example.multiagent.grpc.HelloReply;
import com.example.multiagent.grpc.HelloRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class GrpcGreeterClient {

    @Value("${grpc.greeter.host:localhost}")
    private String host;

    @Value("${grpc.greeter.port:50051}")
    private int port;

    private ManagedChannel channel;
    private GreeterGrpc.GreeterBlockingStub stub;

    @PostConstruct
    void init() {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        stub = GreeterGrpc.newBlockingStub(channel);
    }

    public String sayHello(String name) {
        try {
            HelloReply reply = stub.sayHello(
                    HelloRequest.newBuilder()
                            .setName(name == null ? "" : name)
                            .build()
            );
            return reply.getMessage();
        } catch (StatusRuntimeException exception) {
            Status.Code statusCode = exception.getStatus().getCode();

            if (statusCode == Status.Code.UNAVAILABLE || statusCode == Status.Code.DEADLINE_EXCEEDED) {
                throw new GrpcBackendUnavailableException(
                        "gRPC backend is unavailable at %s:%d".formatted(host, port),
                        exception
                );
            }

            throw exception;
        }
    }

    @PreDestroy
    void shutdown() throws InterruptedException {
        if (channel != null) {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
