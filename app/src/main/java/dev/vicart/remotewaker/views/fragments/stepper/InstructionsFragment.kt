package dev.vicart.remotewaker.views.fragments.stepper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.vicart.remotewaker.databinding.FragmentInstructionStepperBinding

class InstructionsFragment : Fragment() {

    private lateinit var binding: FragmentInstructionStepperBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInstructionStepperBinding.inflate(inflater, container, false)
        return binding.root
    }
}