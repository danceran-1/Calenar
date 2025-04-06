package com.example.mymodule;

import java.util.Calendar;
import java.text.SimpleDateFormat;

class CalendarEntry {
    private int index;
    private Calendar startDateTime;
    private Calendar endDateTime;
    private int entryCode;
    private int recurrenceCode;
    private boolean isCompleted;
    private boolean isReminderSet;
    private String reminderText; // Новое поле для текста напоминания

    // Обновлённый конструктор с текстом напоминания
    public CalendarEntry(int index, Calendar startDateTime, Calendar endDateTime,
                         int entryCode, int recurrenceCode, boolean isCompleted,
                         boolean isReminderSet, String reminderText) {
        this.index = index;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.entryCode = entryCode;
        this.recurrenceCode = recurrenceCode;
        this.isCompleted = isCompleted;
        this.isReminderSet = isReminderSet;
        this.reminderText = reminderText;
    }

    // Геттеры и сеттеры
    public String getEndTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return timeFormat.format(endDateTime.getTime());
    }
    public int getIndex() { return index; }
    public Calendar getStartDateTime() { return startDateTime; }
    public Calendar getEndDateTime() { return endDateTime; }
    public String getDescription() {
        return reminderText;
    }
    public int getEntryCode() { return entryCode; }
    public int getRecurrenceCode() { return recurrenceCode; }
    public String getRecurrenceDescription(int recurrenceCode) {
        switch (recurrenceCode) {
            case 0:
                return "Не повторяется";
            case 1:
                return "Ежедневно";
            case 2:
                return "Еженедельно";
            case 3:
                return "Ежемесячно";
            case 4:
                return "Ежегодно";
            default:
                return "Неизвестно";
        }
    }
    public boolean isCompleted() { return isCompleted; }
    public boolean isReminderSet() { return isReminderSet; }
    public String getReminderText() { return reminderText; } // Новый геттер для текста напоминания

    public void setCompleted(boolean completed) { isCompleted = completed; }
    public void setReminderSet(boolean reminderSet) { isReminderSet = reminderSet; }
    public void setReminderText(String reminderText) { this.reminderText = reminderText; } // Новый сеттер для текста напоминания
}
