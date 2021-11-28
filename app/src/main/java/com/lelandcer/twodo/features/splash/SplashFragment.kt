package com.lelandcer.twodo.features.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.lelandcer.twodo.R
import kotlinx.coroutines.delay


/**
 * Splash screen fragment used to ease the user into the app on launch and mask any initial loading
 */
class SplashFragment : Fragment() {

    init {
        lifecycleScope.launchWhenStarted {
            // TODO Connect to any "ready" events if initial loading is needed
            delay(2000)
//            val action = SplashFragmentDirections.actionSplashFragmentToTwoDoListsFragment()
//            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onResume() {
        super.onResume()
        //TODO Rethink actionbar implementation
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }


}