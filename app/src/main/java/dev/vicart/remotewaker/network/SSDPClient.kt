package dev.vicart.remotewaker.network

import android.content.Context
import android.net.wifi.WifiManager
import android.util.Log
import androidx.core.content.getSystemService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.lang.Byte
import java.net.*

class SSDPClient(private val context: Context) {

    private val HOST_PORT = "239.255.255.250:1900"
    private val group = InetAddress.getByName(HOST_PORT.split(':')[0])
    private val port = HOST_PORT.split(':')[1].toInt()

    suspend fun searchDevices(serviceType: String = "ssdp:all") : Flow<SSDPResponse> {

        val wifi = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if(wifi != null) {
            val lock = wifi.createMulticastLock("SSDP Lock")
            lock.acquire()

            val query = "M-SEARCH * HTTP/1.1\r\n" +
                    "HOST: $HOST_PORT\r\n" +
                    "MAN: \"ssdp:discover\"\r\n" +
                    "MX: 1\r\n" +
                    "ST: $serviceType\r\n" +
                    "\r\n"

            val socket = MulticastSocket(port)
            socket.reuseAddress = true
            socket.joinGroup(group)

            val dgram = DatagramPacket(query.encodeToByteArray(), query.length, group, port)
            socket.send(dgram)

            val time = System.currentTimeMillis()
            var curTime = System.currentTimeMillis()

            return flow {
                while(curTime - time < 1000) {
                    val p = DatagramPacket(ByteArray(2048), 2048)
                    socket.receive(p)

                    val s = String(p.data, 0, p.length)
                    val types = s.lines()
                    if(types[0].startsWith("NOTIFY")) {
                        val map = types.subList(1, types.size-2).associate {
                            val split = it.split(':', limit = 2)
                            split[0].trim() to if(split.size > 1) split[1] else ""
                        }

                        val response = SSDPResponse(types[0], map, p.address.hostAddress ?: "")
                        emit(response)
                    }

                    curTime = System.currentTimeMillis()
                }
                socket.close()
                lock.release()
            }
        }
        return emptyFlow()
    }

    class SSDPResponse(val status: String, val data: Map<String, String>, val remoteAddr: String) {

        val st = data["ST"]
        val usn = data["USN"]
        val location = data["LOCATION"]
        val isSetUp = data["SETUP"]?.toBoolean() ?: false

        override fun toString(): String {
            return "SSDPResponse { status=$status, data: ${data.map { 
                "${it.key}: ${it.value}"
            }} }"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as SSDPResponse

            if (st != other.st) return false
            if (usn != other.usn) return false

            return true
        }

        override fun hashCode(): Int {
            var result = st?.hashCode() ?: 0
            result = 31 * result + (usn?.hashCode() ?: 0)
            return result
        }
    }
}