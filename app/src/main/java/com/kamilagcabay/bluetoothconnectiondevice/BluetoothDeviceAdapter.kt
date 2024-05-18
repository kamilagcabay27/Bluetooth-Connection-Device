package com.kamilagcabay.bluetoothconnectiondevice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BluetoothDeviceAdapter(private val deviceList: MutableList<String>) : RecyclerView.Adapter<BluetoothDeviceAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val deviceName: TextView = view.findViewById(R.id.device_name)
        val deviceIcon: ImageView = view.findViewById(R.id.device_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bluetooth_device, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val deviceName = deviceList[position]
        holder.deviceName.text = deviceName
        holder.deviceIcon.setImageResource(R.drawable.ic_launcher_foreground) // İkonu ayarlayın
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

    fun addDevice(deviceName: String) {
        deviceList.add(deviceName)
        notifyItemInserted(deviceList.size - 1)
    }
}