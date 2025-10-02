package edu.ucne.erick_estrada_ap2_p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.erick_estrada_ap2_p1.navigation.MainScreen
import edu.ucne.erick_estrada_ap2_p1.ui.theme.Erick_Estrada_AP2_P1Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Erick_Estrada_AP2_P1Theme {
                val navController = rememberNavController()

                MainScreen(navController)
            }
        }
    }
}

