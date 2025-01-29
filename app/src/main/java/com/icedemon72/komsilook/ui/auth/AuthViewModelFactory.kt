import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.icedemon72.komsilook.data.repositories.AuthRepository
import com.icedemon72.komsilook.ui.auth.AuthViewModel

class AuthViewModelFactory(private val authRepository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
