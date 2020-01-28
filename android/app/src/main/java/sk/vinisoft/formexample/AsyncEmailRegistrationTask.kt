package sk.vinisoft.formexample

import android.os.AsyncTask

abstract class AsyncEmailRegistrationTask :
    AsyncTask<InputValidation, Void, InputValidation?>() {
    override fun doInBackground(vararg params: InputValidation?): InputValidation? {
        return params[0]?.let { input ->
            InputValidation(
                inputText = input.inputText,
                isValid = input.isValid,
                isAvailable = isEmailFree(input.inputText)
            )
        }
    }

    fun isEmailFree(email: String): Boolean {
        return !EmailRegistrationService.USED_EMAILS.contains(email)
    }
}