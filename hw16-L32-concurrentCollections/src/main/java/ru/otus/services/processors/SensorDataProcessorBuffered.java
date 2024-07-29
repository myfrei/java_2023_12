package ru.otus.services.processors;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;
import ru.otus.lib.SensorDataBufferedWriter;

public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private final BlockingQueue<SensorData> dataBuffer;

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
        this.dataBuffer = new ArrayBlockingQueue<>(bufferSize);
    }

    @Override
    public void process(SensorData data) {
        if (!dataBuffer.offer(data)) {
            flush();
            dataBuffer.offer(data);
        }
    }

    public synchronized void flush() {
        if (dataBuffer.isEmpty()) {
            return;
        }
        try {
            List<SensorData> bufferedData = new ArrayList<>();
            dataBuffer.drainTo(bufferedData);
            bufferedData.sort(Comparator.comparing(SensorData::getMeasurementTime));
            writer.writeBufferedData(bufferedData);
        } catch (Exception e) {
            log.error("Error during buffer flush", e);
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}
