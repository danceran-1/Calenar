package com.example.mymodule;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private final Paint paint;
    private final int height;

    public DividerItemDecoration(Context context) {
        paint = new Paint();
        paint.setColor(context.getResources().getColor(android.R.color.darker_gray));
        height = 2; // Высота разделителя
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < parent.getChildCount() - 1; i++) {
            View child = parent.getChildAt(i);
            int top = child.getBottom();
            int bottom = top + height;
            c.drawRect(left, top, right, bottom, paint);
        }
    }
}
