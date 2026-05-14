package com.example.multiagent.rest;

import com.example.multiagent.rest.dto.HelloRequestPayload;
import com.example.multiagent.rest.dto.HelloResponsePayload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class HelloRestController {
    private final GrpcGreeterClient grpcGreeterClient;

    public HelloRestController(GrpcGreeterClient grpcGreeterClient) {
        this.grpcGreeterClient = grpcGreeterClient;
    }

    @GetMapping
    public HelloResponsePayload hello(@RequestParam(defaultValue = "World") String name) {
        return new HelloResponsePayload(grpcGreeterClient.sayHello(name));
    }

    @PostMapping
    public HelloResponsePayload hello(@RequestBody(required = false) HelloRequestPayload request) {
        String name = request == null ? "World" : request.name();
        return new HelloResponsePayload(grpcGreeterClient.sayHello(name));
    }
}
