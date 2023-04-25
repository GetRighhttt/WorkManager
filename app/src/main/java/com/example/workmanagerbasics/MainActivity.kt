package com.example.workmanagerbasics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.workmanagerbasics.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val MAIN = "MAIN"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        floatingActionButtonOnClick()
    }

    /*
    Launches work manager's request with an on click.
    */
    private fun floatingActionButtonOnClick() {
        binding.apply {
            floatingActionButton.setOnClickListener {
                setOneTimeWorkRequest()
            }
        }
    }

    /*
    Step 2: Create work Request
     */
    private fun setOneTimeWorkRequest() {
        // work manager instance
        val workManager = WorkManager.getInstance(applicationContext)

        // constraints instance - sets constraints for when task should be performed
        val uploadDataConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            // .setRequiresCharging(true)
            .build()


        // variable for one time request
        val uploadWorkRequest = OneTimeWorkRequest.Builder(WorkerClass::class.java)
            .setInitialDelay(3000, TimeUnit.MILLISECONDS)
            .setConstraints(uploadDataConstraints)
            .build()

        val numerousWorkRequest =
            PeriodicWorkRequestBuilder<WorkerClass>(5000, TimeUnit.MILLISECONDS)

        // enqueueing the one time request
        workManager.enqueue(uploadWorkRequest)

        /*
        Observe code by Live Data, and set text fields to show state of workManager
         */
        workManager.getWorkInfoByIdLiveData(uploadWorkRequest.id)
            .observe(this, Observer { workInfo ->
                binding.apply {
                    textInfo.text = workInfo.state.name
                    secondInfo.text = workInfo.state.isFinished.toString()
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}