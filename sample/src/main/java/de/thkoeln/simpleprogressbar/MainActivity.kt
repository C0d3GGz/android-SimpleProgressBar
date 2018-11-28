package de.thkoeln.simpleprogressbar

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import de.thkoeln.simpleprogressbar.sample.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        myCustomProgressbar.bgColor = ContextCompat.getColor(this, R.color.colorAccent)
//        myCustomProgressbar.primaryColor = ContextCompat.getColor(this, android.R.color.holo_blue_bright)
//        myCustomProgressbar.secondaryColor = ContextCompat.getColor(this, android.R.color.holo_blue_dark)

        increaseProgress.setOnClickListener { myCustomProgressbar.primaryProgress += 10 }
        increaseSecondary.setOnClickListener { myCustomProgressbar.secondaryProgress += 10}
        increaseMax.setOnClickListener {myCustomProgressbar.maxProgress += 10}
    }
}
