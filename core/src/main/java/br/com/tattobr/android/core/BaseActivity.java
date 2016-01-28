package br.com.tattobr.android.core;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.InputType;

import br.com.tattobr.android.dialogs.AlertDialogFragment;
import br.com.tattobr.android.dialogs.EditTextAlertDialogFragment;

public abstract class BaseActivity extends br.com.tattobr.android.adsanalytics.BaseActivity implements
        AlertDialogFragment.AlertDialogFragmentListener,
        EditTextAlertDialogFragment.EditTextAlertDialogFragmentListener {
    protected final String FRAGMENT_TAG_WORKER = "br.com.tattobr.android.core.FRAGMENT_TAG_WORKER";
    protected final String FRAGMENT_TAG_DIALOG = "br.com.tattobr.android.core.FRAGMENT_TAG_DIALOG";

    private boolean mIsResumed;

    protected boolean isBaseActivityResumed() {
        return mIsResumed;
    }

    protected boolean hasWriteExternalStoragePermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    protected void requestWriteExternalStoragePermission(int requestCode) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
    }

    protected boolean shouldShowRequestPermissionRationaleWriteExternalStoragePermission() {
        return ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    protected void openPermissionSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mIsResumed = false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        mIsResumed = true;
    }

    protected void setupToolbar(boolean navigationDrawer, boolean navigationUp) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(navigationUp);
            actionBar.setHomeButtonEnabled(navigationDrawer);
        }
    }

    protected void removeDialogFragment() {
        removeDialogFragment(FRAGMENT_TAG_DIALOG);
    }

    protected void removeWorkerFragment() {
        removeWorkerFragment(FRAGMENT_TAG_WORKER);
    }

    protected void removeDialogFragment(String tag) {
        Fragment task = getSupportFragmentManager().findFragmentByTag(tag);
        if (task != null) {
            getSupportFragmentManager().beginTransaction().remove(task).commitAllowingStateLoss();
        }
    }

    protected void removeWorkerFragment(String tag) {
        Fragment task = getSupportFragmentManager().findFragmentByTag(tag);
        if (task != null) {
            getSupportFragmentManager().beginTransaction().remove(task).commitAllowingStateLoss();
        }
    }

    protected void showAlertDialog(int tag, String title, String message) {
        showAlertDialog(tag, title, message, true);
    }

    protected void showAlertDialog(int tag, String title, String message, boolean showNegativeButton) {
        showAlertDialog(tag, title, message, showNegativeButton, null);
    }

    protected void showAlertDialog(int tag, String title, String message, int checkbox, boolean showNegativeButton) {
        showAlertDialog(tag, title, message, checkbox, false, showNegativeButton, false, false, null);
    }

    protected void showAlertDialog(int tag, String title, String message, boolean showNegativeButton, Bundle bundle) {
        AlertDialogFragment alertDialog = AlertDialogFragment.newInstance(
                tag, title, message, false, showNegativeButton, false, false, bundle
        );
        showFragmentDialog(alertDialog);
    }

    protected void showAlertDialog(int tag, String title, String message, int checkbox, boolean checkboxChecked, boolean showNegativeButton, boolean cancelable, boolean cancelOnTouchOutside, Bundle params) {
        AlertDialogFragment alertDialog = AlertDialogFragment.newInstance(
                tag, title, message, checkbox, checkboxChecked, showNegativeButton, cancelable, cancelOnTouchOutside, params
        );
        showFragmentDialog(alertDialog);
    }

    protected void showAlertDialog(int tag, String title, String message, String positiveLabel, String negativeLabel) {
        AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance(tag, title, message, positiveLabel, negativeLabel, null);
        showFragmentDialog(alertDialogFragment);
    }

    protected void showEditTextDialog(int tag, int title, int message, String text) {
        showEditTextDialog(tag, title, message, text, -1, null);
    }

    protected void showEditTextDialog(int tag, int title, int message, String text, Bundle params) {
        showEditTextDialog(tag, title, message, text, -1, params);
    }

    protected void showEditTextDialogNumberKeyboard(int tag, int title, int message, String text) {
        showEditTextDialog(tag, title, message, text, InputType.TYPE_CLASS_NUMBER, null);
    }

    protected void showEditTextDialogNumberKeyboard(int tag, int title, int message, String text, Bundle params) {
        showEditTextDialog(tag, title, message, text, InputType.TYPE_CLASS_NUMBER, params);
    }

    protected void showEditTextDialog(int tag, int title, int message, String textDefault, int inputType, Bundle params) {
        EditTextAlertDialogFragment alertDialog = EditTextAlertDialogFragment.newInstance(
                tag, title, message, textDefault, inputType, params);
        showFragmentDialog(alertDialog);
    }

    protected void showFragmentDialog(DialogFragment dialog) {
        DialogFragment prevDialog = (DialogFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_DIALOG);
        if (prevDialog != null) {
            getSupportFragmentManager().beginTransaction().remove(prevDialog).commitAllowingStateLoss();
        }
        dialog.show(getSupportFragmentManager().beginTransaction(), FRAGMENT_TAG_DIALOG);
    }

    @Override
    public void onAlertDialogNegativeButton(int tag, boolean checked, Bundle params) {

    }

    @Override
    public void onAlertDialogPositiveButton(int tag, boolean checked, Bundle params) {

    }

    @Override
    public void onEditTextDialogPositiveButton(int tag, String text, Bundle params) {

    }

    @Override
    public void onEditTextDialogNegativeButton(int tag, Bundle params) {

    }
}
