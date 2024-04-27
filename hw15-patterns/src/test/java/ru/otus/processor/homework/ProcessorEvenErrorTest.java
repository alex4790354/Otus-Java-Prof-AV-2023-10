package ru.otus.processor.homework;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import ru.otus.model.Message;


class ProcessorEvenErrorTest {

    @Test
    void shouldThrowExceptionWhenInvokedInEvenSecond() {
        LocalDateTime evenSecond = LocalDateTime.of(2025, 1, 1, 12, 0, 2);
        TimeProvider mockTimeProvider = Mockito.mock(TimeProvider.class);
        when(mockTimeProvider.now()).thenReturn(evenSecond);

        ProcessorEvenError processor = new ProcessorEvenError(mockTimeProvider);

        Message message = new Message.Builder(1L).field10("Test message").build();
        assertThrows(RuntimeException.class, () -> processor.process(message));
    }

    @Test
    void shouldProcessNormallyWhenInvokedInOddSecond() {
        LocalDateTime oddSecond = LocalDateTime.of(2025, 1, 1, 12, 0, 3);
        TimeProvider mockTimeProvider = Mockito.mock(TimeProvider.class);
        when(mockTimeProvider.now()).thenReturn(oddSecond);

        ProcessorEvenError processor = new ProcessorEvenError(mockTimeProvider);
        Message message = new Message.Builder(1L).field10("Test message").build();

        assertSame(message, processor.process(message));
    }

}