package sk.vinisoft.formexample

import android.os.AsyncTask
import io.reactivex.Observable

class AsyncEmailValidationService : EmailValidationService() {
    fun getObservable(input: InputValidation): Observable<InputValidation> =
        Observable.create { emitter ->
            emitter.onNext(
                InputValidation(
                    inputText = input.inputText,
                    isValid = isEmailValid(input.inputText),
                    isAvailable = input.isAvailable
                )
            )
        }
}