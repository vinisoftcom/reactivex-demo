package sk.vinisoft.formexample

import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_common_ui.*
import java.util.concurrent.TimeUnit

class AsyncReactiveActivity : BaseActivity() {

    private var disposable: Disposable? = null

    override fun onResume() {
        super.onResume()
        disposable = searchInput.textChanges().debounce(DELAY, TimeUnit.MILLISECONDS)
            .map { input -> InputValidation(input.toString()) }
            .flatMap { validationInput ->
                emailValidationService.getObservable(validationInput)
            }
            .flatMap { validationInput ->
                emailRegistrationService.getObservable(validationInput)
            }
            .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread())
            .subscribe { validationInput -> handleResult(validationInput) }
    }

    override fun onPause() {
        disposable?.dispose()
        super.onPause()
    }
}
