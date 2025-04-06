package com.example.mymodule;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.RelativeLayout;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Switch;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;



public class MounthlyCalendar extends AppCompatActivity {
    private HashMap<Integer, String> scheduleData;
    private int currentMonth; // Индекс текущего месяца (0 - январь, 11 - декабрь)
    private int currentYear;
    private int currentDay;// Текущий den'
    private final int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; // Количество дней в каждом месяце
    private final String[] monthNames = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

    private Button lastClickedButton = null;
    private ImageView blurBackground;

    private void checkAndRequestNotificationPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                // Запрашиваем разрешение
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "EVENT_REMINDER";
            String channelName = "Напоминания о событиях";
            String channelDescription = "Канал для напоминаний";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkAndRequestNotificationPermission();
        createNotificationChannel();


        CalendarDatabase calendarDatabase = new CalendarDatabase(this);
        CalendarEntry entry = calendarDatabase.getEntry(666);
        if (entry == null) {
            Calendar startDateTime = Calendar.getInstance();
            startDateTime.set(2024, 10, 24, 19, 0, 0);
            Calendar endDateTime = Calendar.getInstance();
            endDateTime.set(2024, 10, 24, 19, 30, 0);
            CalendarEntry newEntry = new CalendarEntry(
                    666, startDateTime, endDateTime,
                    100, 100, false, true, "Процедура: Колоноскопия\nДоктор: Виталий Виталий Витайлевич\nКабинет: 34"
            );
            calendarDatabase.addEntry(newEntry);
        } else {
            calendarDatabase.deleteEntry(666);
            Calendar startDateTime = Calendar.getInstance();
            startDateTime.set(2024, 10, 24, 19, 0, 0);
            Calendar endDateTime = Calendar.getInstance();
            endDateTime.set(2024, 10, 24, 19, 30, 0);
            CalendarEntry newEntry = new CalendarEntry(
                    666, startDateTime, endDateTime,
                    100, 100, false, true, "Процедура: Колоноскопия\nДоктор: Виталий Виталий Витайлевич\nКабинет: 34"
            );
            calendarDatabase.addEntry(newEntry);
        }

        CalendarEntry entry1 = calendarDatabase.getEntry(667);
        if (entry1 == null) {
            Calendar startDateTime = Calendar.getInstance();
            startDateTime.set(2024, 10, 28, 13, 0, 0);
            Calendar endDateTime = Calendar.getInstance();
            endDateTime.set(2024, 10, 28, 13, 30, 0);
            CalendarEntry newEntry = new CalendarEntry(
                    667, startDateTime, endDateTime,
                    100, 100, false, true, "Напоминание о необходимости прохождения ежегодной диспансеризации"
            );
            calendarDatabase.addEntry(newEntry);
        } else {
            calendarDatabase.deleteEntry(667);
            Calendar startDateTime = Calendar.getInstance();
            startDateTime.set(2024, 11, 28, 13, 0, 0);
            Calendar endDateTime = Calendar.getInstance();
            endDateTime.set(2024, 11, 28, 13, 30, 0);
            CalendarEntry newEntry = new CalendarEntry(
                    667, startDateTime, endDateTime,
                    100, 100, false, true, "Напоминание о необходимости прохождения ежегодной диспансеризации"
            );
            calendarDatabase.addEntry(newEntry);
        }

        CalendarEntry entry2 = calendarDatabase.getEntry(668);
        if (entry2 == null) {
            Calendar startDateTime = Calendar.getInstance();
            startDateTime.set(2024, 10, 28, 13, 0, 0);
            Calendar endDateTime = Calendar.getInstance();
            endDateTime.set(2024, 10, 28, 13, 30, 0);
            CalendarEntry newEntry = new CalendarEntry(
                    668, startDateTime, endDateTime,
                    100, 100, false, true, "Событие: Прививка от столбняка\nКабнет: 308"
            );
            calendarDatabase.addEntry(newEntry);
        } else {
            calendarDatabase.deleteEntry(668);
            Calendar startDateTime = Calendar.getInstance();
            startDateTime.set(2024, 10, 28, 13, 0, 0);
            Calendar endDateTime = Calendar.getInstance();
            endDateTime.set(2024, 10, 28, 13, 30, 0);
            CalendarEntry newEntry = new CalendarEntry(
                    668, startDateTime, endDateTime,
                    100, 100, false, true, "Событие: Прививка от столбняка\nКабнет: 308"
            );
            calendarDatabase.addEntry(newEntry);
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groppage);
        scheduleData = new HashMap<>();

        blurBackground = findViewById(R.id.blur_background);

        View primoButton = findViewById(R.id.menu);

        primoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectionDialog();
            }
        });

        View backButton = findViewById(R.id.arrow_back);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        View primoButton1 = findViewById(R.id.primo1);
        primoButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAddEventDialog();

            }
        });


        // Инициализируем текущую дату
        Calendar calendar = Calendar.getInstance();
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentMonth = calendar.get(Calendar.MONTH);
        currentYear = calendar.get(Calendar.YEAR);

        // Определяем TextView для отображения месяца
        TextView monthNameTextView = findViewById(R.id.month_name);

        // Инициализация календаря
        updateCalendar(monthNameTextView);

        // Настраиваем Spinner для выбора года
        Spinner yearSpinner = findViewById(R.id.year_spinner);
        List<String> years = new ArrayList<>();
        for (int i = 2024; i <= 2034; i++) {
            years.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(adapter);
        yearSpinner.setSelection(years.indexOf(String.valueOf(currentYear)));

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentYear = Integer.parseInt(parent.getItemAtPosition(position).toString());
                updateCalendar(monthNameTextView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ничего не делаем
            }
        });
        yearSpinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // Запускаем анимацию вращения стрелки

            }
            return false;
        });

