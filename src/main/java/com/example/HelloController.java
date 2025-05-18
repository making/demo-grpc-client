package com.example;

import com.example.proto.HelloRequest;
import com.example.proto.HelloResponse;
import com.example.proto.HelloServiceGrpc;
import com.google.common.collect.Streams;
import java.util.Iterator;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final HelloServiceGrpc.HelloServiceBlockingStub helloServiceStub;

    public HelloController(HelloServiceGrpc.HelloServiceBlockingStub helloServiceStub) {
        this.helloServiceStub = helloServiceStub;
    }

    @GetMapping(path = "/")
    public Reply sayHello(@RequestParam String greeting) {
        HelloResponse response = helloServiceStub.sayHello(HelloRequest.newBuilder().setGreeting(greeting).build());
        return new Reply(response.getReply());
    }

    @GetMapping(path = "/lots-of-replies")
    public List<Reply> lotsOfReplies(@RequestParam String greeting) {
        Iterator<HelloResponse> replies = helloServiceStub
            .lotsOfReplies(HelloRequest.newBuilder().setGreeting(greeting).build());
        return Streams.stream(replies).map(r -> new Reply(r.getReply())).toList();
    }

    public record Reply(String reply) {
    }

}
