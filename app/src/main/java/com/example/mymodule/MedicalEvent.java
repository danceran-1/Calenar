package com.example.mymodule;

import java.util.Calendar;

public class MedicalEvent {
    private String procedureName;
    private String doctorName;
    private int roomNumber;
    private Calendar date;      // Полное время и дата события, включая начало
    private Calendar startTime; // Время начала процедуры
    private Calendar endTime;   // Время окончания процедуры

    // Конструктор
    public MedicalEvent(String procedureName, String doctorName, int roomNumber, Calendar date, Calendar startTime, Calendar endTime) {
        this.procedureName = procedureName;
        this.doctorName = doctorName;
        this.roomNumber = roomNumber;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Геттеры и сеттеры
    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    // Переопределение метода toString для удобного вывода информации
    @Override
    public String toString() {
        return "Процедура: " + procedureName + "\nДоктор: " + doctorName +
                "\nКабинет: " + roomNumber + "\nДата: " + formatCalendar(date) +
                "\nВремя начала: " + formatCalendarTime(startTime) +
                "\nВремя окончания: " + formatCalendarTime(endTime);
    }

    // Форматирование даты
    private String formatCalendar(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Месяцы в Calendar начинаются с 0
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return String.format("%04d-%02d-%02d", year, month, day);
    }

    // Форматирование времени
    private String formatCalendarTime(Calendar calendar) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return String.format("%02d:%02d", hour, minute);
    }
}
