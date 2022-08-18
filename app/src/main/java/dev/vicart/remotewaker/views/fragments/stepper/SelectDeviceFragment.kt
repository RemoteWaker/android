package dev.vicart.remotewaker.views.fragments.stepper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.vicart.remotewaker.R
import dev.vicart.remotewaker.databinding.FragmentSelectDeviceBinding
import dev.vicart.remotewaker.databinding.SelectDeviceRecyclerBinding
import dev.vicart.remotewaker.viewmodels.SelectDeviceViewModel
import dev.vicart.remotewaker.viewmodels.StepperViewModel
import dev.vicart.remotewaker.views.StepperActivity

class SelectDeviceFragment : Fragment() {

    private lateinit var binding: FragmentSelectDeviceBinding

    private val stepperVm: StepperViewModel by activityViewModels()

    private val vm: SelectDeviceViewModel by navGraphViewModels(R.id.stepper_nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectDeviceBinding.inflate(inflater, container, false)
        binding.vm = vm
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stepperVm.isNextEnabled.value = false

        vm.devices.observe(viewLifecycleOwner) {
            it?.let { response ->
                binding.selectDeviceFoundText.text = resources.getString(R.string.found_device_name, response.usn)
                binding.selectDeviceFoundAddr.text = resources.getString(R.string.found_device_ip, response.remoteAddr)
            }
        }

        binding.selectDeviceNoBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.selectDeviceYesBtn.setOnClickListener {
            (requireActivity() as StepperActivity).goToNextStep()
        }

        vm.searchDevices(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stepperVm.isNextEnabled.value = true
    }
}