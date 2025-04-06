package com.example.mymodule;

import android.app.AlertDialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    private ArrayList<String> timeData;
    private ArrayList<String> eventData;

    public ScheduleAdapter(ArrayList<String> timeData, ArrayList<String> eventData) {
        this.timeData = timeData;
        this.eventData = eventData;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_item, parent, false);
        return new ScheduleViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        String time = timeData.get(position);
        holder.timeTextView.setText(time);

        // Подсвечиваем текущее время
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"));
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        int roundedCurrentMinute = (currentMinute < 30) ? 0 : 30;

        if (String.format("%02d:%02d", currentHour, roundedCurrentMinute).equals(time)) {
            holder.timeTextView.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.blue));
        } else {
            holder.timeTextView.setTextColor(Color.BLACK);
        }

        // Устанавливаем событие, если оно есть
        String event = eventData.get(position);
        holder.eventTextView.setText(event.isEmpty() ? "" : event);

        // Обработка клика на пустом месте для добавления события
//        holder.eventTextView.setOnClickListener(v -> showAddEventDialog(holder, position, time));
//        holder.itemView.setOnClickListener(v -> showAddEventDialog(holder, position, time));

        // Логика отображения прямоугольника для события
        View rectangle = holder.itemView.findViewById(R.id.event_rectangle);
        TextView eventInfoText = holder.itemView.findViewById(R.id.event_info_text);

        // Проверка наличия события
        if (!event.isEmpty()) {
            rectangle.setVisibility(View.VISIBLE); // Показать прямоугольник

            // Установить параметры прямоугольника для центрирования
            int height = holder.itemView.getHeight(); // Высота элемента
            int rectangleHeight = height; // Высота прямоугольника равна высоте элемента
            rectangle.getLayoutParams().height = rectangleHeight;
            rectangle.getLayoutParams().width = rectangleHeight; // Полная ширина
            rectangle.setTranslationY(-rectangleHeight / 2); // Центрирование по вертикали

            eventInfoText.setText(event); // Устанавливаем текст события
            eventInfoText.setVisibility(View.VISIBLE); // Показать текст события
        } else {
            rectangle.setVisibility(View.GONE); // Скрыть прямоугольник, если события нет
            eventInfoText.setVisibility(View.GONE); // Скрыть текст события
        }
    }
    private void showAddEventDialog(ScheduleViewHolder holder, int position, String time) {
        AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
        builder.setTitle("Добавить событие");

        View dialogView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.dialog_add_event, null);
        builder.setView(dialogView);

        final EditText input = dialogView.findViewById(R.id.event_input);
        final Spinner startHourPicker = dialogView.findViewById(R.id.start_hour_picker);
        final Spinner endHourPicker = dialogView.findViewById(R.id.end_hour_picker);

        List<String> timeSlots = generateTimeSlots();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(holder.itemView.getContext(), android.R.layout.simple_spinner_item, timeSlots);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        startHourPicker.setAdapter(adapter);
        endHourPicker.setAdapter(adapter);

        String startTime = time;
        String endTime = String.format("%02d:%02d", Integer.parseInt(time.split(":")[0]), (Integer.parseInt(time.split(":")[1]) + 30) % 60);

        startHourPicker.setSelection(timeSlots.indexOf(startTime));
        endHourPicker.setSelection(timeSlots.indexOf(endTime));

        builder.setPositiveButton("Сохранить", (dialog, which) -> {
            String newEvent = input.getText().toString();

            // Находим позицию для вставки нового события
            int startPosition = timeData.indexOf(startTime);
            if (startPosition != -1 && eventData.get(startPosition).isEmpty()) { // Проверяем, что событие отсутствует
                eventData.set(startPosition, newEvent); // Сохраняем новое событие на найденной позиции
                notifyItemChanged(startPosition); // Обновляем элемент
            }
        });

        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.cancel());
        builder.show();
    }



    private List<String> generateTimeSlots() {
        List<String> timeSlots = new ArrayList<>();
        for (int hour = 0; hour < 24; hour++) {
            for (int minute = 0; minute < 60; minute += 15) {
                timeSlots.add(String.format("%02d:%02d", hour, minute));
            }
        }
        return timeSlots;
    }

    @Override
    public int getItemCount() {
        return timeData.size();
    }

    // Единственное объявление ScheduleViewHolder
    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        public TextView timeTextView;
        public TextView eventTextView;
        public View eventRectangle; // Добавляем переменную для прямоугольника события

        public ScheduleViewHolder(View itemView) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.time_text_view);
            eventTextView = itemView.findViewById(R.id.event_text_view);
            eventRectangle = itemView.findViewById(R.id.event_rectangle); // Инициализируем переменную
        }
    }
}
