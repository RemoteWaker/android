package dev.vicart.remotewaker.viewmodels

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.google.android.material.textfield.TextInputLayout
import dev.vicart.remotewaker.R
import dev.vicart.remotewaker.repositories.UsersRepository
import io.ktor.client.network.sockets.*
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class LoginViewModel : ViewModel() {

    val username = MutableLiveData("")
    val password = MutableLiveData("")
    val loginIncorrect = MutableLiveData(false)
    val isLogging = MutableLiveData(false)

    fun login(view: View) {
        isLogging.value = true
        viewModelScope.launch {
            try {
                UsersRepository.loginWithPassword(username.value!!, password.value!!, view.context)
                view.findNavController().navigate(R.id.action_loginFragment_to_devicesFragment)
            }
            catch (se: SocketTimeoutException) {
                Toast.makeText(view.context, view.resources.getString(R.string.unable_to_connect_to_services), Toast.LENGTH_SHORT).show()
            }
            catch (e: Exception) {
                e.printStackTrace()
                loginIncorrect.value = true
            }
            finally {
                isLogging.value = false
            }
        }
    }

    fun registerRequested(view: View) {
        val usernameLayout: TextInputLayout = view.rootView.findViewById(R.id.login_username_layout)
        val passwordLayout: TextInputLayout = view.rootView.findViewById(R.id.login_password_layout)
        val image: ImageView = view.rootView.findViewById(R.id.login_image)
        val link: Button = view.rootView.findViewById(R.id.login_link)
        val btn: Button = view.rootView.findViewById(R.id.login_button)

        val extra = FragmentNavigatorExtras(usernameLayout to "username", passwordLayout to "password",
            image to "image", link to "link", btn to "btn")

        view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment, null, null, extra)
    }
}