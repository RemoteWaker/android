package dev.vicart.remotewaker.views.fragments.stepper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import dev.vicart.remotewaker.R
import dev.vicart.remotewaker.databinding.FragmentSelectComputerBinding
import dev.vicart.remotewaker.network.ArpClient
import dev.vicart.remotewaker.viewmodels.SelectComputerViewModel

class SelectComputerFragment : Fragment() {

    private lateinit var binding: FragmentSelectComputerBinding

    private val vm: SelectComputerViewModel by navGraphViewModels(R.id.stepper_nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectComputerBinding.inflate(inflater, container, false)
        binding.vm = vm
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}