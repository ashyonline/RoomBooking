package com.codingbad.roombooking.validator;

import android.widget.EditText;

/**
 * Created by ayelen on 11/23/15.
 */
public class EditTextValidator {

    protected final EditText mEditText;
    protected final String mErrorMessage;

    public EditTextValidator(EditText editText, String errorMessage) {
        this.mEditText = editText;
        this.mErrorMessage = errorMessage;
    }

    public boolean validate() {
        if (!validateFieldIsNotEmpty()) {
            mEditText.setError(mErrorMessage);
            return false;
        }

        mEditText.setError(null);
        return true;
    }

    protected boolean validateFieldIsNotEmpty() {
        return mEditText.getText() != null && !mEditText.getText().toString().trim().isEmpty();
    }
}
