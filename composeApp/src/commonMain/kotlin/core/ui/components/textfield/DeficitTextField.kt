package core.ui.components.textfield

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun DeficitTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    placeholder: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = false,
    onDone: (KeyboardActionScope.() -> Unit)? = null,
) {
    val colors = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
    )

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = label,
            style = MaterialTheme.typography.titleMedium,
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(size = 20.dp))
                .clip(shape = RoundedCornerShape(size = 20.dp)),
            value = value,
            shape = RoundedCornerShape(size = 20.dp),
            textStyle = MaterialTheme.typography.bodyMedium,
            colors = colors,
            singleLine = singleLine,
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                )
            },
            trailingIcon = trailingContent,
            leadingIcon = leadingContent,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(onDone = onDone),
            onValueChange = onValueChange,
        )
    }
}
