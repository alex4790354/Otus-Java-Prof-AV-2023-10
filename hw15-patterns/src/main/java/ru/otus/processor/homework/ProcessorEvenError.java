package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;


public class ProcessorEvenError implements Processor {
    private final TimeProvider timeProvider;

    public ProcessorEvenError(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    @Override
    public Message process(Message message) {
        if (timeProvider.now().getSecond() % 2 == 0) {
            throw new RuntimeException("Exception was thrown cause method was invoked in even second");
        }
        return message;
    }
}
