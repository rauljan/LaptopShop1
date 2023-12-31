package by.element.dateTimeService;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Lazy
public class DateTimeProvider {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

    @NotNull
    public String getCurrentDateTime() {
        return formatter.format(new Date());
    }
}