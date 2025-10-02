package edu.ucne.erick_estrada_ap2_p1.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.erick_estrada_ap2_p1.presentation.HuacalListScreen
import edu.ucne.erick_estrada_ap2_p1.presentation.HuacalScreen

@Composable
fun HostNavigation(
    navHostController: NavHostController,
    modifier : Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.List,
        modifier = modifier
    ) {
        composable<Screen.List> {
            HuacalListScreen(
                goToHuacales = { id ->
                    navHostController.navigate(Screen.Huacal(id))
                },
                createHuacal = {
                    navHostController.navigate(Screen.Huacal(null))
                }
            )
        }


        composable<Screen.Huacal> { backStack ->
            val idEntrada = backStack.toRoute<Screen.Huacal>().Id
            HuacalScreen (
                IdEntrada = idEntrada ?: 0,
                goback = {navHostController.popBackStack()}
            )

        }

    }
}