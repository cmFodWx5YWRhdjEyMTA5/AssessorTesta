package com.vipin.assessortesta.utils;


import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.model.NumericRange;
import com.basgeekball.awesomevalidation.utility.custom.CustomErrorReset;
import com.basgeekball.awesomevalidation.utility.custom.CustomValidation;
import com.basgeekball.awesomevalidation.utility.custom.CustomValidationCallback;
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation;
import com.basgeekball.awesomevalidation.validators.BasicValidator;
import com.basgeekball.awesomevalidation.validators.ColorationValidator;
import com.basgeekball.awesomevalidation.validators.TextInputLayoutValidator;
import com.basgeekball.awesomevalidation.validators.UnderlabelValidator;
import com.basgeekball.awesomevalidation.validators.Validator;
import com.google.common.collect.Range;
import java.util.regex.Pattern;

public class AwesomeValidation {
    private Validator mValidator = null;
    private static boolean autoFocusOnFirstFailure = true;

    public AwesomeValidation(ValidationStyle var1) {
        switch(var1) {
            case BASIC:
                if (this.mValidator == null || !(this.mValidator instanceof BasicValidator)) {
                    this.mValidator = new BasicValidator();
                }

                return;
            case COLORATION:
                if (this.mValidator == null || !(this.mValidator instanceof ColorationValidator)) {
                    this.mValidator = new ColorationValidator();
                }

                return;
            case UNDERLABEL:
                if (this.mValidator == null || !(this.mValidator instanceof UnderlabelValidator)) {
                    this.mValidator = new UnderlabelValidator();
                }

                return;
            case TEXT_INPUT_LAYOUT:
                if (this.mValidator == null || !(this.mValidator instanceof TextInputLayoutValidator)) {
                    this.mValidator = new TextInputLayoutValidator();
                }

                return;
            default:
        }
    }

    public static boolean isAutoFocusOnFirstFailureEnabled() {
        return autoFocusOnFirstFailure;
    }

    public static void disableAutoFocusOnFirstFailure() {
        autoFocusOnFirstFailure = false;
    }

    private void checkIsColorationValidator() {
        if (!(this.mValidator instanceof ColorationValidator)) {
            throw new UnsupportedOperationException("Only supported by ColorationValidator.");
        }
    }

    private void checkIsUnderlabelValidator() {
        if (!(this.mValidator instanceof UnderlabelValidator)) {
            throw new UnsupportedOperationException("Only supported by UnderlabelValidator.");
        }
    }

    private void checkIsTextInputLayoutValidator() {
        if (!(this.mValidator instanceof TextInputLayoutValidator)) {
            throw new UnsupportedOperationException("Only supported by TextInputLayoutValidator.");
        }
    }

    private void checkIsNotTextInputLayoutValidator() {
        if (this.mValidator instanceof TextInputLayoutValidator) {
            throw new UnsupportedOperationException("Not supported by TextInputLayoutValidator.");
        }
    }

    public void setContext(Context var1) {
        this.checkIsUnderlabelValidator();
        ((UnderlabelValidator)this.mValidator).setContext(var1);
    }

    public void setColor(int var1) {
        this.checkIsColorationValidator();
        ((ColorationValidator)this.mValidator).setColor(var1);
    }

    public void setUnderlabelColor(int var1) {
        this.checkIsUnderlabelValidator();
        ((UnderlabelValidator)this.mValidator).setColor(var1);
    }

    public void setUnderlabelColorByResource(int var1) {
        this.checkIsUnderlabelValidator();
        ((UnderlabelValidator)this.mValidator).setColorByResource(var1);
    }

    public void setTextInputLayoutErrorTextAppearance(int var1) {
        this.checkIsTextInputLayoutValidator();
        ((TextInputLayoutValidator)this.mValidator).setErrorTextAppearance(var1);
    }

    public void addValidation(EditText var1, String var2, String var3) {
        this.checkIsNotTextInputLayoutValidator();
        this.mValidator.set(var1, var2, var3);
    }

    public void addValidation(TextInputLayout var1, String var2, String var3) {
        this.checkIsTextInputLayoutValidator();
        this.mValidator.set(var1, var2, var3);
    }

    public void addValidation(Activity var1, int var2, String var3, int var4) {
        this.mValidator.set(var1, var2, var3, var4);
    }

    public void addValidation(EditText var1, Pattern var2, String var3) {
        this.checkIsNotTextInputLayoutValidator();
        this.mValidator.set(var1, var2, var3);
    }

    public void addValidation(TextInputLayout var1, Pattern var2, String var3) {
        this.checkIsTextInputLayoutValidator();
        this.mValidator.set(var1, var2, var3);
    }

    public void addValidation(Activity var1, int var2, Pattern var3, int var4) {
        this.mValidator.set(var1, var2, var3, var4);
    }

    public void addValidation(EditText var1, Range var2, String var3) {
        this.checkIsNotTextInputLayoutValidator();
        this.mValidator.set(var1, new NumericRange(var2), var3);
    }

    public void addValidation(TextInputLayout var1, Range var2, String var3) {
        this.checkIsTextInputLayoutValidator();
        this.mValidator.set(var1, new NumericRange(var2), var3);
    }

    public void addValidation(Activity var1, int var2, Range var3, int var4) {
        this.mValidator.set(var1, var2, new NumericRange(var3), var4);
    }

    public void addValidation(EditText var1, EditText var2, String var3) {
        this.checkIsNotTextInputLayoutValidator();
        this.mValidator.set(var1, var2, var3);
    }

    public void addValidation(TextInputLayout var1, TextInputLayout var2, String var3) {
        this.checkIsTextInputLayoutValidator();
        this.mValidator.set(var1, var2, var3);
    }

    public void addValidation(Activity var1, int var2, int var3, int var4) {
        this.mValidator.set(var1, var2, var3, var4);
    }

    public void addValidation(EditText var1, SimpleCustomValidation var2, String var3) {
        this.checkIsNotTextInputLayoutValidator();
        this.mValidator.set(var1, var2, var3);
    }

    public void addValidation(TextInputLayout var1, SimpleCustomValidation var2, String var3) {
        this.checkIsTextInputLayoutValidator();
        this.mValidator.set(var1, var2, var3);
    }

    public void addValidation(Activity var1, int var2, SimpleCustomValidation var3, int var4) {
        this.mValidator.set(var1, var2, var3, var4);
    }

    public void addValidation(View var1, CustomValidation var2, CustomValidationCallback var3, CustomErrorReset var4, String var5) {
        this.mValidator.set(var1, var2, var3, var4, var5);
    }

    public void addValidation(Activity var1, int var2, CustomValidation var3, CustomValidationCallback var4, CustomErrorReset var5, int var6) {
        this.mValidator.set(var1, var2, var3, var4, var5, var6);
    }

    public boolean validate() {
        return this.mValidator.trigger();
    }

    public void clear() {
        this.mValidator.halt();
    }
}
