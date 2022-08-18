package dev.vicart.remotewaker.views.fragments.stepper

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import dev.vicart.remotewaker.databinding.FragmentBeforeContinueStepperBinding

class BeforeContinueFragment : Fragment() {

    private lateinit var binding: FragmentBeforeContinueStepperBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBeforeContinueStepperBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val drawable = binding.beforeContinueCheck.drawable as AnimatedVectorDrawable

        drawable.start()
    }
}