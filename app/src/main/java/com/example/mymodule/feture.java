package com.example.mymodule;

import android.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ScrollView;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import java.util.Calendar;

public class feture extends Activity {
    private GestureDetector gestureDetector;
    private int currentWeekOffset = 0;
    private int currentMonth;
    private int currentYear;
    private int currentDay;
    private final String[] monthNames = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
    private ImageView[] daysOfWeekImages;
    private TextView[] daysOfWeekTexts;
    private CalendarDatabase calendarDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feture);

        // Инициализация базы данных
        calendarDatabase = new CalendarDatabase(this);

        // Инициализация ScrollView и LinearLayout внутри него
        ScrollView scrollView = findViewById(R.id.mili_scroll);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);

        // Создание LayoutInflater для добавления элементов
        LayoutInflater inflater = LayoutInflater.from(this);

        // Получение текущей даты
        Calendar today = Calendar.getInstance();
        Calendar tomorrow = (Calendar) today.clone();
        tomorrow.add(Calendar.DAY_OF_YEAR, 1);
        Calendar oneWeekLater = (Calendar) today.clone();
        oneWeekLater.add(Calendar.DAY_OF_YEAR, 7);

        // Загрузка записей из базы данных начиная с сегодняшней даты
        List<CalendarEntry> events = calendarDatabase.getAllEventsFromToday(today);

        // Разделение событий на группы
        List<CalendarEntry> todayEvents = new ArrayList<>();
        List<CalendarEntry> tomorrowEvents = new ArrayList<>();
        List<CalendarEntry> weekEvents = new ArrayList<>();
        List<CalendarEntry> laterEvents = new ArrayList<>();

        for (CalendarEntry event : events) {
            if (isSameDay(today, event.getStartDateTime())) {
                todayEvents.add(event);
            } else if (isSameDay(tomorrow, event.getStartDateTime())) {
                tomorrowEvents.add(event);
            } else if (event.getStartDateTime().before(oneWeekLater)) {
                weekEvents.add(event);
            } else {
                laterEvents.add(event);
            }
        }

        // Добавление разделителя и событий "Сегодня:"
        if (!todayEvents.isEmpty()) {
            addSeparator(linearLayout, inflater, "Сегодня:");
            addEventsToLayout(todayEvents, linearLayout, inflater);
        }

        // Добавление разделителя и событий "Завтра:"
        if (!tomorrowEvents.isEmpty()) {
            addSeparator(linearLayout, inflater, "Завтра:");
            addEventsToLayout(tomorrowEvents, linearLayout, inflater);
        }

        // Добавление разделителя и событий "Дальше на этой неделе:"
        if (!weekEvents.isEmpty()) {
            addSeparator(linearLayout, inflater, "Дальше на этой неделе:");
            addEventsToLayout(weekEvents, linearLayout, inflater);
        }

        // Добавление разделителя и событий "Позже:"
        if (!laterEvents.isEmpty()) {
            addSeparator(linearLayout, inflater, "Позже:");
            addEventsToLayout(laterEvents, linearLayout, inflater);
        }
        // Остальной код класса
        View primoButton = findViewById(R.id.menu);
        primoButton.setOnClickListener(v -> onBackPressed());
    }

    // Метод для добавления строки-разделителя
    private void addSeparator(LinearLayout layout, LayoutInflater inflater, String text) {
        TextView separatorView = (TextView) inflater.inflate(R.layout.separator_text, layout, false);
        separatorView.setText(text);
        layout.addView(separatorView);
    }

    // Метод для добавления событий в макет
    private void addEventsToLayout(List<CalendarEntry> events, LinearLayout layout, LayoutInflater inflater) {
        for (CalendarEntry event : events) {
            View eventView = inflater.inflate(R.layout.feture_element, layout, false);

            // Установка данных для элемента
            TextView dateTimeText = eventView.findViewById(R.id.text_date_time);
            String startDateTime = formatCalendar(event.getStartDateTime());
            dateTimeText.setText(startDateTime);

            TextView descriptionText = eventView.findViewById(R.id.text_event_description);
            descriptionText.setText(event.getDescription());

            int index = event.getIndex();
            Button markDoneButton = eventView.findViewById(R.id.button_mark_done);
            markDoneButton.setOnClickListener(v -> {
                // Обработка нажатий на кнопку
                Toast.makeText(this, "Событие выполнено: " + event.getDescription(), Toast.LENGTH_SHORT).show();
                showEventDialog(index);
            });

            // Установка иконки в зависимости от recurrenceCode
            ImageView eventIcon = eventView.findViewById(R.id.icon_event_type);
            switch (event.getRecurrenceCode()/10) {
                case 0:
                    eventIcon.setImageResource(R.drawable.ic_event_recording);
                    break;
                case 1:
                    eventIcon.setImageResource(R.drawable.ic_event_pill);
                    break;
                case 2:
                    eventIcon.setImageResource(R.drawable.ic_event_syringe);
                    break;
                case 3:
                    eventIcon.setImageResource(R.drawable.ic_event_doctor);
                    break;
                case 4:
                    eventIcon.setImageResource(R.drawable.ic_event_star);
                    break;
                case 10:
                    eventIcon.setImageResource(R.drawable.ic_event_medical);
                    break;
                default:
                    eventIcon.setImageResource(R.drawable.ic_event_recording); // Иконка по умолчанию
                    break;
            }

            // Добавление элемента в макет
            layout.addView(eventView);
        }
    }

    // Метод для проверки, относятся ли два календаря к одному дню
    private boolean isSameDay(Calendar c1, Calendar c2) {
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
                c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
    }

    // Форматирование объекта Calendar в строку
    private String formatCalendar(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd.MM.yyyy - HH:mm", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

//Сюда
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

    // Создание и установка кастомного фона
    AlertDialog dialog = builder.create();
    dialog.getWindow().setBackgroundDrawableResource(R.drawable.round2);

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    String startDateTimeFormatted = dateFormat.format(entry.getStartDateTime().getTime());
    String startTimeFormatted = timeFormat.format(entry.getStartDateTime().getTime());
    String endDateTimeFormatted = dateFormat.format(entry.getEndDateTime().getTime());
    TextView eventInfoTextView = dialogView.findViewById(R.id.event_info_text);
    eventInfoTextView.setText(String.format("%s\n\nДата: %s\nВремя: %s - %s\nПовторяемость: %s\nНапоминание: %s\nВыполнено: %s",
            entry.getReminderText(),
            startDateTimeFormatted,
            startTimeFormatted,
            entry.getEndTime(),
            entry.getRecurrenceDescription(entry.getRecurrenceCode() % 10),
            entry.isReminderSet() ? "Да" : "Нет",
            entry.isCompleted() ? "Да" : "Нет"));

    // Устанавливаем кнопки для диалога
    builder.setNegativeButton("Закрыть", (dialog1, which) -> dialog1.dismiss());
    builder.setNeutralButton("Отметить выполненным", (dialog1, which) -> {
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
    });


    dialog.show();
}




    private void showSelectionDialog() {

        boolean isInGroppage = this instanceof feture;
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
        if (isInGroppage) {
            // Меняем фон кнопки
            FetureText.setBackgroundResource(R.drawable.goldna200);
        }else {
            FetureText.setBackgroundResource(R.drawable.na180);
        }
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

        monthText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.month, 0, 0, 0);
        monthText.setCompoundDrawablePadding(20);
        monthText.setGravity(Gravity.CENTER_VERTICAL);
        monthText.setBackgroundResource(R.drawable.rectangle_88);
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
                Intent intent = new Intent(feture.this, MounthlyCalendar.class);
                startActivity(intent);
            }
        });
        weekText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(feture.this, registr.class);
                startActivity(intent);
            }
        });

        ukol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(feture.this, registr2.class);
                startActivity(intent);
            }
        });
        FetureText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(feture.this, feture.class);
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
}
