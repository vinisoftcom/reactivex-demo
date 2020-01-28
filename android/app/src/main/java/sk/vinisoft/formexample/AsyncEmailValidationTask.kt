package sk.vinisoft.formexample

import android.os.AsyncTask

abstract class AsyncEmailValidationTask :
    AsyncTask<InputValidation, Void, InputValidation?>() {
    override fun doInBackground(vararg params: InputValidation?): InputValidation? {
        return params[0]?.let { input ->
            InputValidation(
                inputText = input.inputText,
                isValid = isEmailValid(input.inputText),
                isAvailable = input.isAvailable
            )
        }
    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}