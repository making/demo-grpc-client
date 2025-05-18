package com.example;

import com.example.proto.HelloRequest;
import com.example.proto.ReactorHelloServiceGrpc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {

    private final ReactorHelloServiceGrpc.ReactorHelloServiceStub helloServiceStub;

    public HelloController(ReactorHelloServiceGrpc.ReactorHelloServiceStub helloServiceStub) {
        this.helloServiceStub = helloServiceStub;
    }

    @GetMapping(path = "/")
    public Mono<Reply> sayHello(@RequestParam String greeting) {
        return helloServiceStub.sayHello(HelloRequest.newBuilder().setGreeting(greeting).build())
            .map(r -> new Reply(r.getReply()));
    }

    @GetMapping(path = "/lots-of-replies")
    public Flux<Reply> lotsOfReplies(@RequestParam String greeting) {
        return helloServiceStub.lotsOfReplies(HelloRequest.newBuilder().setGreeting(greeting).build())
            .map(r -> new Reply(r.getReply()));
    }

    public record Reply(String reply) {
    }

}
