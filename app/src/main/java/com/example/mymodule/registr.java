package com.example.mymodule;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;


import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout; // Импорт LinearLayout
import android.widget.Spinner;
import android.widget.TextView; // Импорт TextView
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import java.util.Locale;
import java.util.TimeZone;


public class registr extends AppCompatActivity {
    private HashMap<Integer, String> scheduleData;
    private int currentMonth;
    private int currentYear;
    private final int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; // Количество дней в каждом месяце
    private final String[] monthNames = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
    private Button lastClickedButton = null;
    private int currentWeekOffset = 0;
    private List<Integer> yearDays;
    private Context context;
    int maxWeeksInMonth = (int) Math.ceil((daysInMonth[currentMonth] + getFirstDayOffset()) / 7.0);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr);
        ImageButton prevWeekButton = findViewById(R.id.buttonPreviousWeek);
        ImageButton nextWeekButton = findViewById(R.id.buttonNextWeek);
        ImageView addButton = findViewById(R.id.forma_add);
        View primoButton = findViewById(R.id.menu);
        LinearLayout weekDaysContainer = findViewById(R.id.weekDaysContainer);

        Calendar calendar = Calendar.getInstance();
        currentMonth = calendar.get(Calendar.MONTH);
        currentYear = calendar.get(Calendar.YEAR);


        currentWeekOffset = calculateCurrentWeekOffset();


        updateWeekDays(weekDaysContainer);



        updateWeekDays(weekDaysContainer);
        RecyclerView scheduleList = findViewById(R.id.schedule_list);
        scheduleList.setLayoutManager(new LinearLayoutManager(this));


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddEventDialog();
            }
        });
        prevWeekButton.setOnClickListener(v -> {
            // Сохраняем текущее положение контейнера
            float startTranslationX = weekDaysContainer.getTranslationX();
            weekDaysContainer.setTranslationX(startTranslationX); // Устанавливаем текущее положение

            // Анимация сдвига вправо
            weekDaysContainer.animate()
                    .translationX(weekDaysContainer.getWidth()) // Сдвигаем контейнер вправо на его ширину
                    .setDuration(300)
                    .withEndAction(() -> {
                        currentWeekOffset--;
                        if (currentWeekOffset < 0) {
                            currentMonth--;
                            if (currentMonth < 0) {
                                currentMonth = 11;
                                currentYear--;
                            }
                            currentWeekOffset = (daysInMonth[currentMonth] + getFirstDayOffset() - 1) / 7; // Устанавливаем на последнюю неделю месяца
                        }
                        updateWeekDays(weekDaysContainer);

                        // Сдвигаем контейнер влево для появления
                        weekDaysContainer.setTranslationX(-weekDaysContainer.getWidth()); // Устанавливаем его за пределами экрана
                        weekDaysContainer.animate()
                                .translationX(0) // Возвращаем на место
                                .setDuration(300)
                                .start();
                    })
                    .start();
        });

        nextWeekButton.setOnClickListener(v -> {
            // Сохраняем текущее положение контейнера
            float startTranslationX = weekDaysContainer.getTranslationX();
            weekDaysContainer.setTranslationX(startTranslationX); // Устанавливаем текущее положение

            // Анимация сдвига влево
            weekDaysContainer.animate()
                    .translationX(-weekDaysContainer.getWidth()) // Сдвигаем контейнер влево на его ширину
                    .setDuration(300)
                    .withEndAction(() -> {
                        currentWeekOffset++;
                        int totalWeeks = (daysInMonth[currentMonth] + getFirstDayOffset() - 1) / 7 + 1; // Общее количество недель в текущем месяце
                        if (currentWeekOffset >= totalWeeks) {
                            currentWeekOffset = 0;
                            currentMonth++;
                            if (currentMonth > 11) {
                                currentMonth = 0;
                                currentYear++;
                            }
                        }
                        updateWeekDays(weekDaysContainer);

                        // Сдвигаем контейнер вправо для появления
                        weekDaysContainer.setTranslationX(weekDaysContainer.getWidth()); // Устанавливаем его за пределами экрана
                        weekDaysContainer.animate()
                                .translationX(0) // Возвращаем на место
                                .setDuration(300)
                                .start();
                    })
                    .start();
        });





        primoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectionDialog();
            }
        });
        ArrayList<String> timeData = generateTimeData();
        ArrayList<String> eventData = new ArrayList<>(timeData.size());

        for (int i = 0; i < timeData.size(); i++) {
            eventData.add("");
        }

        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(timeData, eventData);
        scheduleList.setAdapter(scheduleAdapter);

        // Добавляем разделитель
        scheduleList.addItemDecoration(new DividerItemDecoration(this));

        scheduleData = new HashMap<>();

        // Инициализируем текущую дату

        currentMonth = calendar.get(Calendar.MONTH);
        currentYear = calendar.get(Calendar.YEAR);



        // Инициализация календаря


        // Настраиваем кнопки для перехода по месяцам



    }
    private void showCustomToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, null);

        TextView toastText = layout.findViewById(R.id.toast_text);
        toastText.setText(message);

        // Задайте цвет текста, если хотите сделать это программно
        toastText.setTextColor(getResources().getColor(R.color.Error)); // Замените на нужный цвет

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
    public void showAddEventDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Добавить событие");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_event, null);
        builder.setView(dialogView);

        final EditText input = dialogView.findViewById(R.id.event_input);
        final Spinner startHourPicker = dialogView.findViewById(R.id.start_hour_picker);
        final Spinner endHourPicker = dialogView.findViewById(R.id.end_hour_picker);




        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"));
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        // Установите начальное и конечное время по умолчанию
        int hour = currentHour;
        int minute = currentMinute;

        String starttime = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
        String endtime = String.format(Locale.getDefault(), "%02d:%02d", hour, (minute + 30) % 60);



        builder.setPositiveButton("Сохранить", (dialog, which) -> {
            String newEvent = input.getText().toString().trim();

            if(newEvent.isEmpty()){
                showCustomToast("Пожалуйста, введите событие.");
                return; // Завершаем выполнение метода, чтобы не добавлять пустое событие
            }
            // Добавление события в ScrollView

            // Обновление календаря

        });

        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss());

        builder.show();
    }
    private int getFirstDayOffset() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, currentYear);
        calendar.set(Calendar.MONTH, currentMonth);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // От 1 (Воскресенье) до 7 (Суббота)
        return (dayOfWeek + 5) % 7; // Преобразуем к началу недели на понедельник)
    }
    private int calculateCurrentWeekOffset() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, currentYear);
        calendar.set(Calendar.MONTH, currentMonth);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        // Найдем смещение первого дня месяца
        int firstDayOffset = getFirstDayOffset();

        // Установим календарь на текущий день месяца
        int today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int weekOfMonth = (today + firstDayOffset - 1) / 7;

        return weekOfMonth;
    }

    private void updateWeekDays(LinearLayout weekDaysContainer) {
        weekDaysContainer.removeAllViews();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, currentYear);
        calendar.set(Calendar.MONTH, currentMonth);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Calendar today = Calendar.getInstance();
        int todayDay = today.get(Calendar.DAY_OF_MONTH);

        int firstDayOffset = getFirstDayOffset(); // Определяем смещение начала месяца
        int startDay = (currentWeekOffset * 7) - firstDayOffset; // Считаем начальный день текущей недели

        for (int i = 0; i < 7; i++) {
            LinearLayout dayContainer = new LinearLayout(this);
            dayContainer.setOrientation(LinearLayout.VERTICAL);
            dayContainer.setGravity(Gravity.CENTER);

            Button dayButton = new Button(this);
            dayButton.setBackgroundResource(R.drawable.rounded_top_button);

            int dayNumber = startDay + i + 1;
            int displayMonth;
            int displayDay;

            if (dayNumber <= 0) {
                // Дни из предыдущего месяца
                int previousMonth = (currentMonth == 0) ? 11 : currentMonth - 1;
                displayMonth = previousMonth;
                int previousMonthDays = daysInMonth[previousMonth];
                displayDay = previousMonthDays + dayNumber;
            } else if (dayNumber > daysInMonth[currentMonth]) {
                // Дни из следующего месяца
                int nextMonth = (currentMonth == 11) ? 0 : currentMonth + 1;
                displayMonth = nextMonth;
                displayDay = dayNumber - daysInMonth[currentMonth];
            } else {
                displayDay = dayNumber;
                displayMonth = currentMonth;
                if (displayMonth == today.get(Calendar.MONTH) && displayDay == today.get(Calendar.DAY_OF_MONTH)) {
                    dayButton.setBackgroundResource(R.drawable.button_today_background);
                }

            }

            String monthLabelText = getMonthName(displayMonth);
            // Установка текста кнопки как название месяца и номер дня
            dayButton.setText(String.format("%d \n %s", displayDay,monthLabelText));
            dayButton.setTextColor(Color.BLACK);
            dayButton.setEnabled(true);



            // Настройка размеров и отступов для кнопки дня
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(135, 150);
            buttonParams.setMargins(0, 0, 0, 0);
            dayButton.setLayoutParams(buttonParams);

            // Добавляем кнопку в основной контейнер
            dayContainer.addView(dayButton);

            // Добавляем контейнер дня в основной контейнер
            weekDaysContainer.addView(dayContainer);
        }
    }


    private String getMonthName(int month) {
        String[] monthNames = {"Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"};
        return monthNames[month];
    }




    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    private ArrayList<String> generateTimeData() {
        ArrayList<String> timeData = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"));
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        // Приводим текущее время к ближайшей половине часа
        int roundedCurrentMinute = (currentMinute < 30) ? 0 : 30;

        for (int hour = 0; hour < 24; hour++) {
            for (int minute = 0; minute < 60; minute += 60) {
                String timeString = String.format("%02d:%02d", hour, minute);
                // Проверяем, совпадает ли текущее время с текущей меткой времени
                if (hour == currentHour && minute == roundedCurrentMinute) {
                    timeData.add(timeString); // Например, добавляем "(Текущая)" к времени
                } else {
                    timeData.add(timeString);
                }
            }
        }

        Log.d("TimeData", "Generated Time Data: " + timeData.toString()); // Логируем сгенерированные временные данные
        return timeData;
    }
    private void showSelectionDialog() {

        boolean isInGroppage = this instanceof registr;
        Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setCanceledOnTouchOutside(true); // Закрытие при нажатии вне окна

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(8, 25, 40, 32); // Отступы для LinearLayout
        layout.setBackgroundColor(Color.WHITE);
        layout.setBackgroundResource(R.drawable.round_right);

        TextView calenarText = new TextView(this);
        calenarText.setText("Календарь");

        calenarText.setPadding(5, 16, 16, 16);
        calenarText.setGravity(Gravity.CENTER);
        calenarText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        calenarText.setTextSize(30);
        Typeface customFont = ResourcesCompat.getFont(this, R.font.fonts_medium);
        calenarText.setTypeface(customFont);

        TextView weekText = new TextView(this);
        weekText.setText("Неделя");

        weekText.setPadding(32, 16, 16, 16);
        weekText.setBackgroundResource(R.drawable.rectangle_88);
        weekText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.week, 0, 0, 0);
        weekText.setCompoundDrawablePadding(20);
        weekText.setGravity(Gravity.CENTER_VERTICAL);
        weekText.setTextSize(16);
        Typeface customFont1 = ResourcesCompat.getFont(this, R.font.fonts_medium);
        weekText.setTypeface(customFont);

        if (isInGroppage) {

            weekText.setBackgroundResource(R.drawable.gold);
        }else {
            weekText.setBackgroundResource(R.drawable.rectangle_88);
        }

        LinearLayout.LayoutParams weekParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        weekParams.setMargins(0, 0, 0, 20); // Отступ внизу
        weekText.setLayoutParams(weekParams);

        TextView ukol = new TextView(this);
        ukol.setText("Привики");
        // Устанавливаем отступ слева на 2 пикселя
        ukol.setPadding(32, 16, 16, 16); // Изменено на 2 пикселя слева
        ukol.setBackgroundResource(R.drawable.rectangle_88);
        ukol.setCompoundDrawablesWithIntrinsicBounds(R.drawable.syringe_26_23, 0, 0, 0);
        ukol.setCompoundDrawablePadding(20);
        ukol.setGravity(Gravity.CENTER_VERTICAL);

        ukol.setTextSize(16);

        ukol.setTypeface(customFont1);

        LinearLayout.LayoutParams ukolParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        ukolParams.setMargins(0, 0, 0, 20); // Отступ внизу
        ukol.setLayoutParams(ukolParams);

        TextView monthText = new TextView(this);
        monthText.setText("Месяц");
        // Устанавливаем отступ слева на 2 пикселя
        monthText.setPadding(32, 16, 16, 16); // Изменено на 2 пикселя слева

        monthText.setBackgroundResource(R.drawable.rectangle_88);

        monthText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.month, 0, 0, 0);
        monthText.setCompoundDrawablePadding(20);
        monthText.setGravity(Gravity.CENTER_VERTICAL);

        monthText.setTextSize(16);

        monthText.setTypeface(customFont1);


        LinearLayout.LayoutParams monthParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        monthParams.setMargins(0, 0, 0, 20); // Отступ внизу
        monthText.setLayoutParams(monthParams);

        monthText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registr.this, MounthlyCalendar.class);
                startActivity(intent);
            }
        });
        weekText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registr.this, registr.class);
                startActivity(intent);
            }
        });

        layout.addView(calenarText);
        layout.addView(monthText);
        layout.addView(weekText);






        Button closeButton = new Button(this);
        closeButton.setText("Закрыть");
        closeButton.setBackgroundResource(R.drawable.okrug);

        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                800,
                100
        );
        buttonParams.gravity = Gravity.CENTER;
        buttonParams.setMargins(0, 20, 0, 0);
        closeButton.setLayoutParams(buttonParams);

        closeButton.setPadding(40, 16, 16, 16);

        closeButton.setOnClickListener(v -> {
            // Анимация при закрытии по кнопке "Закрыть"
            layout.animate()
                    .translationX(-layout.getWidth())
                    .setDuration(300)
                    .withEndAction(dialog::dismiss);
        });

        layout.addView(closeButton);

        dialog.setContentView(layout);
        dialog.show();

        // Устанавливаем параметры окна диалога
        Window window = dialog.getWindow();
        if (window != null) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.rectangle_51_color));

            // Устанавливаем параметры расположения
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.TOP | Gravity.START;
            params.y = 100;
            window.setAttributes(params);
        }
        dialog.setOnDismissListener(d -> {
            layout.animate()
                    .translationX(layout.getWidth())  // Анимация ухода вправо
                    .setDuration(1000)
                    .withEndAction(() -> {

                    });
        });

        // Переменные для отслеживания положения пальца
        final float[] startX = {0};
        final float[] translationX = {0};

        // Устанавливаем обработчик касания на layout для обработки свайпа
        layout.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX[0] = event.getX(); // Запоминаем начальную позицию касания
                    translationX[0] = layout.getTranslationX();
                    return true;

                case MotionEvent.ACTION_MOVE:
                    // Вычисляем смещение и ограничиваем его, чтобы не двигаться вправо от начального положения
                    float deltaX = event.getX() - startX[0];
                    float newTranslationX = Math.min(translationX[0] + deltaX, 0); // Ограничение справа
                    layout.setTranslationX(newTranslationX); // Смещаем layout
                    return true;

                case MotionEvent.ACTION_UP:
                    // Закрываем или возвращаем окно в зависимости от положения
                    if (layout.getTranslationX() < -layout.getWidth() / 6) { // Закрытие при смещении на треть ширины
                        layout.animate().translationX(-layout.getWidth()).setDuration(250).withEndAction(dialog::dismiss);
                    } else {
                        layout.animate().translationX(0).setDuration(300); // Возвращаем обратно
                    }
                    return true;
            }
            return false;
        });
        dialog.setOnShowListener(dialogInterface -> {
            View decorView = dialog.getWindow().getDecorView();
            decorView.setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Rect dialogBounds = new Rect();
                    layout.getGlobalVisibleRect(dialogBounds);

                    // Проверяем, было ли касание вне диалога
                    if (!dialogBounds.contains((int) event.getRawX(), (int) event.getRawY())) {
                        // Анимация закрытия вправо при нажатии вне окна, как у кнопки закрытия
                        layout.animate()
                                .translationX(-layout.getWidth())  // Перемещение влево
                                .setDuration(300)
                                .withEndAction(dialog::dismiss);
                        return true;
                    }
                }
                return false;
            });
        });


    }



}
