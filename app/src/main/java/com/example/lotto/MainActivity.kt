package com.example.lotto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.lotto.ui.theme.LottoTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LottoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Lotto()
                }
            }
        }
    }
}

@Composable
fun Lotto() {
    var lottoLines by remember { mutableStateOf(listOf<List<Pair<Int, Color>>>()) }
    var rotation by remember { mutableStateOf(0f) }

    val animationRotation by animateFloatAsState(
        targetValue = rotation,
        animationSpec = tween(600)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        lottoLines.forEach { lottoNumbers ->
            LottoNumbers(lottoNumbers = lottoNumbers, rotation = animationRotation)
            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(
            onClick = {
                rotation += 720
                lottoLines = List(1) { generateLottoNumbers().map { it to generateRandomColor() } }
            },
            shape = RoundedCornerShape(10.dp),
        ) {
            Text(text = "천원", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                rotation += 720
                lottoLines = List(5) { generateLottoNumbers().map { it to generateRandomColor() } }
            },
            shape = RoundedCornerShape(10.dp),
        ) {
            Text(text = "5천원", color = Color.White)
        }

    }
}

@Composable
fun LottoNumbers(lottoNumbers: List<Pair<Int, Color>>, rotation: Float) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.2f))
            .padding(16.dp)
            .border(1.dp, Color.Black, MaterialTheme.shapes.medium)
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        lottoNumbers.forEach { (number, color) ->
            LottoBall(number = number, rotation = rotation, color = color)
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
fun LottoBall(number: Int, rotation: Float, color: Color) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color)
            .graphicsLayer {
                rotationY = rotation
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            color = Color.White,
            modifier = Modifier.graphicsLayer {
                rotationY = rotation
            },
        )
    }
}

fun generateLottoNumbers(): List<Int> {
    val lottoNumbers = mutableListOf<Int>()
    while (lottoNumbers.size < 6) {
        val number = Random.nextInt(1, 46)
        if (number !in lottoNumbers) {
            lottoNumbers.add(number)
        }
    }
    return lottoNumbers.sorted()
}

fun generateRandomColor(): Color {
    return Color(
        red = Random.nextFloat(),
        green = Random.nextFloat(),
        blue = Random.nextFloat()
    )
}


//버튼 자체가 360돌아감

//Button(
//onClick = {
//    rotation += 360
//    lottoNumbers = generateLottoNumbers()
//},
//modifier = Modifier.rotate(animationRotation)
//) {
//    Text(text = "로또 번호 생성")
//}


//버튼안에 Row로 감싼 Text가 돌아감

//Button(
//onClick = {
//    rotation += 360
//    lottoNumbers = generateLottoNumbers()
//}
//) {
//    Row(
//        Modifier.rotate(animationRotation),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(text = "로또 번호 생성")
//    }
//}


//수평 방향으로 회전

//fun LottoBall(number: Int, rotation: Float) {
//    Box(
//        modifier = Modifier
//            .size(40.dp)
//            .clip(CircleShape)
//            .background(Color.Blue)
//            .graphicsLayer {
//                rotationY = rotation
//            },
//        contentAlignment = Alignment.Center
//    ) {
//        Text(
//            text = number.toString(),
//            color = Color.White,
//        )
//    }
//}

//시계 방향으로 회전

//fun LottoBall(number: Int, rotation: Float) {
//    Box(
//        modifier = Modifier
//            .size(40.dp)
//            .clip(CircleShape)
//            .background(Color.Blue)
//            .rotate(rotation),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(
//            text = number.toString(),
//            color = Color.White,
//        )
//    }
//}