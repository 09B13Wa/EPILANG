package GUI

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.svgResource

class MainAppScreen {
    private var Unit: Unit

    public init {
        InitalizeCanvas()
    }

    public fun InitalizeCanvas() {
        Canvas(modifier = Modifier.fillMaxSize()) {
            painterResource("")
        }
    }
}