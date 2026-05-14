package com.example.multiagent.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class HelloWorldGrpcClient {
    public static void main(String[] args) throws InterruptedException {
        String name = args.length > 0 ? args[0] : "World";

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        try {
            GreeterGrpc.GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(channel);
            HelloReply reply = stub.sayHello(HelloRequest.newBuilder().setName(name).build());
            System.out.println(reply.getMessage());
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
