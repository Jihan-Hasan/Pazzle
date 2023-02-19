package com.example.pazzle.layer

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pazzle.R
import com.example.pazzle.ui.theme.PazzleTheme



@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = viewModel()
) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    if (gameUiState.isGameOver){
        FinalScoreDialog(onPlayAgain = {gameViewModel.resetGame()}, score = gameUiState.score)
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        GameStatus(
            wordCount = gameUiState.currentWordCount,
            Score = gameUiState.score
        )
        GameLayout(
            isGuessWrong = gameUiState.isGuessedWordWrong,
            onKeyboardDone = {gameViewModel.checkUserGuess()},
            userGuess =gameViewModel.userGuess,
            GuessChanged = {gameViewModel.updateUserGuess(it)},
            currentWord = gameUiState.currentScrambledWord
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OutlinedButton(
                onClick = {gameViewModel.skiptheWord() },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(text = stringResource(id = R.string.skip))
            }
            Button(
                onClick = { gameViewModel.checkUserGuess() },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(text = stringResource(id = R.string.submit))
            }
        }

    }
}


// Game Layout Part -- >

@Composable
fun GameLayout(
    modifier: Modifier = Modifier,
    isGuessWrong: Boolean,
    onKeyboardDone: () -> Unit,
    userGuess: String,
    GuessChanged: (String) -> Unit,
    currentWord: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = currentWord,
            fontSize = 45.sp,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(id = R.string.instructions),
            fontSize = 17.sp,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
        OutlinedTextField(
            value = userGuess,
            onValueChange = GuessChanged,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                if (isGuessWrong) {
                    Text(text = stringResource(id = R.string.wrong_guess))
                } else {
                    Text(text = stringResource(id = R.string.enter_your_word))
                }
            },
            isError = isGuessWrong,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { onKeyboardDone() })
        )
    }
}

// Game Status Part -- >

@Composable
fun GameStatus(
    modifier: Modifier = Modifier,
    wordCount: Int,
    Score: Int
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .size(48.dp)
    ) {
        Text(
            text = stringResource(id = R.string.word_count, wordCount),
            fontSize = 18.sp
        )
        Text(
            text = stringResource(id = R.string.score, Score),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            fontSize = 18.sp
        )
    }
}

//Game Final result -- >
@Composable
fun FinalScoreDialog(

    modifier: Modifier = Modifier,
    onPlayAgain : () -> Unit,
    score : Int
) {
    val activity = (LocalContext.current as Activity)

    AlertDialog(
        onDismissRequest = {},
        title = { Text(stringResource(R.string.congratulations)) },
        text = { Text(stringResource(R.string.you_scored, score)) },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    activity.finish()
                }
            ) {
                Text(text = stringResource(R.string.exit))
            }
        },
        confirmButton = {
            TextButton(
                onClick = onPlayAgain
            ) {
                Text(text = stringResource(R.string.play_again))
            }
        }
        )
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreiview() {
    PazzleTheme {
        GameScreen()
    }
}