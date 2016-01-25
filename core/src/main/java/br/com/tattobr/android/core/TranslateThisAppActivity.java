package br.com.tattobr.android.core;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class TranslateThisAppActivity extends BaseActivity implements View.OnClickListener {
    private Button mButtonTranslateApp;

    @Override
    public String getIntersticialAdUnitId() {
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

    private void initialize(){
        setupToolbar(false, true);
        setupAds();

        mButtonTranslateApp = (Button) findViewById(R.id.button_translate_app);
        mButtonTranslateApp.setOnClickListener(this);
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
        if(v == mButtonTranslateApp){
            sendEmail();
        }
    }

    private void sendEmail(){
        String subjectEmail = String.format("%s - %s", getString(R.string.core_title_activity_translate_this_app), getString(R.string.app_name));
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, subjectEmail);
        intent.setData(Uri.parse("mailto:apps@freemindapps.com.br"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try{
            startActivity(intent);
        }catch (ActivityNotFoundException anfe){
            anfe.printStackTrace();
        }
    }
}