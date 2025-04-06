package com.example.mymodule;

import java.util.Calendar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import android.content.Context;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;


public class CalendarDatabase {
    private static final String FILE_NAME = "calendar_data.json";
    private List<CalendarEntry> entries;
    private Gson gson;
    private File file;

    // Конструктор
    public CalendarDatabase(Context context) {
        gson = new GsonBuilder().setPrettyPrinting().create();
        file = new File(context.getFilesDir(), FILE_NAME);
        entries = loadEntries();
    }

    // Метод для загрузки записей из файла JSON
    private List<CalendarEntry> loadEntries() {
        if (!file.exists()) {
            try {
                file.createNewFile(); // Создаем файл, если он не существует
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ArrayList<>(); // Возвращаем пустой список, если файл только что создан
        }

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<CalendarEntry>>(){}.getType();
            List<CalendarEntry> loadedEntries = gson.fromJson(reader, listType);
            return loadedEntries != null ? loadedEntries : new ArrayList<>(); // Проверяем на null
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Метод для сохранения записей в файл JSON
    private void saveEntries() {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(entries, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод добавления новой записи
    public void addEntry(CalendarEntry entry) {
        entries.add(entry);
        saveEntries();
    }

    // Метод получения записи по индексу
    public CalendarEntry getEntry(int index) {
        for (CalendarEntry entry : entries) {
            if (entry.getIndex() == index) return entry;
        }
        return null;
    }

    // Метод удаления записи по индексу
    public void deleteEntry(int index) {
        entries.removeIf(entry -> entry.getIndex() == index);
        saveEntries();
    }

    // Добавьте этот метод в CalendarDatabase для фильтрации событий по дате
    public List<CalendarEntry> getEntriesByDate(int year, int month, int day) {
        List<CalendarEntry> eventsOnDate = new ArrayList<>();
        for (CalendarEntry entry : entries) {
            Calendar startDateTime = entry.getStartDateTime();
            if (startDateTime.get(Calendar.YEAR) == year &&
                    startDateTime.get(Calendar.MONTH) == month &&
                    startDateTime.get(Calendar.DAY_OF_MONTH) == day) {
                eventsOnDate.add(entry);
            }
        }
        return eventsOnDate;
    }

    public List<CalendarEntry> getAllEventsFromToday(Calendar today) {
        // Загрузить все записи из базы данных
        List<CalendarEntry> allEntries = getAllEntries();

        // Отфильтровать записи начиная с текущей даты
        List<CalendarEntry> filteredEntries = new ArrayList<>();
        for (CalendarEntry entry : allEntries) {
            if (!entry.getStartDateTime().before(today)) {
                filteredEntries.add(entry);
            }
        }

        // Сортировка по дате и времени
        filteredEntries.sort(Comparator.comparing(CalendarEntry::getStartDateTime));
        return filteredEntries;
    }



    // Метод получения всех записей
    public List<CalendarEntry> getAllEntries() {
        return entries;
    }

    public String getJsonContent() {
        return gson.toJson(entries);
    }

}