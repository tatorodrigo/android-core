package br.com.tattobr.android.core;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TranslateThisAppActivity extends BaseActivity implements View.OnClickListener {
    private Button mButtonTranslateApp;

    @Override
    public String getIntersticialAdUnitId() {
        return null;
    }

    @Override
    public String getBannerAdUnitId() {
        return null;
    }

    @Override
    protected long getInterstitialAdMilisInterval() {
        return 0l;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_this_app);

        initialize();
    }

    private void initialize() {
        setupToolbar(false, true);
        setupAds();

        TextView textViewTranslateApp = (TextView) findViewById(R.id.text_view_info_translate_app);
        if (textViewTranslateApp != null) {
            String email = super.getTranslateThisAppEmail();
            if (email == null || email.isEmpty()) {
                email = getTranslateThisAppEmail();
            }

            if (!email.isEmpty()) {
                textViewTranslateApp.setText(getString(R.string.core_text_info_translate_app, email));
            }
        }

        mButtonTranslateApp = (Button) findViewById(R.id.button_translate_app);
        if (mButtonTranslateApp != null) {
            mButtonTranslateApp.setOnClickListener(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v == mButtonTranslateApp) {
            sendEmail();
        }
    }

    @Override
    protected String getTranslateThisAppEmail() {
        return getString(R.string.email_translate_this_app);
    }

    private void sendEmail() {
        String email = super.getTranslateThisAppEmail();
        if (email == null || email.isEmpty()) {
            email = getTranslateThisAppEmail();
        }

        if (email.isEmpty()) {
            throw new Error("Please, override method getTranslateThisAppEmail()");
        }
        String subjectEmail = String.format("%s - %s", getString(R.string.core_title_activity_translate_this_app), getString(R.string.app_name));
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, subjectEmail);
        intent.setData(Uri.parse("mailto:" + email));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
