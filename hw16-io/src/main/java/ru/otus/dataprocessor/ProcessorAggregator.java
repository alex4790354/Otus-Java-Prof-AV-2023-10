package ru.otus.dataprocessor;

import ru.otus.model.Measurement;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        // groups the output list by name, while summing the value fields
        return data.stream()
                .collect(Collectors.groupingBy(Measurement::name, Collectors.summingDouble(Measurement::value)));
    }
}
