package dev.vicart.remotewaker.views.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.BaseAdapter
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.vicart.remotewaker.R
import dev.vicart.remotewaker.databinding.DeviceRecyclerItemBinding
import dev.vicart.remotewaker.databinding.FragmentDevicesBinding
import dev.vicart.remotewaker.models.Device
import dev.vicart.remotewaker.viewmodels.DevicesViewModel

class DevicesFragment : Fragment() {

    private lateinit var binding: FragmentDevicesBinding

    private val vm: DevicesViewModel by navGraphViewModels(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDevicesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = vm
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = DeviceAdapter()
        binding.devicesRecycler.adapter = adapter

        vm.devices.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.devicesSwipeRefresh.isRefreshing = false
        }

        vm.loadDevices()

        binding.devicesSwipeRefresh.setOnRefreshListener {
            vm.loadDevices()
        }
    }

    class DeviceAdapter : ListAdapter<Device, DeviceAdapter.ViewHolder>(object: DiffUtil.ItemCallback<Device>() {
        override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean {
            return oldItem.id == newItem.id && oldItem.deviceId == newItem.deviceId &&
                    oldItem.name == newItem.name
        }
    }) {

        private lateinit var binding: DeviceRecyclerItemBinding

        class ViewHolder(private val binding: DeviceRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {

            private var isExpanded = false

            fun bind(item: Device) {
                binding.deviceItemTitle.text = item.name
                binding.deviceItemSubtitle.text = itemView.resources.getString(R.string.device_id, item.deviceId)
                binding.deviceRootLinear.setOnClickListener {
                    binding.deviceExpandArrow.animate().rotationBy(if(isExpanded) 180F else -180F).setDuration(300)
                        .setInterpolator(LinearOutSlowInInterpolator()).start()
                    isExpanded = !isExpanded
                    binding.deviceExpandableLayout.visibility = if(isExpanded) View.VISIBLE else View.GONE
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            binding = DeviceRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(getItem(position))
        }
    }
}