package com.pocotones.gettime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetDateTimeConsole {

    public static void main(String[] args) {

        LocalDateTime currentTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);

        System.out.println("Fecha y hora actual: " + formattedTime);

    }
}
