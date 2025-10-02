package edu.ucne.erick_estrada_ap2_p1.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Huacales") },
                navigationIcon = {
                    IconButton(onClick = {
                    }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú")
                    }
                }
            )
        }
    ) { paddingValues ->
        HostNavigation(
            navHostController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}
