package sk.vinisoft.formexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_common_ui.*
import java.util.concurrent.TimeUnit

class ReactiveActivity : AppCompatActivity() {

    private var disposable: Disposable? = null
    val emailRegistrationService = EmailRegistrationService()
    val emailValidationService = EmailValidationService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_ui)
    }

    override fun onResume() {
        super.onResume()
        disposable = searchInput.textChanges().debounce(1, TimeUnit.SECONDS)
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

    private fun handleResult(validationInput: InputValidation?) {
        validationInput?.let {
            if (!it.isValid) {
                validationResult.text = getString(R.string.is_not_valid)
            } else if (!it.isAvailable) {
                validationResult.text = getString(R.string.is_not_available)
            } else {
                validationResult.text = getString(R.string.is_ok)
            }
        }
    }

    data class InputValidation(
        val inputText: String,
        val isValid: Boolean = false,
        val isAvailable: Boolean = false
    )
}
