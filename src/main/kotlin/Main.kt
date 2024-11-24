import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

enum class Tile {
    X, O, Empty
}

@Composable
@Preview
fun App() {
    var counter by remember { mutableStateOf(0) }
    var gameState by remember { mutableStateOf(Array(3) { Array(3) { Tile.Empty } }) }
    var winner by remember { mutableStateOf(Tile.Empty) }
    var winnerState by remember { mutableStateOf(false) }

    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.size(Dp(500f)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                for (i in 0..2) {
                    Row {
                        for (j in 0..2) {
                            Button(
                                onClick = {
                                    if (gameState[i][j] == Tile.Empty && winner == Tile.Empty) {
                                        gameState = gameState.clone().apply {
                                            this[i][j] = if (counter % 2 == 0) Tile.X else Tile.O
                                        }
                                        counter++
                                        winner = checkWinner(gameState)
                                    }
                                },
                                modifier = Modifier
                                    .size(Dp(165f))
                                    .padding(Dp(3f))
                            ) {
                                Text(
                                    text = when (gameState[i][j]) {
                                        Tile.X -> "X"
                                        Tile.O -> "O"
                                        else -> ""
                                    }
                                )
                            }
                        }
                    }
                }
            }
            if (winner != Tile.Empty || (winner == Tile.Empty && counter == 9)) {
                Box(
                    modifier = Modifier
                        .height(Dp(100f))
                        .width(Dp(150f))
                        .border(BorderStroke(Dp(2f), Color.Black))
                        .background(Color(185, 12, 230, 255)),
                    contentAlignment = Alignment.Center
                ) {
                    var winMessage = when {
                        winner == Tile.Empty && counter == 9 -> "Draw!"
                        winner != Tile.Empty -> "Winner: ${winner}"
                        else -> ""
                    }
                    if (winMessage.isNotEmpty()) {
                        winnerState = true;
                        Column {
                            Text(
                                text = winMessage,
                                style = MaterialTheme.typography.h6
                            )
                            Button(
                                onClick = {
                                    winMessage = ""
                                    winner = Tile.Empty
                                    counter = 0
                                    gameState = Array(3) { Array(3) { Tile.Empty } }
                                },
                                modifier = Modifier.padding(start = Dp(8f))
                            ) {
                                Text(text = "RESTART")
                            }
                        }
                    }
                }
            }
        }
    }
}

fun checkWinner(gameState: Array<Array<Tile>>): Tile {
    // Check rows
    for (i in 0 until 3) {
        if (gameState[i][0] != Tile.Empty &&
            gameState[i][0] == gameState[i][1] &&
            gameState[i][1] == gameState[i][2]
        ) {
            return gameState[i][0]
        }
    }

    // Check columns
    for (i in 0 until 3) {
        if (gameState[0][i] != Tile.Empty &&
            gameState[0][i] == gameState[1][i] &&
            gameState[1][i] == gameState[2][i]
        ) {
            return gameState[0][i]
        }
    }

    // Check diagonals
    if (gameState[0][0] != Tile.Empty &&
        gameState[0][0] == gameState[1][1] &&
        gameState[1][1] == gameState[2][2]
    ) {
        return gameState[0][0]
    }

    if (gameState[0][2] != Tile.Empty &&
        gameState[0][2] == gameState[1][1] &&
        gameState[1][1] == gameState[2][0]
    ) {
        return gameState[0][2]
    }

    return Tile.Empty
}


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Tic Tac Toe",
        state = rememberWindowState(width = Dp(600f), height = Dp(600f)),
        icon = null,
        resizable = false
    ) {

        App()
    }
}
