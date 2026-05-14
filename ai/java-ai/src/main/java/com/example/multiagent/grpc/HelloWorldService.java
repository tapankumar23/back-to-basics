package com.example.multiagent.grpc;

import io.grpc.stub.StreamObserver;

public class HelloWorldService extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        String name = request.getName().isBlank() ? "World" : request.getName().trim();
        HelloReply reply = HelloReply.newBuilder()
                .setMessage("Hello, " + name + "!")
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
