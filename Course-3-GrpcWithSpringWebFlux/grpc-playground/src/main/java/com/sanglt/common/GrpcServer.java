package com.sanglt.common;

import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class GrpcServer {

    private static final Logger log = LoggerFactory.getLogger(GrpcServer.class);
    private final Server server;

    private GrpcServer(Server server) {
        this.server = server;
    }

    public static GrpcServer create(BindableService... services) {
        return create(6565, services);
    }

    public static GrpcServer create(int port, BindableService... services) {
        var builder = ServerBuilder.forPort(port)
                /*
                .keepAliveTime(10, TimeUnit.SECONDS)
                The server will send a keep-alive ping to the client every 10 seconds
                if there are no data packets being sent.
                 */
                /*
                .keepAliveTimeout(1, TimeUnit.SECONDS)
                After sending a keep-alive ping, the server will wait 10 seconds
                for a response before considering the connection dead
                 */
                /*
                .maxConnectionIdle(25, TimeUnit.SECONDS)
                 Specifies the maximum amount of time a connection can remain idle before the server closes it.
                 This is useful for managing resources and ensuring that idle connections do not consume server resources indefinitely.
                 */
                ;
        Arrays.asList(services).forEach(builder::addService);
        return new GrpcServer(builder.build());
    }

    public GrpcServer start() {
        var services = server.getServices()
                .stream()
                .map(ServerServiceDefinition::getServiceDescriptor)
                .map(ServiceDescriptor::getName)
                .toList();
        try {
            server.start();
            log.info("gRPC server started. Listening on port: {}. Services: {}", server.getPort(), services);
            return this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void await() {
        try {
            server.awaitTermination();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        server.shutdownNow();
        log.info("gRPC server stopped");
    }

}
