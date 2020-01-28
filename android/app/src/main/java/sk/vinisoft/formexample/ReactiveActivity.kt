package sk.vinisoft.formexample

import android.os.Bundle
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_common_ui.*
import java.util.concurrent.TimeUnit

class ReactiveActivity : SyncActivity() {

    private var disposable: Disposable? = null

    override fun onResume() {
        super.onResume()
        disposable = searchInput.textChanges().debounce(DELAY, TimeUnit.MILLISECONDS)
            .map { input -> InputValidation(input.toString()) }
            .map { validationInput ->
                validationInput.copy(
                    inputText = validationInput.inputText,
                    isValid = emailValidationService.isEmailValid(validationInput.inputText)
                )
            }
            .map { validationInput ->
                validationInput.copy(
                    inputText = validationInput.inputText,
                    isAvailable = emailRegistrationService.isEmailFree(validationInput.inputText)
                )
            }
            .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread())
            .subscribe { validationInput -> handleResult(validationInput) }
    }

    override fun onPause() {
        disposable?.dispose()
        super.onPause()
    }
}
