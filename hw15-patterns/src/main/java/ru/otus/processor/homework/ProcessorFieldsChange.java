package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorFieldsChange implements Processor {

    @Override
    public Message process(Message message) {
        return message.copy();
    }
}
