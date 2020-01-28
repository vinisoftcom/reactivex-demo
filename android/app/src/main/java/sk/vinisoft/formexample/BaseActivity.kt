package sk.vinisoft.formexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_common_ui.*

abstract class BaseActivity : AppCompatActivity() {

    val emailRegistrationService = AsyncEmailRegistrationService()
    val emailValidationService = AsyncEmailValidationService()

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

    companion object {
        val DELAY = 1000L
    }
}
