import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.minimumInteractiveComponentSize
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
                    Row(
                      modifier = Modifier.weight(1f)
                    ) {
                        for (j in 0..2) {
                            var text by remember { mutableStateOf("")   }
                            Button(
                                onClick = {
                                    if (text == "" && winner == Tile.Empty) {
                                        text = if (counter % 2 == 0) {
                                            gameState[i][j] = Tile.X
                                            "X"
                                        }
                                        else {
                                            gameState[i][j] = Tile.O
                                            "O"
                                        }
                                        counter++

                                        for (i in 0 until 3) {
                                            if (winner == Tile.Empty) {
                                                if (gameState[i][0] == gameState[i][1] && gameState[i][1] == gameState[i][2]) {
                                                    winner = gameState[i][0]
                                                }
                                            }
                                        }

                                        for (i in 0 until 3) {
                                            if (winner == Tile.Empty) {
                                                if (gameState[0][i] == gameState[1][i] && gameState[1][i] == gameState[2][i]) {
                                                    winner = gameState[0][i]
                                                }
                                            }
                                        }

                                        if (winner == Tile.Empty) {
                                            if (gameState[0][0] == gameState[1][1] && gameState[1][1] == gameState[2][2]) {
                                                winner = gameState[0][0]
                                            }
                                        }

                                        if (winner == Tile.Empty) {
                                            if (gameState[0][2] == gameState[1][1] && gameState[1][1] == gameState[2][0]) {
                                                winner = gameState[0][2]
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .padding(Dp(3f))) {
                                Text(
                                    text,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .wrapContentSize(Alignment.Center)
                                )
                            }
                        }
                    }
                }
            }
            if (winner != Tile.Empty || (winner == Tile.Empty && counter == 9)) {
                Box(
                    modifier = Modifier
                        .height(Dp(50f))
                        .width(Dp(150f))
                        .border(BorderStroke(Dp(2f), Color.Black))
                        .background(Color(85, 12, 230, 255)),
                    contentAlignment = Alignment.Center
                ) {
                    val winMessage = when {
                        winner == Tile.Empty && counter == 9 -> "Draw!"
                        winner != Tile.Empty -> "Winner: ${winner}"
                        else -> ""
                    }
                    if (winMessage.isNotEmpty()) {
                        Text(
                            text = winMessage,
                            style = MaterialTheme.typography.h6
                        )
                    }
                }
            }
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Tic Tac Toe",
        state = rememberWindowState(width = Dp(600f), height = Dp(600f)),
        icon = null
    ) {

        App()
    }
}
