package com.server.tictactoe.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by klausvillaca on 1/17/16.
 */
public class DateUtils {

    /**
     * Get the now date and format to dd/MM/yyyy
     * @return
     */
    public static String formatDate() {
        final LocalDate date = LocalDate.now();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        final String formattedStringDate = date.format(formatter);
        return formattedStringDate;
    }
}
