package sk.vinisoft.formexample

import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_common_ui.*
import java.util.*

class AsyncNonReactiveActivity : BaseActivity() {
    private var timer: Timer? = null
    private var asyncValidationTask: InternalValidationTask? = null
    private var asyncRegistrationTask: InternalRegisterationTask? = null

    override fun onResume() {
        super.onResume()
        searchInput.addTextChangedListener {
            cancelTimer()
            cancelTask()
            timer = Timer().apply {
                val searched = it.toString()
                schedule(object : TimerTask() {
                    override fun run() {
                        cancelTask()
                        asyncValidationTask = InternalValidationTask().apply {
                            execute(InputValidation(searched))
                        }
                    }
                }, DELAY)
            }
        }
    }

    private fun cancelTask() {
        asyncValidationTask?.apply {
            cancel(true)
        }
        asyncRegistrationTask?.apply {
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


    inner class InternalValidationTask : AsyncEmailValidationTask() {
        override fun onPostExecute(result: InputValidation?) {
            super.onPostExecute(result)
            asyncRegistrationTask = InternalRegisterationTask().apply {
                execute(result)
            }
        }
    }

    inner class InternalRegisterationTask : AsyncEmailRegistrationTask() {
        override fun onPostExecute(result: InputValidation?) {
            super.onPostExecute(result)
            handleResult(result)
        }
    }

}