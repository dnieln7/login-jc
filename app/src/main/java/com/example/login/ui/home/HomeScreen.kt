package com.example.login.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeBody() {
    Box(contentAlignment = Alignment.Center) {
        Text(text = "Welcome To Home")
    }
}

@Preview
@Composable
fun PreviewHomeBody() {

}