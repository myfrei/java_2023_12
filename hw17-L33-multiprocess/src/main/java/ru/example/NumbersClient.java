package ru.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class NumbersClient {

    private static final Logger logger = LoggerFactory.getLogger(NumbersClient.class);

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        NumbersServiceGrpc.NumbersServiceStub stub = NumbersServiceGrpc.newStub(channel);

        AtomicInteger currentValue = new AtomicInteger(0);
        AtomicInteger lastServerValue = new AtomicInteger(0);

        StreamObserver<NumbersProto.Number> responseObserver = new StreamObserver<>() {
            @Override
            public void onNext(NumbersProto.Number value) {
                lastServerValue.set(value.getValue());
                logger.info("new value: {}", lastServerValue.get());
            }

            @Override
            public void onError(Throwable t) {
                logger.error("Error in responseObserver", t);
            }

            @Override
            public void onCompleted() {
                logger.info("request completed");
            }
        };

        stub.generateNumbers(
                NumbersProto.NumberRange.newBuilder()
                        .setFirstValue(0)
                        .setLastValue(30)
                        .build(),
                responseObserver);

        for (int i = 0; i < 50; i++) {
            Thread.sleep(1000);
            currentValue.addAndGet(lastServerValue.getAndSet(0) + 1);
            logger.info("currentValue: {}", currentValue.get());
        }

        channel.shutdown();
    }
}