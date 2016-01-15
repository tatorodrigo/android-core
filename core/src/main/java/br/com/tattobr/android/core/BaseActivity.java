package br.com.tattobr.android.core;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import br.com.tattobr.android.dialogs.AlertDialogFragment;
import br.com.tattobr.android.dialogs.EditTextAlertDialogFragment;

public abstract class BaseActivity extends br.com.tattobr.android.adsanalytics.BaseActivity implements
        AlertDialogFragment.AlertDialogFragmentListener,
        EditTextAlertDialogFragment.EditTextAlertDialogFragmentListener{
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

    protected void removeDialogFragment() {
        Fragment task = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_DIALOG);
        if (task != null) {
            getSupportFragmentManager().beginTransaction().remove(task).commitAllowingStateLoss();
        }
    }

    protected void removeWorkerFragment() {
        Fragment task = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_WORKER);
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

    protected void showAlertDialog(int tag, String title, String message, boolean showNegativeButton, Bundle bundle) {
        AlertDialogFragment alertDialog = AlertDialogFragment.newInstance(
                tag, title, message, showNegativeButton, false, false, bundle
        );
        showFragmentDialog(alertDialog);
    }

    protected void showEditTextDialog(int tag, int title, int message, String text) {
        EditTextAlertDialogFragment editTextAlertDialogFragment = EditTextAlertDialogFragment.newInstance(
                tag, title, message, text
        );
        showFragmentDialog(editTextAlertDialogFragment);
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
    public void onEditTextDialogPositiveButton(int tag, String text) {

    }

    @Override
    public void onEditTextDialogNegativeButton(int tag) {

    }
}
