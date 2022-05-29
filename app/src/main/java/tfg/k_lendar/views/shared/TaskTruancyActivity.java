package tfg.k_lendar.views.shared;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import tfg.k_lendar.databinding.ActivityTaskTruancyBinding;
import tfg.k_lendar.views.shared.ui.main.SectionsPagerAdapter;

public class TaskTruancyActivity extends AppCompatActivity {

    private ActivityTaskTruancyBinding binding;
    public static String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int intent = getIntent().getExtras().getInt("frgToLoad");

        binding = ActivityTaskTruancyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        date = getIntent().getStringExtra("date");

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        viewPager.setCurrentItem(intent, true);

        tabs.setupWithViewPager(viewPager);
    }
}