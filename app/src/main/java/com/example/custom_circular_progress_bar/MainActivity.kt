package com.example.custom_circular_progress_bar

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var progr = 0
    private var sec_progr = 0
    private var dummy: Int = 0
    var handler: Handler? = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //updateProgressBar()

        button_incr.setOnClickListener {
            if (progr <= 90) {
                progr += 10
                updateProgressBar()
            }
        }
        button_decr.setOnClickListener {
            if (progr >= 10) {
                progr -= 10
                updateProgressBar()
            }
        }
        auto_incr.setOnClickListener {
            showProgress()
        }
        btnSecondProgressBar.setOnClickListener {
            showFillUpProgress()
        }


    }

    private fun updateProgressBar() {
        progress_bar.progress = progr
        text_view_progress.text = "$progr%"
    }

    private fun showProgress() {
        // task is run on a thread
        Thread(Runnable {
            // dummy thread mimicking some operation whose progress can be tracked
            while (progr < 100) {
                // performing some dummy operation
                try {
                    dummy = dummy + 10

                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                // tracking progress
                progr = dummy

                runOnUiThread {
                    kotlin.run {
                        // Updating the progress bar
                        progress_bar.progress = progr
                        text_view_progress.text = "$progr%"
                    }
                }

            }

        }).start()
    }

    private fun showFillUpProgress() {

        Thread(Runnable {
            while (sec_progr < 100) {
                sec_progr += 1

                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                handler?.post {
                        secondProgressBar.progress = sec_progr
                        tv_second_progress.text = "Complete $sec_progr% of 100"
                        if (sec_progr == 51) {
                            tv_second_progress.setTextColor(Color.parseColor("#ffffff"))
                        }

                        if (sec_progr == 100) {
                            tv_second_progress.text = "All tasks completed"
                            tv_second_progress.setTextColor(Color.parseColor("#ffffff"))
                        }

                    }
            }
        }).start()
    }

}