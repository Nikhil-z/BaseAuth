package xyz.belvi.baseauth.countrySelector;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import xyz.belvi.validator.PhoneNumberValidator;

import java.util.Arrays;

import xyz.belvi.baseauth.R;

public class CountrySelectorActivity extends AppCompatActivity {

    public static final String SELECTED_COUNTRY = "SELECTED_COUNTRY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_selector);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initSelector();
    }

    private void initSelector() {
        RecyclerView recyclerView = findViewById(R.id.cc_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(false);
        PhoneNumberValidator.Country countries = null;
        try {
            countries = PhoneNumberValidator.Country.valueOf(getIntent().getStringExtra(SELECTED_COUNTRY));
        } catch (NullPointerException ex) {

        }
        if (countries != null)
            recyclerView.scrollToPosition(Arrays.asList(PhoneNumberValidator.Country.values()).indexOf(countries));
        recyclerView.setAdapter(new CountrySelectorAdapter(countries) {
            @Override
            public void onCountrySelected(PhoneNumberValidator.Country countries) {
                setResult(Activity.RESULT_OK, getIntent().putExtra(SELECTED_COUNTRY, countries.name()));
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
