package sk.vinisoft.formexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_update.*
import sk.vinisoft.formexample.MainActivity.Companion.EMAIL_PUBLISHING_SUBJECT

class UpdateFragment : Fragment() {

    private var disposable: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_update, container, false)

    override fun onResume() {
        super.onResume()
        disposable = EMAIL_PUBLISHING_SUBJECT.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread())
            .subscribe { validationInput -> lastCheckedEmail.text = validationInput }
    }

    override fun onPause() {
        disposable?.dispose()
        super.onPause()
    }
}