// Слушатель для закрытия Spinner
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Анимация при закрытии

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Кнопки перехода по месяцам
        ImageButton leftButton = findViewById(R.id.left);
        ImageButton rightButton = findViewById(R.id.right);

        leftButton.setOnClickListener(v -> {
            animateCalendarTransition(-1);

            if (currentMonth < 0) {
                currentMonth = 11;
                currentYear--;
            }
            updateCalendar(monthNameTextView);
        });

        rightButton.setOnClickListener(v -> {
            animateCalendarTransition(1);

            if (currentMonth > 11) {
                currentMonth = 0;
                currentYear++;
            }
            updateCalendar(monthNameTextView);
        });

        // Обновление TextView для текущего дня недели и даты
        int todayDay = calendar.get(Calendar.DAY_OF_MONTH);
        updateCurrentDateInfo(todayDay);
    }

    private void animateCalendarTransition(int direction) {
        final TextView monthNameTextView = findViewById(R.id.month_name);
        final GridLayout calendarGrid = findViewById(R.id.calendar_grid);

        // Сохраните текущее значение месяца и года


        // Обновите месяц и год
        if (direction == -1) { // Влево
            currentMonth--;
            if (currentMonth < 0) {
                currentMonth = 11;
                currentYear--;
            }
        } else { // Вправо
            currentMonth++;
            if (currentMonth > 11) {
                currentMonth = 0;
                currentYear++;
            }
        }

        // Создайте новый календарь для нового месяца
        updateCalendar(monthNameTextView);

        // Установите анимацию
        calendarGrid.setTranslationX(direction == -1 ? -calendarGrid.getWidth() : calendarGrid.getWidth());
        calendarGrid.setAlpha(0f);

        calendarGrid.animate()
                .translationX(0f)
                .alpha(1f)
                .setDuration(500) // Увеличьте длительность анимации
                .setInterpolator(new DecelerateInterpolator()) // Используйте интерполятор
                .setListener(null);
    }


    private void updateCalendar(TextView monthNameTextView) {
        GridLayout calendarGrid = findViewById(R.id.calendar_grid);
        calendarGrid.removeAllViews();

        // Получаем общее количество дней в текущем месяце
        int daysToShow = daysInMonth[currentMonth];
        if (currentMonth == 1 && isLeapYear(currentYear)) {
            daysToShow = 29;
        }

        // Массив с днями недели
        String[] weekDays = {"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"};
        for (String day : weekDays) {
            TextView dayTextView = new TextView(this);
            dayTextView.setText(day);
            dayTextView.setTextColor(Color.BLACK);
            dayTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f);
            dayTextView.setLayoutParams(params);

            calendarGrid.addView(dayTextView);
        }

        // Определяем первый день месяца
        Calendar firstDayOfMonth = Calendar.getInstance();
        firstDayOfMonth.set(currentYear, currentMonth, 1);
        int firstDayOfWeek = firstDayOfMonth.get(Calendar.DAY_OF_WEEK);
        int emptyDays = (firstDayOfWeek + 5) % 7;

        int previousMonthDays = (currentMonth == 0) ? 31 : daysInMonth[currentMonth - 1];
        if (currentMonth == 1 && isLeapYear(currentYear)) {
            previousMonthDays = 29;
        }

        for (int i = 0; i < emptyDays; i++) {
            Button dayButton = new Button(this);
            dayButton.setText(String.valueOf(previousMonthDays - emptyDays + i + 1));
            dayButton.setEnabled(false);
            dayButton.setBackgroundColor(Color.LTGRAY);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f);
            dayButton.setLayoutParams(params);

            calendarGrid.addView(dayButton);
        }

        CalendarDatabase calendarDatabase = new CalendarDatabase(this);
        Calendar today = Calendar.getInstance();
        int todayDay = today.get(Calendar.DAY_OF_MONTH);
        int todayMonth = today.get(Calendar.MONTH);
        int todayYear = today.get(Calendar.YEAR);
        Drawable todayOutlineBackground = getResources().getDrawable(R.drawable.button_outline);
        Drawable defaultBackground = null;

        for (int i = 1; i <= daysToShow; i++) {
            ImageView checkIcon = findViewById(R.id.calendarExtension);
            if(daysToShow>29 && emptyDays>4){
                checkIcon.setVisibility(View.VISIBLE);
            } else {
                checkIcon.setVisibility(View.GONE); // Прячем галочку
            }

            ImageView p = findViewById(R.id.p);
            RelativeLayout dayLayout = new RelativeLayout(this);
            Button dayButton = new Button(this);
            dayButton.setText(String.valueOf(i));

            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.width = 0;
            param.height = GridLayout.LayoutParams.WRAP_CONTENT;
            param.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f);
            dayLayout.setLayoutParams(param);

            if (i == todayDay && currentMonth == todayMonth && currentYear == todayYear) {
                dayButton.setBackground(todayOutlineBackground);
            } else {
                dayButton.setBackground(defaultBackground);
            }

            final int day = i;
            dayButton.setOnClickListener(v -> {
                currentDay = day;
                updateCurrentDateInfo(day);
                if (day == todayDay && currentMonth == todayMonth && currentYear == todayYear) {
                    dayButton.setBackgroundResource(R.drawable.button_normal);
                } else {
                    if (lastClickedButton != null) {
                        lastClickedButton.setBackground(defaultBackground);
                    }
                    dayButton.setBackgroundResource(R.drawable.button_normal);
                }
                if (lastClickedButton != null && lastClickedButton != dayButton) {
                    if (lastClickedButton.getText().toString().equals(String.valueOf(todayDay)) &&
                            currentMonth == todayMonth && currentYear == todayYear) {
                        lastClickedButton.setBackground(todayOutlineBackground);
                    } else {
                        lastClickedButton.setBackground(defaultBackground);
                    }
                }
                lastClickedButton = dayButton;
            });

            // Проверяем, есть ли события на текущую дату
            List<CalendarEntry> eventsOnDate = calendarDatabase.getEntriesByDate(currentYear, currentMonth, day);
            if (!eventsOnDate.isEmpty()) {
                View eventIndicator = new View(this);
                eventIndicator.setBackgroundResource(R.drawable.point); // Используем point.xml для фона

                RelativeLayout.LayoutParams indicatorParams = new RelativeLayout.LayoutParams(20, 20);

                indicatorParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                indicatorParams.addRule(RelativeLayout.ALIGN_PARENT_END);

                indicatorParams.setMargins(0, 20, 20, 0); // Смещение по оси X и Y (в пикселях), например, -10 смещает индикатор немного влево и вверх

                eventIndicator.setLayoutParams(indicatorParams);


                // Добавляем индикатор после dayButton, чтобы он всегда был сверху
                dayLayout.addView(dayButton);
                dayLayout.addView(eventIndicator);
            } else {
                dayLayout.addView(dayButton);
            }

            calendarGrid.addView(dayLayout);
        }

        monthNameTextView.setText(monthNames[currentMonth] + " " + currentYear);
    }


    private void updateCurrentDateInfo(int day) {
        TextView informTextView = findViewById(R.id.informat);

        // Устанавливаем выбранный день
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(currentYear, currentMonth, day);

        // Получаем день недели
        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.getDefault()).format(selectedDate.getTime());
        dayOfWeek = dayOfWeek.substring(0, 1).toUpperCase() + dayOfWeek.substring(1).toLowerCase();

        // Форматируем дату
        String date = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(selectedDate.getTime());
        informTextView.setText(String.format("%s\n%s", dayOfWeek, date));

        // Инициализируем базу данных и получаем записи на выбранную дату
        CalendarDatabase calendarDatabase = new CalendarDatabase(getApplicationContext());
        List<CalendarEntry> entries = calendarDatabase.getAllEntries();

        // Очищаем контейнер событий перед добавлением новых
        LinearLayout eventContainer = findViewById(R.id.event_container);
        eventContainer.removeAllViews();

        boolean eventsFound = false;

        for (CalendarEntry entry : entries) {
            Calendar startDate = entry.getStartDateTime();
            Calendar endDate = entry.getEndDateTime();

            // Сравниваем даты (без учёта времени)
            if (startDate.get(Calendar.YEAR) == selectedDate.get(Calendar.YEAR) &&
                    startDate.get(Calendar.MONTH) == selectedDate.get(Calendar.MONTH) &&
                    startDate.get(Calendar.DAY_OF_MONTH) == selectedDate.get(Calendar.DAY_OF_MONTH)) {

                // Получаем время начала и конца события
                String startTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(startDate.getTime());
                String endTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(endDate.getTime());

                // Добавляем событие в ScrollView, передавая индекс записи
                addEventToScrollView(entry.getDescription(), startTime, endTime, entry.getIndex());

                eventsFound = true;
            }
        }

        // Если событий нет, отображаем Toast
        if (!eventsFound) {
            //Если событий нет что-то можно напиисать
        }
    }

    private void showSelectionDialog() {
        boolean isInGroppage = this instanceof MounthlyCalendar;
        Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        View rootView = dialog.findViewById(android.R.id.content);


        // Устанавливаем свое изображение как фон

        dialog.setCanceledOnTouchOutside(true); // Закрытие при нажатии вне окна

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(8, 25, 40, 32); // Отступы для LinearLayout
        layout.setBackgroundColor(Color.WHITE);
        layout.setBackgroundResource(R.drawable.round_right);

        TextView calenarText = new TextView(this);
        calenarText.setText("Календарь");
        // Устанавливаем отступ слева на 2 пикселя
        calenarText.setPadding(5, 16, 16, 16); // Изменено на 2 пикселя слева
        calenarText.setGravity(Gravity.CENTER);
        calenarText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        calenarText.setTextSize(30);
        Typeface customFont = ResourcesCompat.getFont(this, R.font.fonts_medium);
        calenarText.setTypeface(customFont);

        TextView weekText = new TextView(this);
        weekText.setText("Неделя");
        // Устанавливаем отступ слева на 2 пикселя
        weekText.setPadding(32, 16, 16, 16); // Изменено на 2 пикселя слева
        weekText.setBackgroundResource(R.drawable.rectangle_88);
        weekText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.week, 0, 0, 0);
        weekText.setCompoundDrawablePadding(20);
        weekText.setGravity(Gravity.CENTER_VERTICAL);
        weekText.setTextSize(16);
        Typeface customFont1 = ResourcesCompat.getFont(this, R.font.fonts_medium);
        weekText.setTypeface(customFont);

        LinearLayout.LayoutParams weekParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        weekParams.setMargins(0, 0, 0, 20); // Отступ внизу
        weekText.setLayoutParams(weekParams);

        TextView FetureText = new TextView(this);
        FetureText.setText("Предстоящие события");

        // Устанавливаем отступ слева на 2 пикселя
        FetureText.setPadding(32, 16, 16, 16);
        FetureText.setBackgroundResource(R.drawable.na180);
        FetureText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.forfture, 0, 0, 0);
        FetureText.setCompoundDrawablePadding(20);
        FetureText.setGravity(Gravity.CENTER_VERTICAL);
        FetureText.setTextSize(16);
        FetureText.setTypeface(customFont);

        LinearLayout.LayoutParams FetureParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        FetureParams.setMargins(0, 0, 0, 20); // Отступ внизу
        FetureText.setLayoutParams(FetureParams);

        TextView ukol = new TextView(this);
        ukol.setText("Неделя2");

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
        if (isInGroppage) {
            // Меняем фон кнопки
            monthText.setBackgroundResource(R.drawable.gold);
        } else {
            monthText.setBackgroundResource(R.drawable.rectangle_88);
        }
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
                Intent intent = new Intent(MounthlyCalendar.this, MounthlyCalendar.class);
                startActivity(intent);
            }
        });
        weekText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MounthlyCalendar.this, registr.class);
                startActivity(intent);
            }
        });

        ukol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MounthlyCalendar.this, registr2.class);
                startActivity(intent);
            }
        });
        FetureText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MounthlyCalendar.this, feture.class);
                startActivity(intent);
            }
        });

        layout.addView(calenarText);
        layout.addView(monthText);
        layout.addView(weekText);
        layout.addView(ukol);
        layout.addView(FetureText);


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
                    .translationX(layout.getWidth())
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
            params.gravity = Gravity.TOP | Gravity.END;
            params.y = 100;
            window.setAttributes(params);
        }
        dialog.setOnDismissListener(d -> {
            layout.animate()
                    .translationX(layout.getWidth())  // Анимация ухода вправо
                    .setDuration(1000)
                    .withEndAction(() -> {
                        // Снимаем размытие после завершения анимации
                        ImageView blurBackground = findViewById(R.id.blur_background);
                        blurBackground.setImageDrawable(null);
                        blurBackground.setVisibility(View.GONE);
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
                    // Вычисляем смещение и ограничиваем его, чтобы не двигаться влево от начального положения
                    float deltaX = event.getX() - startX[0];
                    float newTranslationX = Math.max(translationX[0] + deltaX, -layout.getWidth()); // Ограничение слева
                    layout.setTranslationX(newTranslationX); // Смещаем layout
                    return true;

                case MotionEvent.ACTION_UP:
                    // Закрываем или возвращаем окно в зависимости от положения
                    if (layout.getTranslationX() > layout.getWidth() / 6) { // Закрытие при смещении на треть ширины вправо
                        layout.animate().translationX(layout.getWidth()).setDuration(250).withEndAction(dialog::dismiss);
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

    private void showAddEventDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Добавить событие");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_event, null);
        builder.setView(dialogView);



        final EditText input = dialogView.findViewById(R.id.event_input);
        final Spinner dayPicker = dialogView.findViewById(R.id.day_picker);
        final Spinner monthPicker = dialogView.findViewById(R.id.month_picker);
        final Spinner yearPicker = dialogView.findViewById(R.id.year_picker);
        final Spinner startHourPicker = dialogView.findViewById(R.id.start_hour_picker);
        final Spinner startMinutePicker = dialogView.findViewById(R.id.start_minute_picker);
        final Spinner endHourPicker = dialogView.findViewById(R.id.end_hour_picker);
        final Spinner endMinutePicker = dialogView.findViewById(R.id.end_minute_picker);
        final Spinner recurrencePicker = dialogView.findViewById(R.id.recurrence_picker);
        final Switch reminderSwitch = dialogView.findViewById(R.id.reminder_switch);
        final Spinner eventTypePicker = dialogView.findViewById(R.id.event_type_picker);

        Integer[] eventIcons = {R.drawable.event_type_recording, R.drawable.event_type_pill, R.drawable.event_type_syringe, R.drawable.event_type_doctor, R.drawable.event_type_other};
        ArrayAdapter<Integer> eventTypeAdapter = new ArrayAdapter<Integer>(this, R.layout.spinner_icon_item, eventIcons) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_icon_item, parent, false);
                }
                ImageView iconView = (ImageView) convertView;
                iconView.setImageResource((Integer) getItem(position)); // Убедитесь, что это int
                return iconView;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_icon_item, parent, false);
                }
                ImageView iconView = (ImageView) convertView;
                iconView.setImageResource((Integer) getItem(position)); // Убедитесь, что это int
                return iconView;
            }
        };

        eventTypePicker.setAdapter(eventTypeAdapter);

        // Установка значений по умолчанию для года, месяца и дня
        List<String> years = generateNumberList(currentYear, 2033);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        yearPicker.setAdapter(yearAdapter);
        yearPicker.setSelection(0);

        List<String> months = generateNumberList(1, 12);
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        monthPicker.setAdapter(monthAdapter);
        monthPicker.setSelection(currentMonth);

        int daysInSelectedMonth = getDaysInMonth(currentYear, currentMonth + 1); // Учитываем текущий месяц
        List<String> days = generateNumberList(1, daysInSelectedMonth);
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        dayPicker.setAdapter(dayAdapter);
        dayPicker.setSelection(currentDay - 1); // Установка текущего дня

        // Адаптеры для времени
        ArrayAdapter<String> hourAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, generateNumberList(0, 23));
        startHourPicker.setAdapter(hourAdapter);
        endHourPicker.setAdapter(hourAdapter);

        ArrayAdapter<String> minuteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, generateNumberList(0, 59));
        startMinutePicker.setAdapter(minuteAdapter);
        endMinutePicker.setAdapter(minuteAdapter);

        ArrayAdapter<String> recurrenceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Arrays.asList("Не повторять", "Ежедневно", "Еженедельно", "Ежемесячно", "Ежегодно"));
        recurrencePicker.setAdapter(recurrenceAdapter);

        // Установка времени окончания по умолчанию
        startHourPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateEndTime(startHourPicker, startMinutePicker, endHourPicker, endMinutePicker);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        startMinutePicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateEndTime(startHourPicker, startMinutePicker, endHourPicker, endMinutePicker);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        builder.setPositiveButton("Сохранить", (dialog, which) -> {
            String eventName = input.getText().toString().trim();

            if (eventName.isEmpty()) {
                showCustomToast("Описание события не может быть пустым!");
                return;
            }

            int day = Integer.parseInt(dayPicker.getSelectedItem().toString());
            int month = Integer.parseInt(monthPicker.getSelectedItem().toString());
            int year = Integer.parseInt(yearPicker.getSelectedItem().toString());
            int startHour = Integer.parseInt(startHourPicker.getSelectedItem().toString());
            int startMinute = Integer.parseInt(startMinutePicker.getSelectedItem().toString());
            int endHour = Integer.parseInt(endHourPicker.getSelectedItem().toString());
            int endMinute = Integer.parseInt(endMinutePicker.getSelectedItem().toString());

            Calendar startDateTime = Calendar.getInstance();
            startDateTime.set(year, month - 1, day, startHour, startMinute, 0);

            Calendar endDateTime = Calendar.getInstance();
            endDateTime.set(year, month - 1, day, endHour, endMinute, 0);

            // Получаем индекс типа события и повторяемости
            int eventTypeIndex = eventTypePicker.getSelectedItemPosition(); // Индекс типа события
            int recurrenceIndex = recurrencePicker.getSelectedItemPosition(); // Индекс повторяемости

            int recurrenceCode = eventTypeIndex * 10 + recurrenceIndex;

            boolean isReminderSet = reminderSwitch.isChecked();

            // Генерируем уникальный индекс для группы записей
            int newIndex = generateUniqueIndex();

            CalendarDatabase calendarDatabase = new CalendarDatabase(this);

            // Добавляем записи в соответствии с повторяемостью
            Calendar currentStartDate = (Calendar) startDateTime.clone();
            Calendar currentEndDate = (Calendar) endDateTime.clone();

            // Устанавливаем шаг повторения
            int recurrenceStep = 0;
            switch (recurrenceIndex) {
                case 1: // Ежедневно
                    recurrenceStep = Calendar.DAY_OF_YEAR;
                    break;
                case 2: // Еженедельно
                    recurrenceStep = Calendar.WEEK_OF_YEAR;
                    break;
                case 3: // Ежемесячно
                    recurrenceStep = Calendar.MONTH;
                    break;
                case 4: // Ежегодно
                    recurrenceStep = Calendar.YEAR;
                    break;
                default: // Не повторять
                    break;
            }

            if (recurrenceStep > 0) {
                if (recurrenceIndex == 1){
                    for (int i = 0; i < 60; i++) { // Добавляем на год вперёд
                    CalendarEntry newEntry = new CalendarEntry(
                            newIndex, (Calendar) currentStartDate.clone(), (Calendar) currentEndDate.clone(),
                            recurrenceCode, recurrenceCode, false, isReminderSet, eventName
                    );

                    calendarDatabase.addEntry(newEntry);

                    if (isReminderSet) {
                        scheduleNotification(this, newEntry);
                    }

                    currentStartDate.add(recurrenceStep, 1);
                    currentEndDate.add(recurrenceStep, 1);

                    // Прекращаем добавление, если вышли за год
                    if (currentStartDate.get(Calendar.YEAR) > year + 1) break;
                    }
                }else {
                    for (int i = 0; i < 365; i++) { // Добавляем на год вперёд
                        CalendarEntry newEntry = new CalendarEntry(
                                newIndex, (Calendar) currentStartDate.clone(), (Calendar) currentEndDate.clone(),
                                recurrenceCode, recurrenceCode, false, isReminderSet, eventName
                        );

                        calendarDatabase.addEntry(newEntry);

                        if (isReminderSet) {
                            scheduleNotification(this, newEntry);
                        }

                        currentStartDate.add(recurrenceStep, 1);
                        currentEndDate.add(recurrenceStep, 1);

                        // Прекращаем добавление, если вышли за год
                        if (currentStartDate.get(Calendar.YEAR) > year + 1) break;
                    }
                }
            } else {
                // Добавляем одно событие, если нет повторяемости
                CalendarEntry newEntry = new CalendarEntry(
                        newIndex, startDateTime, endDateTime,
                        recurrenceCode, recurrenceCode, false, isReminderSet, eventName
                );

                calendarDatabase.addEntry(newEntry);

                if (isReminderSet) {
                    scheduleNotification(this, newEntry);
                }
            }

            updateCalendar(findViewById(R.id.month_name));
            updateCurrentDateInfo(day);
        });



        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    // Метод для обновления доступных дней в зависимости от выбранного месяца и года
    private void updateDayPicker(Spinner dayPicker, int year, int month, int currentDay) {
        Calendar today = Calendar.getInstance();
        int maxDays = today.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (year == today.get(Calendar.YEAR) && month == today.get(Calendar.MONTH) + 1) {
            dayPicker.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, generateNumberList(currentDay, maxDays)));
        } else {
            dayPicker.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, generateNumberList(1, maxDays)));
        }
    }

    private int getDaysInMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    // Метод для генерации уникального индекса (демонстрационный, следует заменить на реальную логику)
    private int generateUniqueIndex() {
        CalendarDatabase calendarDatabase = new CalendarDatabase(this);
        List<CalendarEntry> entries = calendarDatabase.getAllEntries();
        return entries.isEmpty() ? 1 : entries.get(entries.size() - 1).getIndex() + 1;
    }


    // Метод для генерации списка чисел для заполнения Spinner
    private List<String> generateNumberList(int start, int end) {
        List<String> numbers = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            numbers.add(String.format("%02d", i));
        }
        return numbers;
    }

    // Метод для отображения Toast с пользовательским макетом
    private void showCustomToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, null);

        TextView toastText = layout.findViewById(R.id.toast_text);
        toastText.setText(message);
        toastText.setTextColor(getResources().getColor(R.color.Error)); // Настройте цвет текста

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }


    private void addEventToScrollView(String event, String startTime, String endTime, int index) {
        // Получаем запись из базы данных по индексу
        CalendarDatabase calendarDatabase = new CalendarDatabase(this);
        CalendarEntry entry = calendarDatabase.getEntry(index);
        int type = entry.getRecurrenceCode();
        type = type / 10;
        if (type == 0) {
            LinearLayout eventContainer = findViewById(R.id.event_container);
            LayoutInflater inflater = LayoutInflater.from(this);

            View eventView = inflater.inflate(R.layout.event_type_recording, eventContainer, false);

            // Находим элементы макета
            ImageView iconEventType = eventView.findViewById(R.id.icon_event_type);
            TextView textTime = eventView.findViewById(R.id.text_time);
            TextView textEventDescription = eventView.findViewById(R.id.text_event_description);
            RelativeLayout rectangleWithCheck = eventView.findViewById(R.id.rectangle_with_check);

            // Устанавливаем данные события
            textTime.setText(String.format("%s - %s", startTime, endTime));
            textEventDescription.setText(event);

            // Устанавливаем галочку для выполненных событий
            ImageView checkIcon = eventView.findViewById(R.id.check_icon);

            if (entry.isCompleted()) {
                checkIcon.setVisibility(View.VISIBLE); // Показываем галочку
            } else {
                checkIcon.setVisibility(View.GONE); // Прячем галочку
            }

            // Устанавливаем обработчик нажатий для события
            eventView.setTag(index); // Сохраняем индекс записи в качестве метки
            eventView.setOnClickListener(v -> showEventDialog(index));

            // Добавляем макет в контейнер
            eventContainer.addView(eventView, 0);
        } else if (type == 1) {
            LinearLayout eventContainer = findViewById(R.id.event_container);
            LayoutInflater inflater = LayoutInflater.from(this);

            View eventView = inflater.inflate(R.layout.event_type_pill, eventContainer, false);

            // Находим элементы макета
            ImageView iconEventType = eventView.findViewById(R.id.icon_event_type);
            TextView textTime = eventView.findViewById(R.id.text_time);
            TextView textEventDescription = eventView.findViewById(R.id.text_event_description);
            RelativeLayout rectangleWithCheck = eventView.findViewById(R.id.rectangle_with_check);

            // Устанавливаем данные события
            textTime.setText(String.format("%s - %s", startTime, endTime));
            textEventDescription.setText(event);

            // Устанавливаем галочку для выполненных событий
            ImageView checkIcon = eventView.findViewById(R.id.check_icon);

            if (entry.isCompleted()) {
                checkIcon.setVisibility(View.VISIBLE); // Показываем галочку
            } else {
                checkIcon.setVisibility(View.GONE); // Прячем галочку
            }

            // Устанавливаем обработчик нажатий для события
            eventView.setTag(index); // Сохраняем индекс записи в качестве метки
            eventView.setOnClickListener(v -> showEventDialog(index));

            // Добавляем макет в контейнер
            eventContainer.addView(eventView, 0);
        } else if (type == 2) {
            LinearLayout eventContainer = findViewById(R.id.event_container);
            LayoutInflater inflater = LayoutInflater.from(this);

            View eventView = inflater.inflate(R.layout.event_type_syringe, eventContainer, false);

            // Находим элементы макета
            ImageView iconEventType = eventView.findViewById(R.id.icon_event_type);
            TextView textTime = eventView.findViewById(R.id.text_time);
            TextView textEventDescription = eventView.findViewById(R.id.text_event_description);
            RelativeLayout rectangleWithCheck = eventView.findViewById(R.id.rectangle_with_check);

            // Устанавливаем данные события
            textTime.setText(String.format("%s - %s", startTime, endTime));
            textEventDescription.setText(event);

            // Устанавливаем галочку для выполненных событий
            ImageView checkIcon = eventView.findViewById(R.id.check_icon);

            if (entry.isCompleted()) {
                checkIcon.setVisibility(View.VISIBLE); // Показываем галочку
            } else {
                checkIcon.setVisibility(View.GONE); // Прячем галочку
            }

            // Устанавливаем обработчик нажатий для события
            eventView.setTag(index); // Сохраняем индекс записи в качестве метки
            eventView.setOnClickListener(v -> showEventDialog(index));

            // Добавляем макет в контейнер
            eventContainer.addView(eventView, 0);
        } else if (type == 3) {
            LinearLayout eventContainer = findViewById(R.id.event_container);
            LayoutInflater inflater = LayoutInflater.from(this);

            View eventView = inflater.inflate(R.layout.event_type_doc, eventContainer, false);

            // Находим элементы макета
            ImageView iconEventType = eventView.findViewById(R.id.icon_event_type);
            TextView textTime = eventView.findViewById(R.id.text_time);
            TextView textEventDescription = eventView.findViewById(R.id.text_event_description);
            RelativeLayout rectangleWithCheck = eventView.findViewById(R.id.rectangle_with_check);

            // Устанавливаем данные события
            textTime.setText(String.format("%s - %s", startTime, endTime));
            textEventDescription.setText(event);

            // Устанавливаем галочку для выполненных событий
            ImageView checkIcon = eventView.findViewById(R.id.check_icon);

            if (entry.isCompleted()) {
                checkIcon.setVisibility(View.VISIBLE); // Показываем галочку
            } else {
                checkIcon.setVisibility(View.GONE); // Прячем галочку
            }

            // Устанавливаем обработчик нажатий для события
            eventView.setTag(index); // Сохраняем индекс записи в качестве метки
            eventView.setOnClickListener(v -> showEventDialog(index));

            // Добавляем макет в контейнер
            eventContainer.addView(eventView, 0);
        } else if (type == 10) {
            LinearLayout eventContainer = findViewById(R.id.event_container);
            LayoutInflater inflater = LayoutInflater.from(this);

            View eventView = inflater.inflate(R.layout.event_type_medical, eventContainer, false);

            // Находим элементы макета
            ImageView iconEventType = eventView.findViewById(R.id.icon_event_type);
            TextView textTime = eventView.findViewById(R.id.text_time);
            TextView textEventDescription = eventView.findViewById(R.id.text_event_description);

            // Устанавливаем данные события
            textTime.setText(String.format("Медицинская запись\n%s - %s", startTime, endTime));
            textEventDescription.setText(event);

            // Устанавливаем обработчик нажатий для события
            eventView.setTag(index); // Сохраняем индекс записи в качестве метки
            eventView.setOnClickListener(v -> showEventDialogForMedic(index));

            // Добавляем макет в контейнер
            eventContainer.addView(eventView, 0);
        } else {
            LinearLayout eventContainer = findViewById(R.id.event_container);
            LayoutInflater inflater = LayoutInflater.from(this);

            View eventView = inflater.inflate(R.layout.event_type_other, eventContainer, false);

            // Находим элементы макета
            ImageView iconEventType = eventView.findViewById(R.id.icon_event_type);
            TextView textTime = eventView.findViewById(R.id.text_time);
            TextView textEventDescription = eventView.findViewById(R.id.text_event_description);
            RelativeLayout rectangleWithCheck = eventView.findViewById(R.id.rectangle_with_check);

            // Устанавливаем данные события
            textTime.setText(String.format("%s - %s", startTime, endTime));
            textEventDescription.setText(event);

            // Устанавливаем галочку для выполненных событий
            ImageView checkIcon = eventView.findViewById(R.id.check_icon);

            if (entry.isCompleted()) {
                checkIcon.setVisibility(View.VISIBLE); // Показываем галочку
            } else {
                checkIcon.setVisibility(View.GONE); // Прячем галочку
            }

            // Устанавливаем обработчик нажатий для события
            eventView.setTag(index); // Сохраняем индекс записи в качестве метки
            eventView.setOnClickListener(v -> showEventDialog(index));

            // Добавляем макет в контейнер
            eventContainer.addView(eventView, 0);
        }
    }


    private void showEventDialogForMedic(int index) {
        CalendarDatabase calendarDatabase = new CalendarDatabase(this);
        CalendarEntry entry = calendarDatabase.getEntry(index);

        if (entry == null) {
            Toast.makeText(this, "Событие не найдено", Toast.LENGTH_SHORT).show();
            return;
        }

        // Создаем диалог для отображения информации о событии
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Медицинская запись");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_event_info, null);
        builder.setView(dialogView);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        String startDateTimeFormatted = dateFormat.format(entry.getStartDateTime().getTime());
        String endDateTimeFormatted = dateFormat.format(entry.getEndDateTime().getTime());
        TextView eventInfoTextView = dialogView.findViewById(R.id.event_info_text);
        eventInfoTextView.setText(String.format("%s\n\nДата/Время: %s - %s",
                entry.getReminderText(),
                startDateTimeFormatted,
                entry.getEndTime()));


        builder.setNeutralButton("Закрыть", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    private void showEventDialog(int index) {
        CalendarDatabase calendarDatabase = new CalendarDatabase(this);
        CalendarEntry entry = calendarDatabase.getEntry(index);

        if (entry == null) {
            Toast.makeText(this, "Событие не найдено", Toast.LENGTH_SHORT).show();
            return;
        }

        // Создаем диалог для отображения информации о событии
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Информация о событии");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_event_info, null);
        builder.setView(dialogView);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        String startDateTimeFormatted = dateFormat.format(entry.getStartDateTime().getTime());
        String endDateTimeFormatted = dateFormat.format(entry.getEndDateTime().getTime());
        TextView eventInfoTextView = dialogView.findViewById(R.id.event_info_text);
        eventInfoTextView.setText(String.format("%s\n\nДата/Время: %s - %s\nПовторяемость: %s\nНапоминание: %s\nВыполнено: %s",
                entry.getReminderText(),
                startDateTimeFormatted,
                entry.getEndTime(),
                entry.getRecurrenceDescription(entry.getRecurrenceCode() % 10),
                entry.isReminderSet() ? "Да" : "Нет",
                entry.isCompleted() ? "Да" : "Нет"));


        // Устанавливаем кнопки для диалога
        builder.setPositiveButton("Редактировать", (dialog, which) -> showEditEventDialog(index));
        builder.setNegativeButton("Закрыть", (dialog, which) -> dialog.dismiss());
        builder.setNeutralButton("Отметить выполненным", (dialog, which) -> {
            Toast.makeText(this, "Событие отмечено как выполненное", Toast.LENGTH_SHORT).show();
        });
        builder.setNeutralButton("Отметить выполненным", (dialog, which) -> {
            if (entry == null) {
                Toast.makeText(this, "Событие не найдено", Toast.LENGTH_SHORT).show();
                return;
            }

            // Создаем обновленную запись с флагом isCompleted, установленным в true
            CalendarEntry updatedEntry = new CalendarEntry(
                    entry.getIndex(),
                    entry.getStartDateTime(),
                    entry.getEndDateTime(),
                    entry.getEntryCode(),
                    entry.getRecurrenceCode(),
                    true,  // Меняем значение isCompleted на true
                    entry.isReminderSet(),
                    entry.getReminderText()
            );

            // Обновляем запись в базе данных
            calendarDatabase.deleteEntry(entry.getIndex()); // Удаление старой записи
            calendarDatabase.addEntry(updatedEntry); // Добавление обновленной записи

            // Отображаем уведомление
            Toast.makeText(this, "Событие отмечено как выполненное", Toast.LENGTH_SHORT).show();

            // Обновляем интерфейс, если это необходимо
            updateCalendar(findViewById(R.id.month_name));
            updateCurrentDateInfo(entry.getStartDateTime().get(Calendar.DAY_OF_MONTH));
        });

        builder.show();
    }


    private void showEditEventDialog(int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Редактировать событие");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_event, null);
        builder.setView(dialogView);

        final EditText input = dialogView.findViewById(R.id.event_input);
        final Spinner dayPicker = dialogView.findViewById(R.id.day_picker);
        final Spinner monthPicker = dialogView.findViewById(R.id.month_picker);
        final Spinner yearPicker = dialogView.findViewById(R.id.year_picker);
        final Spinner startHourPicker = dialogView.findViewById(R.id.start_hour_picker);
        final Spinner startMinutePicker = dialogView.findViewById(R.id.start_minute_picker);
        final Spinner endHourPicker = dialogView.findViewById(R.id.end_hour_picker);
        final Spinner endMinutePicker = dialogView.findViewById(R.id.end_minute_picker);
        final Spinner recurrencePicker = dialogView.findViewById(R.id.recurrence_picker);
        final Switch reminderSwitch = dialogView.findViewById(R.id.reminder_switch);
        final Spinner eventTypePicker = dialogView.findViewById(R.id.event_type_picker);

        CalendarDatabase calendarDatabase = new CalendarDatabase(this);
        CalendarEntry entry = calendarDatabase.getEntry(index);

        if (entry != null) {
            // Установка значений из записи
            input.setText(entry.getReminderText());

            Calendar startDate = entry.getStartDateTime();
            Calendar endDate = entry.getEndDateTime();

            int startDay = startDate.get(Calendar.DAY_OF_MONTH);
            int startMonth = startDate.get(Calendar.MONTH) + 1; // Месяцы начинаются с 0
            int startYear = startDate.get(Calendar.YEAR);

            // Установка адаптеров и значений для даты
            ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, generateNumberList(startYear, 2033));
            yearPicker.setAdapter(yearAdapter);
            yearPicker.setSelection(0);

            ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, generateNumberList(1, 12));
            monthPicker.setAdapter(monthAdapter);
            monthPicker.setSelection(startMonth - 1);

            updateDayPicker(dayPicker, startYear, startMonth, startDay);

            // Установка слушателей для обновления дней при изменении года или месяца
            yearPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int selectedYear = Integer.parseInt(yearPicker.getSelectedItem().toString());
                    int selectedMonth = Integer.parseInt(monthPicker.getSelectedItem().toString());
                    updateDayPicker(dayPicker, selectedYear, selectedMonth, startDay);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            monthPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int selectedYear = Integer.parseInt(yearPicker.getSelectedItem().toString());
                    int selectedMonth = Integer.parseInt(monthPicker.getSelectedItem().toString());
                    updateDayPicker(dayPicker, selectedYear, selectedMonth, startDay);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            // Установка значений для времени
            ArrayAdapter<String> hourAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, generateNumberList(0, 23));
            startHourPicker.setAdapter(hourAdapter);
            endHourPicker.setAdapter(hourAdapter);
            startHourPicker.setSelection(startDate.get(Calendar.HOUR_OF_DAY));
            endHourPicker.setSelection(endDate.get(Calendar.HOUR_OF_DAY));

            ArrayAdapter<String> minuteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, generateNumberList(0, 59));
            startMinutePicker.setAdapter(minuteAdapter);
            endMinutePicker.setAdapter(minuteAdapter);
            startMinutePicker.setSelection(startDate.get(Calendar.MINUTE));
            endMinutePicker.setSelection(endDate.get(Calendar.MINUTE));

            // Установка значений для типа события и повторяемости
            Integer[] eventIcons = {R.drawable.event_type_recording, R.drawable.event_type_pill, R.drawable.event_type_syringe, R.drawable.event_type_doctor, R.drawable.event_type_other};
            ArrayAdapter<Integer> eventTypeAdapter = new ArrayAdapter<Integer>(this, R.layout.spinner_icon_item, eventIcons) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_icon_item, parent, false);
                    }
                    ImageView iconView = (ImageView) convertView;
                    iconView.setImageResource((Integer) getItem(position));
                    return iconView;
                }

                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_icon_item, parent, false);
                    }
                    ImageView iconView = (ImageView) convertView;
                    iconView.setImageResource((Integer) getItem(position));
                    return iconView;
                }
            };

            eventTypePicker.setAdapter(eventTypeAdapter);
            eventTypePicker.setSelection(entry.getRecurrenceCode() / 10); // Индекс типа события

            recurrencePicker.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Arrays.asList("Не повторять", "Ежедневно", "Еженедельно", "Ежемесячно", "Ежегодно")));
            recurrencePicker.setSelection(entry.getRecurrenceCode() % 10); // Индекс повторяемости

            reminderSwitch.setChecked(entry.isReminderSet());
        }

        builder.setPositiveButton("Сохранить", (dialog, which) -> {
            String eventName = input.getText().toString().trim();

            if (eventName.isEmpty()) {
                showCustomToast("Описание события не может быть пустым!");
                return;
            }

            int day = Integer.parseInt(dayPicker.getSelectedItem().toString());
            int month = Integer.parseInt(monthPicker.getSelectedItem().toString());
            int year = Integer.parseInt(yearPicker.getSelectedItem().toString());
            int startHour = Integer.parseInt(startHourPicker.getSelectedItem().toString());
            int startMinute = Integer.parseInt(startMinutePicker.getSelectedItem().toString());
            int endHour = Integer.parseInt(endHourPicker.getSelectedItem().toString());
            int endMinute = Integer.parseInt(endMinutePicker.getSelectedItem().toString());

            Calendar startDateTime = Calendar.getInstance();
            startDateTime.set(year, month - 1, day, startHour, startMinute, 0);

            Calendar endDateTime = Calendar.getInstance();
            endDateTime.set(year, month - 1, day, endHour, endMinute, 0);

            // Получаем индекс типа события и повторяемости
            int eventTypeIndex = eventTypePicker.getSelectedItemPosition(); // Индекс типа события
            int recurrenceIndex = recurrencePicker.getSelectedItemPosition(); // Индекс повторяемости

            int recurrenceCode = eventTypeIndex * 10 + recurrenceIndex;

            boolean isReminderSet = reminderSwitch.isChecked();

            // Генерируем уникальный индекс для группы записей
            int newIndex = generateUniqueIndex();

            // Добавляем записи в соответствии с повторяемостью
            Calendar currentStartDate = (Calendar) startDateTime.clone();
            Calendar currentEndDate = (Calendar) endDateTime.clone();

            // Устанавливаем шаг повторения
            int recurrenceStep = 0;
            switch (recurrenceIndex) {
                case 1: // Ежедневно
                    recurrenceStep = Calendar.DAY_OF_YEAR;
                    break;
                case 2: // Еженедельно
                    recurrenceStep = Calendar.WEEK_OF_YEAR;
                    break;
                case 3: // Ежемесячно
                    recurrenceStep = Calendar.MONTH;
                    break;
                case 4: // Ежегодно
                    recurrenceStep = Calendar.YEAR;
                    break;
                default: // Не повторять
                    break;
            }

            if (recurrenceStep > 0) {
                if (recurrenceIndex == 1){
                    for (int i = 0; i < 60; i++) { // Добавляем на год вперёд
                        CalendarEntry newEntry = new CalendarEntry(
                                newIndex, (Calendar) currentStartDate.clone(), (Calendar) currentEndDate.clone(),
                                recurrenceCode, recurrenceCode, false, isReminderSet, eventName
                        );

                        calendarDatabase.deleteEntry(index);
                        calendarDatabase.addEntry(newEntry);

                        if (isReminderSet) {
                            scheduleNotification(this, newEntry);
                        }

                        currentStartDate.add(recurrenceStep, 1);
                        currentEndDate.add(recurrenceStep, 1);

                        // Прекращаем добавление, если вышли за год
                        if (currentStartDate.get(Calendar.YEAR) > year + 1) break;
                    }
                }else {
                    for (int i = 0; i < 365; i++) { // Добавляем на год вперёд
                        CalendarEntry newEntry = new CalendarEntry(
                                newIndex, (Calendar) currentStartDate.clone(), (Calendar) currentEndDate.clone(),
                                recurrenceCode, recurrenceCode, false, isReminderSet, eventName
                        );

                        calendarDatabase.deleteEntry(index); // Удаление старой записи
                        calendarDatabase.addEntry(newEntry); // Сохранение обновленной записи

                        if (isReminderSet) {
                            scheduleNotification(this, newEntry);
                        }

                        currentStartDate.add(recurrenceStep, 1);
                        currentEndDate.add(recurrenceStep, 1);

                        // Прекращаем добавление, если вышли за год
                        if (currentStartDate.get(Calendar.YEAR) > year + 1) break;
                    }
                }
            } else {
                // Добавляем одно событие, если нет повторяемости
                CalendarEntry newEntry = new CalendarEntry(
                        newIndex, startDateTime, endDateTime,
                        recurrenceCode, recurrenceCode, false, isReminderSet, eventName
                );

                calendarDatabase.deleteEntry(index); // Удаление старой записи
                calendarDatabase.addEntry(newEntry); // Сохранение обновленной записи

                if (isReminderSet) {
                    scheduleNotification(this, newEntry);
                }
            }

            updateCalendar(findViewById(R.id.month_name));
            updateCurrentDateInfo(day);
        });


        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss());

        builder.setNeutralButton("Удалить", (dialog, which) -> {
            int day = Integer.parseInt(dayPicker.getSelectedItem().toString());
            calendarDatabase.deleteEntry(index);
            updateCalendar(findViewById(R.id.month_name));
            dialog.dismiss();
            updateCurrentDateInfo(day);
        });

        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss());
        builder.show();
    }


    // Метод для обновления времени окончания события
    private void updateEndTime(Spinner startHourPicker, Spinner startMinutePicker, Spinner endHourPicker, Spinner endMinutePicker) {
        int startHour = Integer.parseInt(startHourPicker.getSelectedItem().toString());
        int startMinute = Integer.parseInt(startMinutePicker.getSelectedItem().toString());

        int endHour = startHour;
        int endMinute = startMinute + 15;

        if (endMinute >= 60) {
            endMinute -= 60;
            endHour++;
        }

        // Устанавливаем время окончания в спиннеры
        endHourPicker.setSelection(endHour);
        endMinutePicker.setSelection(endMinute);

        // Слушатели для предотвращения установки времени окончания меньше начала
        endHourPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                validateEndTime(startHourPicker, startMinutePicker, endHourPicker, endMinutePicker);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        endMinutePicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                validateEndTime(startHourPicker, startMinutePicker, endHourPicker, endMinutePicker);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    // Метод для проверки времени окончания
    private void validateEndTime(Spinner startHourPicker, Spinner startMinutePicker, Spinner endHourPicker, Spinner endMinutePicker) {
        int startHour = Integer.parseInt(startHourPicker.getSelectedItem().toString());
        int startMinute = Integer.parseInt(startMinutePicker.getSelectedItem().toString());
        int endHour = Integer.parseInt(endHourPicker.getSelectedItem().toString());
        int endMinute = Integer.parseInt(endMinutePicker.getSelectedItem().toString());

        if (endHour < startHour || (endHour == startHour && endMinute < startMinute + 15)) {
            updateEndTime(startHourPicker, startMinutePicker, endHourPicker, endMinutePicker);
        }
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

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public void scheduleNotification(Context context, @NonNull CalendarEntry entry) {
        if (!entry.isReminderSet()) return; // Если напоминание не установлено

        Calendar startDateTime = entry.getStartDateTime();
        Calendar oneHourBefore = (Calendar) startDateTime.clone();
        oneHourBefore.add(Calendar.HOUR_OF_DAY, -1); // Уведомление за час до начала события

        // Создаем интент для уведомления за час до начала
        Intent reminderIntent = new Intent(context, NotificationReceiver.class);
        reminderIntent.putExtra("reminderText", "Событие \"" + entry.getReminderText() + "\" начинается в " +
                formatTime(entry.getStartDateTime()) + ", не пропустите!");
        reminderIntent.putExtra("title", "Событие скоро начнётся");

        PendingIntent reminderPendingIntent = PendingIntent.getBroadcast(
                context,
                entry.getIndex() + 1, // Уникальный идентификатор для этого уведомления
                reminderIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    oneHourBefore.getTimeInMillis(),
                    reminderPendingIntent
            );
        }

        // Создаем интент для уведомления о начале события
        Intent startIntent = new Intent(context, NotificationReceiver.class);
        startIntent.putExtra("reminderText", "Событие \"" + entry.getReminderText() + "\" началось!");
        startIntent.putExtra("title", "Событие началось");

        PendingIntent startPendingIntent = PendingIntent.getBroadcast(
                context,
                entry.getIndex(), // Уникальный идентификатор для этого уведомления
                startIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Уведомление о начале события
        if (alarmManager != null) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    entry.getStartDateTime().getTimeInMillis(),
                    startPendingIntent
            );
        }
    }

    // Метод для форматирования времени
    private String formatTime(Calendar calendar) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return String.format("%02d:%02d", hour, minute);
    }

    public void cancelNotification(Context context, @NonNull CalendarEntry entry) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Отмена уведомления за 1 час до события
        Intent reminderIntent = new Intent(context, NotificationReceiver.class);
        reminderIntent.putExtra("reminderText", "Событие \"" + entry.getReminderText() + "\" начинается в " +
                formatTime(entry.getStartDateTime()) + ", не пропустите!");
        reminderIntent.putExtra("title", "Событие скоро начнётся");

        PendingIntent reminderPendingIntent = PendingIntent.getBroadcast(
                context,
                entry.getIndex() + 1, // Уникальный идентификатор для этого уведомления
                reminderIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        if (alarmManager != null) {
            alarmManager.cancel(reminderPendingIntent); // Отменяем уведомление за час до события
        }

        // Отмена уведомления о начале события
        Intent startIntent = new Intent(context, NotificationReceiver.class);
        startIntent.putExtra("reminderText", "Событие \"" + entry.getReminderText() + "\" началось!");
        startIntent.putExtra("title", "Событие началось");

        PendingIntent startPendingIntent = PendingIntent.getBroadcast(
                context,
                entry.getIndex(), // Уникальный идентификатор для этого уведомления
                startIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        if (alarmManager != null) {
            alarmManager.cancel(startPendingIntent); // Отменяем уведомление о начале события
        }
    }

}

