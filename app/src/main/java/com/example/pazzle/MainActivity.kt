package com.example.pazzle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.pazzle.layer.GameScreen
import com.example.pazzle.ui.theme.PazzleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PazzleTheme {
                // A surface container using the 'background' color from the theme
                GameScreen()
                // This is edited from Github
                // This is complete
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PazzleTheme {

    }
}
