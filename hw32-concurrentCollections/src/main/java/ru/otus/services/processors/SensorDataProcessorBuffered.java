package ru.otus.services.processors;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;
import ru.otus.lib.SensorDataBufferedWriter;


public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);
    private final PriorityBlockingQueue<SensorData> dataBuffer =
            new PriorityBlockingQueue<>(500, Comparator.comparing(SensorData::getMeasurementTime));
    private final int bufferSize;
    private final SensorDataBufferedWriter writer;

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
    }

    @Override
    public void process(SensorData data) {
        dataBuffer.add(data);
            if (dataBuffer.size() >= bufferSize) {
                flush();
            }
    }

    public void flush() {
        try {
            ArrayList<SensorData> list = new ArrayList<>();
            dataBuffer.drainTo(list);
            if (!list.isEmpty()) {
                writer.writeBufferedData(list);
            }
        } catch (Exception e) {
            log.error("Error during buffer writing process", e);
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}
