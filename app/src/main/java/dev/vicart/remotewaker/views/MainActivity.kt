package dev.vicart.remotewaker.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import dev.vicart.remotewaker.R
import dev.vicart.remotewaker.databinding.ActivityMainBinding
import dev.vicart.remotewaker.repositories.UsersRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashscreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var userInit = false

        splashscreen.setKeepOnScreenCondition {
            !userInit
        }

        binding = ActivityMainBinding.inflate(layoutInflater)

        val navController = binding.mainNavFragment.getFragment<NavHostFragment>().findNavController()

        runBlocking {
            UsersRepository.initLocalUser(this@MainActivity)

            if(UsersRepository.currentUser == null) {
                navController.graph.setStartDestination(R.id.loginFragment)
                navController.navigate(R.id.action_devicesFragment_to_loginFragment)
            }
            userInit = true
        }

        setContentView(binding.root)

        setSupportActionBar(binding.mainToolbar)
        val appBarConfig = AppBarConfiguration(setOf(R.id.devicesFragment, R.id.loginFragment))
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        return binding.mainNavFragment.getFragment<NavHostFragment>().findNavController().navigateUp()
    }
}