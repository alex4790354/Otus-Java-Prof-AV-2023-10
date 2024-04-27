package ru.otus.dataprocessor;

import ru.otus.model.Measurement;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        // groups the output list by name, while summing the value fields
        Map<String, Double> groupedMap = data.stream()
                .collect(Collectors.groupingBy(Measurement::name, Collectors.summingDouble(Measurement::value)));
        return new TreeMap<>(groupedMap);
    }
}
