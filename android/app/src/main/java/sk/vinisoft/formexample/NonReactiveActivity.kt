package sk.vinisoft.formexample

import android.os.AsyncTask
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_common_ui.*
import java.util.*


class NonReactiveActivity : SyncActivity() {

    private var timer: Timer? = null
    private var asyncTask: IsEmailFreeTask? = null

    override fun onResume() {
        super.onResume()
        searchInput.addTextChangedListener {
            cancelTimer()
            timer = Timer().apply {
                val searched = it.toString()
                schedule(object : TimerTask() {
                    override fun run() {
                        cancelTask()
                        asyncTask = IsEmailFreeTask().apply {
                            execute(searched)
                        }
                    }
                }, DELAY)
            }
        }
    }

    private fun cancelTask() {
        asyncTask?.apply {
            cancel(true)
        }
    }

    private fun cancelTimer() {
        timer?.apply {
            cancel()
        }
    }

    override fun onPause() {
        cancelTask()
        super.onPause()
    }

    private inner class IsEmailFreeTask : AsyncTask<String, Void, InputValidation?>() {
        override fun doInBackground(vararg params: String?): InputValidation? {
            return params[0]?.let {
                InputValidation(
                    it,
                    emailValidationService.isEmailValid(it),
                    emailRegistrationService.isEmailFree(it)
                )
            }

        }

        override fun onPostExecute(result: InputValidation?) {
            super.onPostExecute(result)
            result?.let { handleResult(it) }
        }
    }
}
