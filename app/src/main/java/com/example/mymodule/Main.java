package com.example.mymodule;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;
import com.example.mymodule.MedicalEvent;

public class Main {
    public static void main(String[] args) {
        List<MedicalEvent> events = new ArrayList<>();

        // Создание даты и времени для первого события
        Calendar date1 = Calendar.getInstance();
        date1.set(2024, Calendar.OCTOBER, 24);
        Calendar startTime1 = Calendar.getInstance();
        startTime1.set(2024, Calendar.OCTOBER, 24, 9, 30);
        Calendar endTime1 = Calendar.getInstance();
        endTime1.set(2024, Calendar.OCTOBER, 24, 10, 0);

        // Добавление первого события
        events.add(new MedicalEvent("Осмотр терапевта", "Иванов И.И.", 101, date1, startTime1, endTime1));

        // Создание даты и времени для второго события
        Calendar date2 = Calendar.getInstance();
        date2.set(2024, Calendar.OCTOBER, 25);
        Calendar startTime2 = Calendar.getInstance();
        startTime2.set(2024, Calendar.OCTOBER, 25, 10, 15);
        Calendar endTime2 = Calendar.getInstance();
        endTime2.set(2024, Calendar.OCTOBER, 25, 10, 30);

        // Добавление второго события
        events.add(new MedicalEvent("Прививка от гриппа", "Петрова А.А.", 202, date2, startTime2, endTime2));
    }
}
