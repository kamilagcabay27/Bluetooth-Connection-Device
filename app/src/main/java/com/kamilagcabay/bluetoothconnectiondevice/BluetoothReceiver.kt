package com.kamilagcabay.bluetoothconnectiondevice

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat

class BluetoothReceiver(private val deviceAdapter: BluetoothDeviceAdapter) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == BluetoothDevice.ACTION_ACL_CONNECTED) {
            val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
            device?.let {
                val deviceName = if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                    it.name ?: "Unknown Device"
                } else {
                    "Unknown Device"
                }
                // Bildirimi gönder
                sendNotification(context, "Bluetooth Device Connected", "Connected to $deviceName")
                // Cihaz adını listeye ekle
                deviceAdapter.addDevice(deviceName)
            }
        }
    }

    private fun sendNotification(context: Context?, title: String, message: String) {
        val channelId = "bluetooth_channel"
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Bluetooth Notifications", NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = "Channel for Bluetooth notifications"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context!!, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.baseline_add_circle_24) // Uygun bir ikon ekleyin
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(1, notification)
    }
}
