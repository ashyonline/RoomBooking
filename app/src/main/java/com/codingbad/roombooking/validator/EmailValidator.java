package com.codingbad.roombooking.validator;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * Created by ayelen on 11/23/15.
 */
public class EmailValidator extends EditTextValidator {

    public EmailValidator(EditText editText, String errorMessage) {
        super(editText, errorMessage);
    }

    @Override
    public boolean validate() {
        if (!isValidEmail(mEditText.getText())) {
            mEditText.setError(mErrorMessage);
            return false;
        }

        return super.validate();
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
