package com.icedemon72.komsilook

import android.app.Application
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import com.icedemon72.komsilook.di.AppComponent
import com.icedemon72.komsilook.di.DaggerAppComponent // generated component...

class Komsilook : Application(), HasDefaultViewModelProviderFactory {

	lateinit var appComponent: AppComponent

	override fun onCreate() {
		super.onCreate()
		appComponent = DaggerAppComponent.factory().create(this)
	}

	override val defaultViewModelProviderFactory: ViewModelProvider.Factory
	get() = appComponent.viewModelFactory()
}