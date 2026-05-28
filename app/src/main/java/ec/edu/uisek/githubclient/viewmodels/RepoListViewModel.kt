package ec.edu.uisek.githubclient.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.uisek.githubclient.models.Repository
import ec.edu.uisek.githubclient.models.RepositoryPayload
import ec.edu.uisek.githubclient.services.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepoListViewModel : ViewModel() {

    private val _repos = MutableStateFlow<List<Repository>>(emptyList())
    val repos: StateFlow<List<Repository>> = _repos.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg: StateFlow<String?> = _errorMsg.asStateFlow()

    init {
        fetchRepos()
    }

    fun fetchRepos() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMsg.value = null
            try {
                _repos.value = RetrofitClient.apiService.getRepositories()
            } catch (e: Exception) {
                _errorMsg.value = "Error al cargar repositorios: ${e.localizedMessage}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Paso 4: Eliminar repositorio
    fun deleteRepo(owner: String, repo: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                RetrofitClient.apiService.deleteRepository(owner, repo)
                fetchRepos() // refresca la lista después de eliminar
            } catch (e: Exception) {
                _errorMsg.value = "Error al eliminar repositorio: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Paso 4: Editar repositorio
    fun updateRepo(owner: String, repo: String, name: String, description: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val payload = RepositoryPayload(name, description)
                RetrofitClient.apiService.updateRepository(owner, repo, payload)
                fetchRepos() // refresca la lista después de editar
            } catch (e: Exception) {
                _errorMsg.value = "Error al actualizar repositorio: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
