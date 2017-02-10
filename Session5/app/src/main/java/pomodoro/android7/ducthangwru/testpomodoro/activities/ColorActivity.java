package pomodoro.android7.ducthangwru.testpomodoro.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pomodoro.android7.ducthangwru.testpomodoro.R;
import pomodoro.android7.ducthangwru.testpomodoro.adapters.ColorAdapter;

public class ColorActivity extends AppCompatActivity {
    private ColorAdapter colorAdapter;
    @BindView(R.id.rv_color)
    RecyclerView rv_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        setupUI();
    }

    private void setupUI() {
        ButterKnife.bind(this);
        colorAdapter = new ColorAdapter();
        rv_color.setAdapter(colorAdapter);
        rv_color.setLayoutManager(new GridLayoutManager(this, 4));
    }
}