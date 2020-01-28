package sk.vinisoft.formexample

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_common_ui.*
import sk.vinisoft.formexample.MainActivity.Companion.EMAIL_PUBLISHING_SUBJECT

abstract class BaseActivity : FragmentActivity() {

    val emailRegistrationService = AsyncEmailRegistrationService()
    val emailValidationService = AsyncEmailValidationService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_ui)
    }

    protected fun handleResult(validationInput: InputValidation?) {
        validationInput?.let {
            EMAIL_PUBLISHING_SUBJECT.onNext(it.inputText)
            if (!it.isValid) {
                searchLayout.error = getString(R.string.is_not_valid)
            } else if (!it.isAvailable) {
                searchLayout.error = getString(R.string.is_not_available)
            } else {
                searchLayout.error = getString(R.string.is_ok)
            }
        }
    }

    companion object {
        val DELAY = 1000L
    }
}
