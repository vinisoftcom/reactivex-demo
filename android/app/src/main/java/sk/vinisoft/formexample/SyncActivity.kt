package sk.vinisoft.formexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_common_ui.*

abstract class SyncActivity : AppCompatActivity() {

    val emailRegistrationService = EmailRegistrationService()
    val emailValidationService = EmailValidationService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_ui)
    }

    protected fun handleResult(validationInput: InputValidation?) {
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

    companion object {
        val DELAY = 1000L
    }
}
