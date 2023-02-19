package com.example.pazzle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pazzle.layer.FinalScoreDialog
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
