package ec.edu.uisek.githubclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.uisek.githubclient.ui.screens.RepoList
import ec.edu.uisek.githubclient.ui.theme.GithubClientTheme
import ec.edu.uisek.githubclient.viewmodels.RepoListViewModel
import ec.edu.uisek.githubclient.ui.screens.RepoForm

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubClientTheme {
                val listViewModel: RepoListViewModel = viewModel()
                var currentScreen by remember { mutableStateOf("repoList") }

                when (currentScreen) {
                    "repoList" -> RepoList(
                        onNavigateToForm = { currentScreen = "repoForm" },
                        // Paso 6: conectar botones de edición y eliminación
                        viewModel = listViewModel
                    )

                    "repoForm" -> RepoForm(
                        onBackClick = { currentScreen = "repoList" },
                        onSaveSuccess = {
                            listViewModel.fetchRepos() // refresca la lista
                            currentScreen = "repoList" // regresa a la lista
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RepoListPreview() {
    GithubClientTheme {
        RepoList()
    }
}
