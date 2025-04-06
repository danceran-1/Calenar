package com.example.mymodule;

import android.app.Activity;  // или импортируйте AppCompatActivity, если используете библиотеку поддержки
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

public class registr2 extends Activity {
    private GestureDetector gestureDetector;
    private int currentWeekOffset = 0;
    private int currentMonth;
    private int currentYear;
    private int currentDay;
    private final String[] monthNames = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
    private ImageView[] daysOfWeekImages;
    private TextView[] daysOfWeekTexts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registr2);

        daysOfWeekImages = new ImageView[]{
                findViewById(R.id.kub),
                findViewById(R.id.kub1),
                findViewById(R.id.kub2),
                findViewById(R.id.kub3),
                findViewById(R.id.kub4),
                findViewById(R.id.kub5),
                findViewById(R.id.kub6)
        };

        daysOfWeekTexts = new TextView[]{
                findViewById(R.id.kubText),
                findViewById(R.id.kubText1),
                findViewById(R.id.kubText2),
                findViewById(R.id.kubText3),
                findViewById(R.id.kubText4),
                findViewById(R.id.kubText5),
                findViewById(R.id.kubText6)
        };

        // Инициализация GestureDetector
        gestureDetector = new GestureDetector(this, new SwipeGestureListener());
        Calendar calendar = Calendar.getInstance();
        currentMonth = calendar.get(Calendar.MONTH);
        currentYear = calendar.get(Calendar.YEAR);
        currentDay = calendar.get(Calendar.DATE)-1;
        // Присвоение чисел текущей недели

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        TextView monthNameTextView = findViewById(R.id.nov);
        TextView monthNameTextView1 = findViewById(R.id.nov1);
        TextView DayNameTextView = findViewById(R.id.name_maouth);
        updateMonthText(monthNameTextView, currentMonth);
        updateMonthText(monthNameTextView1, currentMonth);

        updateDayText(DayNameTextView, currentDay);

        // Присвоение чисел текущей недели
        int[] monthDaysCount = new int[12]; // Массив для подсчета дней по месяцам
        for (int i = 0; i < daysOfWeekImages.length; i++) {
            int dayNumber = calendar.get(Calendar.DAY_OF_MONTH); // Число дня
            int weekMonth = calendar.get(Calendar.MONTH);

            monthDaysCount[weekMonth]++;

            daysOfWeekTexts[i].setText(String.valueOf(dayNumber));
            if (weekMonth == currentMonth) {
                daysOfWeekTexts[i].setTextColor(Color.BLACK);
            } else {
                daysOfWeekTexts[i].setTextColor(Color.GRAY);
            }


            // Смещение даты на следующий день
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        int maxDays = 0;
        int dominantMonth = currentMonth;
        for (int i = 0; i < monthDaysCount.length; i++) {
            if (monthDaysCount[i] > maxDays) {
                maxDays = monthDaysCount[i];
                dominantMonth = i;
            }
        }
        currentMonth = dominantMonth;

        // Устанавливаем обработчик свайпов для рамки
        ImageView ramka = findViewById(R.id.ramka);
        ramka.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event); // Обработка свайпов
                return false; // Возвращаем false, чтобы передать событие дальше
            }
        });


        View primoButton = findViewById(R.id.menu);
        primoButton.setOnClickListener(v -> onBackPressed());

    }

    // Класс для обработки свайпов
    private class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > 100) { // Порог для вертикального свайпа
                    if (e1.getY() > e2.getY()) {
                        // Свайп сверху вниз (например, переключение на следующую неделю)
                        changeDates(true); // true - переход к следующей неделе
                    } else {
                        // Свайп снизу вверх (например, переключение на предыдущую неделю)
                        changeDates(false); // false - переход к предыдущей неделе
                    }
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    // Метод для изменения дат в зависимости от направления свайпа
    private void changeDates(boolean nextWeek) {
        // Обновляем смещение
        if (nextWeek) {
            currentWeekOffset++;
        } else {
            currentWeekOffset--;
        }

        // Получаем ссылку на TextView
        TextView monthNameTextView = findViewById(R.id.nov);

        // Создаем объект Calendar, начиная с текущей недели
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // Устанавливаем начало недели на понедельник

        calendar.add(Calendar.WEEK_OF_YEAR, currentWeekOffset);


        int currentMonth = calendar.get(Calendar.MONTH);

        updateMonthText(monthNameTextView, currentMonth);

        // Обновление чисел текущей недели
        for (int i = 0; i < daysOfWeekImages.length; i++) {
            Calendar dayCalendar = (Calendar) calendar.clone();
            dayCalendar.add(Calendar.DAY_OF_WEEK, i);

            int dayNumber = dayCalendar.get(Calendar.DAY_OF_MONTH); // Число дня
            daysOfWeekTexts[i].setText(String.valueOf(dayNumber));
            daysOfWeekTexts[i].setTextColor(Color.BLACK);
        }

        Toast.makeText(this, "Неделя обновлена: " + currentWeekOffset, Toast.LENGTH_SHORT).show();
    }

    private void updateMonthText(TextView monthNameTextView, int currentMonth) {
        String[] monthNames = {
                "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
                "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
        };

        monthNameTextView.setText(monthNames[currentMonth]);
    }

    private void updateDayText(TextView DayNameTextView, int currentDay) {
        String[] DayNames = {
                "1", "2", "3", "4", "5", "6",
                "7", "8", "9", "10", "11", "12", "13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"
        };

        DayNameTextView.setText(DayNames[currentDay]);
    }





    private void updateCalendar(TextView monthNameTextView) {
        // Проверяем границы месяца
        if (currentMonth < 0) {
            currentMonth = 11;
            currentYear--;
        } else if (currentMonth > 11) {
            currentMonth = 0;
            currentYear++;
        }


        monthNameTextView.setText(String.format("%s", monthNames[currentMonth]));

        // Создаем календарь для 1-го числа месяца
        Calendar calendar = Calendar.getInstance();
        calendar.set(currentYear, currentMonth, 1); // Устанавливаем 1-е число месяца

        // День недели первого дня месяца
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        firstDayOfWeek = (firstDayOfWeek == Calendar.SUNDAY) ? 6 : firstDayOfWeek - 2;

        // Сдвигаем к понедельнику
        calendar.add(Calendar.DAY_OF_MONTH, -firstDayOfWeek);

        // Обновляем UI кубов
        for (int i = 0; i < 7; i++) {
            TextView cube = daysOfWeekTexts[i];

            if (cube != null) {
                int dayNumber = calendar.get(Calendar.DAY_OF_MONTH);
                cube.setText(String.valueOf(dayNumber));

                // Проверяем, текущий это месяц или соседний
                if (calendar.get(Calendar.MONTH) != currentMonth) {
                    cube.setTextColor(Color.GRAY); // Дни соседних месяцев
                } else {
                    cube.setTextColor(Color.BLACK); // Дни текущего месяца
                }

                // Подсветка текущего дня
                Calendar today = Calendar.getInstance();
                if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                        calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                        calendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
                    daysOfWeekImages[i].setBackgroundColor(Color.CYAN);
                } else {
                    daysOfWeekImages[i].setBackgroundColor(Color.TRANSPARENT);
                }

                // Переходим к следующему дню
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        }

        // Сбрасываем смещение недели
        currentWeekOffset = 0;
    }




    // Вспомогательная функция для получения количества дней в месяце
    private int getDaysInMonth(int month, int year) {
        if (month == 1) { // Февраль
            return isLeapYear(year) ? 29 : 28;
        }
        // Месяцы с 31 днём
        if (month == 0 || month == 2 || month == 4 || month == 6 || month == 7 || month == 9 || month == 11) {
            return 31;
        }
        return 30; // Остальные месяцы
    }

    // Проверяем високосный год
    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }



    private void showSelectionDialog() {

        boolean isInGroppage = this instanceof registr2;
        Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
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

        TextView ukol = new TextView(this);
        ukol.setText("Неделя2");
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
        if (isInGroppage) {
            // Меняем фон кнопки
            monthText.setBackgroundResource(R.drawable.gold);
        }else {
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
                Intent intent = new Intent(registr2.this, MounthlyCalendar.class); // Замените CurrentActivity на имя вашей текущей активности
                startActivity(intent);
            }
        });
        weekText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registr2.this, registr.class); // Замените CurrentActivity на имя вашей текущей активности
                startActivity(intent);
            }
        });

        ukol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registr2.this, registr2.class); // Замените CurrentActivity на имя вашей текущей активности
                startActivity(intent);
            }
        });

        layout.addView(calenarText);
        layout.addView(monthText);
        layout.addView(weekText);
        layout.addView(ukol);






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
        dialog.setOnDismissListener(d -> {
            ImageView blurBackground = findViewById(R.id.blur_background);
            blurBackground.setImageDrawable(null);
            blurBackground.setVisibility(View.GONE); // Скрываем размытие
        });

    }
}
