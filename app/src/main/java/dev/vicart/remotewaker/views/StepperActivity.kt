package dev.vicart.remotewaker.views

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import dev.vicart.remotewaker.databinding.ActivityStepperBinding
import dev.vicart.remotewaker.viewmodels.StepperViewModel

class StepperActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStepperBinding

    private val vm: StepperViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStepperBinding.inflate(layoutInflater)
        binding.vm = vm
        binding.lifecycleOwner = this
        setContentView(binding.root)

        setSupportActionBar(binding.stepperToolbar)

        val stepper = binding.stepperNavView
        val navController = binding.stepperNav.getFragment<NavHostFragment>().findNavController()

        stepper.setupWithNavController(navController)

        val appBarConfig = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfig)

        binding.stepperNextBtn.setOnClickListener {
            stepper.goToNextStep()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return binding.stepperNav.getFragment<NavHostFragment>().findNavController()
            .navigateUp()
    }

    override fun onBackPressed() {
        if(binding.stepperNavView.currentStep == 0) {
            super.onBackPressed()
        }
        else {
            onSupportNavigateUp()
        }
    }

    fun goToNextStep() {
        binding.stepperNavView.goToNextStep()
    }
}