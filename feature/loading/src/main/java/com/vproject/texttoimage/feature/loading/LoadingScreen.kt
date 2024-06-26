package com.vproject.texttoimage.feature.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
internal fun LoadingRoute(
    modifier: Modifier = Modifier,
    viewModel: LoadingViewModel = hiltViewModel(),
    onImageGenerated: (url: String, prompt: String, styleId: String) -> Unit = {_,_,_ ->},
    onError: (message: String) -> Unit = {}
) {
    val loadingUiState by viewModel.loadingUiState.collectAsStateWithLifecycle()
    LoadingScreen(
        loadingUiState = loadingUiState,
        modifier = modifier.fillMaxSize(),
        onImageGenerated = onImageGenerated,
        onError = onError
    )
}

@Composable
internal fun LoadingScreen(
    modifier: Modifier = Modifier,
    loadingUiState: LoadingUiState,
    onImageGenerated: (url: String, prompt: String, styleId: String) -> Unit = {_,_,_ ->},
    onError: (message: String) -> Unit = {}
) {
    if (loadingUiState is LoadingUiState.Generating) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                modifier = Modifier.size(160.dp),
                composition = composition, iterations = LottieConstants.IterateForever
            )
            Text(text = "Generating...",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                ),)
        }
    } else if (loadingUiState is LoadingUiState.Generated) {
        onImageGenerated(loadingUiState.url, loadingUiState.prompt, loadingUiState.styleId)
    } else if (loadingUiState is LoadingUiState.Error) {
        onError("Some unknown error just happen")
    }
}

@Preview
@Composable
private fun LoadingScreenPreview() {
    LoadingScreen(loadingUiState = LoadingUiState.Generating)
}