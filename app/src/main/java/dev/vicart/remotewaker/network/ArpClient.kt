package dev.vicart.remotewaker.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.InetAddress
import java.net.NetworkInterface
import java.nio.charset.Charset

class ArpClient {

    fun getMacFromIp(ip: String, context: Context) : String? {
        ping(ip)
        val addresses = readTable(context, ip)

        Log.d("addresses", addresses.joinToString())

        return addresses.firstOrNull {
            val splitted = it.split(' ')
            splitted[0] == ip
        }?.substringAfter("lladdr ")?.split(' ')?.get(0)
    }

    private fun ping(ip: String) {
        ProcessBuilder().command("ping", "-c", "1", ip).start()
    }

    private fun readTable(context: Context, ip: String): List<String> {
        return listOf(NetworkInterface.getByName(ip).hardwareAddress.contentToString())
    }
}