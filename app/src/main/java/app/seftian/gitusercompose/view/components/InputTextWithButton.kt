package app.seftian.gitusercompose.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTextWithButton(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (value: String) -> Unit,
    onClick: () -> Unit,
    isEnabled: Boolean,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(3F),
            value = text,
            label = { "username" },
            onValueChange = { value ->
                onTextChange(value)
            },
            singleLine = true,
            enabled = isEnabled
        )

        Button(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1F),
            onClick = { onClick() },
            shape = RoundedCornerShape(4.dp),
            enabled = isEnabled && text.isNotBlank()
        ) {
            Text("Cari")
        }
    }
}
