package ru.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class NumbersServer {

    private static final Logger logger = LoggerFactory.getLogger(NumbersServer.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server =
                ServerBuilder.forPort(8080).addService(new NumbersServiceImpl()).build();

        server.start();
        logger.info("Server started, listening on {}", server.getPort());
        server.awaitTermination();
    }

    static class NumbersServiceImpl extends NumbersServiceGrpc.NumbersServiceImplBase {

        @Override
        public void generateNumbers(
                NumbersProto.NumberRange request, StreamObserver<NumbersProto.Number> responseObserver) {
            int firstValue = request.getFirstValue();
            int lastValue = request.getLastValue();

            for (int i = firstValue + 1; i <= lastValue; i++) {
                NumbersProto.Number number =
                        NumbersProto.Number.newBuilder().setValue(i).build();
                responseObserver.onNext(number);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    logger.error("Thread interrupted", e);
                }
            }
            responseObserver.onCompleted();
        }
    }
}