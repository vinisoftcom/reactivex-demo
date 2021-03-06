package sk.vinisoft.formexample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        reactive.setOnClickListener {
            startActivity(Intent(this, ReactiveActivity::class.java))
        }
        nonreactive.setOnClickListener {
            startActivity(Intent(this, NonReactiveActivity::class.java))
        }
        asyncreactive.setOnClickListener {
            startActivity(Intent(this, AsyncReactiveActivity::class.java))
        }
        asyncnonreactive.setOnClickListener {
            startActivity(Intent(this, AsyncNonReactiveActivity::class.java))
        }
    }

    companion object {
        val EMAIL_PUBLISHING_SUBJECT: BehaviorSubject<String> = BehaviorSubject.create()
    }
}
