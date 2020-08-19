package ru.falin.RestaurantVotingSystem.web.convertor;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static ru.falin.RestaurantVotingSystem.util.DateTimeUtil.parseLocalDate;
import static ru.falin.RestaurantVotingSystem.util.DateTimeUtil.parseLocalTime;

public class DataTimeFormatters {

    public static class LocalDateFormatters implements Formatter<LocalDate> {

        @Override
        public LocalDate parse(String text, Locale locale) throws ParseException {
            return parseLocalDate(text);
        }

        @Override
        public String print(LocalDate ld, Locale locale) {
            return ld.format(DateTimeFormatter.ISO_LOCAL_TIME);
        }
    }

    public static class LocalTimeFormatters implements Formatter<LocalTime> {

        @Override
        public LocalTime parse(String text, Locale locale) throws ParseException {
            return parseLocalTime(text);
        }

        @Override
        public String print(LocalTime lt, Locale locale) {
            return lt.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }
}
