package com.icedemon72.komsilook

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.icedemon72.komsilook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
	private lateinit var binding: ActivityMainBinding
	private lateinit var navController: NavController

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()

		// Use DataBinding to set the content view
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

		// Get the NavController using the binding reference
		val navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragment.id) as? androidx.navigation.fragment.NavHostFragment
		navController = navHostFragment?.navController ?: throw IllegalStateException("NavController not found")

		ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
			val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			view.updatePadding(
				top = systemBarsInsets.top,
//				bottom = systemBarsInsets.bottom
			)
			insets
		}

		// Bottom navigation show/hide handler
		navController.addOnDestinationChangedListener { _, destination, _ ->
			binding.bottomNavigationView.visibility = when (destination.id) {
				R.id.loginFragment, R.id.registerFragment -> View.GONE
				else -> View.VISIBLE
			}
		}

		binding.bottomNavigationView.selectedItemId = R.id.nav_home
		binding.bottomNavigationView.setOnItemSelectedListener { item ->
			when (item.itemId) {
				R.id.nav_search -> {

					true
				}
				R.id.nav_home -> {
					navController.navigate(R.id.homeFragment)
					true
				}
				R.id.nav_profile -> {
					navController.navigate(R.id.profileFragment)
					true
				}
				else -> false
			}
		}


	}

	override fun onSupportNavigateUp(): Boolean {
		return navController.navigateUp() || super.onSupportNavigateUp()
	}
}