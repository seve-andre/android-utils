package com.mitch.androidutils.utils.autofill

import android.view.LayoutInflater
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.mitch.androidutils.R

// needs AppCompatActivity, not ComponentActivity
@Composable
fun TextFieldWithAutofill(
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    @LayoutRes layoutRes: Int
) {
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            val layout = LayoutInflater.from(context).inflate(layoutRes, null)
            layout.findViewById<TextInputEditText>(R.id.tilET).apply {
                doAfterTextChanged {
                    onTextChanged(safeText)
                }
            }
            layout
        },
        update = {

        }
    )
}

private val EditText?.safeText: String get() = this?.editableText?.toString().orEmpty().trim()
