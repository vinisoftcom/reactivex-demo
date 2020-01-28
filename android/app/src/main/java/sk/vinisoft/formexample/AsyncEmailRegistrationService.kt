package sk.vinisoft.formexample

import android.os.AsyncTask
import io.reactivex.Observable

class AsyncEmailRegistrationService : EmailRegistrationService() {
    fun getObservable(input: InputValidation): Observable<InputValidation> =
        Observable.create { emitter ->
            emitter.onNext(
                InputValidation(
                    inputText = input.inputText,
                    isValid = input.isValid,
                    isAvailable = isEmailFree(input.inputText)
                )
            )
        }
}