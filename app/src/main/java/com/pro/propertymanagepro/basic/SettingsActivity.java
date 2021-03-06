package com.pro.propertymanagepro.basic;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pro.propertymanagepro.R;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settingsfragment);
            findPreference("prefAndroid").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Toast toast = Toast.makeText(getActivity(), "最新版本：2.0.1\n您已是最新版本！", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    return true;
                }
            });
        }

//        @Override
//        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
//            super.onCreate(savedInstanceState);
//            addPreferencesFromResource(R.xml.settingsfragment);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
