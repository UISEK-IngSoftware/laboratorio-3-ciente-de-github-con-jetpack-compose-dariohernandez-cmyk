package ec.edu.uisek.githubclient.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ec.edu.uisek.githubclient.models.Repository
import ec.edu.uisek.githubclient.models.GithubUser

@Composable
fun RepoItem(
    repository: Repository,
    onEdit: (Repository) -> Unit = {},
    onDelete: (Repository) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = repository.owner.avatarUrl,
                    contentDescription = "Imagen de ${repository.name}",
                    modifier = Modifier.size(60.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = repository.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    repository.description?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 3
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    repository.language?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botones de acción (editar y eliminar)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onEdit(repository) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar"
                    )
                }
                IconButton(onClick = { onDelete(repository) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RepoItemPreview() {
    val repository = Repository(
        id = "1",
        name = "Repo Name",
        owner = GithubUser(
            id = "1",
            name = "Owner Name",
            avatarUrl = "https://avatars.githubusercontent.com/u/168299135?v=4"
        ),
        description = "Repo Description",
        language = "Kotlin"
    )

    RepoItem(
        repository = repository,
        onEdit = { /* Acción de prueba */ },
        onDelete = { /* Acción de prueba */ }
    )
}
