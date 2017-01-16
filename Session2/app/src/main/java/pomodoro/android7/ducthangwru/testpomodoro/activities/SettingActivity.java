package pomodoro.android7.ducthangwru.testpomodoro.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import pomodoro.android7.ducthangwru.testpomodoro.R;
import pomodoro.android7.ducthangwru.testpomodoro.settings.SettingCredendials;
import pomodoro.android7.ducthangwru.testpomodoro.settings.SharePrefs;


public class SettingActivity extends AppCompatActivity {
    private TextView tv_workTime, tv_Break, tv_LongBreak;
    private Spinner sp_Choose;
    private SeekBar sb_workTime, sb_Break, sb_LongBreak;
    private String[] data = new String[]{ "0", "1", "2", "3", "4", "5", "6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getReferences();
        setupUI();
        addListeners();
    }


    private void setupUI() {
        sb_workTime.setProgress(25);
        sb_Break.setProgress(5);
        sb_LongBreak.setProgress(10);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, data);
        sp_Choose.setAdapter(arrayAdapter);
        if(SharePrefs.getInstance().getSetting() == null)
            sp_Choose.setSelection(Integer.parseInt(data[3]));
    }

    private void addListeners() {
        if (SharePrefs.getInstance().getSetting() != null) {
            SettingCredendials settingCredentials = SharePrefs.getInstance().getSetting();
            sb_workTime.setProgress(settingCredentials.getWorkTime());
            sb_Break.setProgress(settingCredentials.getTimeBreak());
            sb_LongBreak.setProgress(settingCredentials.getLongBreak());
            sp_Choose.setSelection(settingCredentials.getBreaks());
        }

        tv_workTime.setText(sb_workTime.getProgress() + getResources().getString(R.string.mins) + "");
        tv_Break.setText(sb_Break.getProgress() + getResources().getString(R.string.mins) + "");
        tv_LongBreak.setText(sb_LongBreak.getProgress() + getResources().getString(R.string.mins) + "");

        sb_workTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv_workTime.setText(sb_workTime.getProgress() + getResources().getString(R.string.mins) + "");
                sb_workTime.setProgress(i);
                SharePrefs.getInstance().putSetting(new SettingCredendials(sb_workTime.getProgress(), sb_Break.getProgress(), sb_LongBreak.getProgress(), sp_Choose.getSelectedItemPosition()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_Break.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv_Break.setText(sb_Break.getProgress() + getResources().getString(R.string.mins) + "");
                sb_Break.setProgress(i);
                SharePrefs.getInstance().putSetting(new SettingCredendials(sb_workTime.getProgress(), sb_Break.getProgress(), sb_LongBreak.getProgress(), sp_Choose.getSelectedItemPosition()));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_LongBreak.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv_LongBreak.setText(sb_LongBreak.getProgress() + getResources().getString(R.string.mins) + "");
                sb_LongBreak.setProgress(i);
                SharePrefs.getInstance().putSetting(new SettingCredendials(sb_workTime.getProgress(), sb_Break.getProgress(), sb_LongBreak.getProgress(), sp_Choose.getSelectedItemPosition()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sp_Choose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sp_Choose.setSelection(Integer.parseInt(data[position]));
                SharePrefs.getInstance().putSetting(new SettingCredendials(sb_workTime.getProgress(), sb_Break.getProgress(), sb_LongBreak.getProgress(), position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getReferences() {

        tv_workTime = (TextView) findViewById(R.id.tv_worktime);
        tv_Break = (TextView) findViewById(R.id.tv_break);
        tv_LongBreak = (TextView) findViewById(R.id.tv_long_break);
        sb_workTime = (SeekBar) findViewById(R.id.sb_workTime);
        sb_Break = (SeekBar) findViewById(R.id.sb_break);
        sb_LongBreak = (SeekBar) findViewById(R.id.sb_longBreak);
        sp_Choose = (Spinner) findViewById(R.id.sp_choose_break);
    }
}