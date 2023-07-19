package ui.composables

import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun IconSearch(
    modifier: Modifier = Modifier,
    suggestion: List<String>,
    onSearch: (String) -> Unit,
    onDropDownMenuExpand: (Boolean) -> Unit,
    onSearchCitySelected: (String) -> Unit,
    isExpandMenuSuggestion: Boolean = false,
    keyword: String
) {

    var expanded by remember { mutableStateOf(false) }
    val transition: Transition<State> = updateTransition(if (expanded) State.EXPANDED else State.COLLAPSED)
    val searchIconRotation: Float by transition.animateFloat {
        if (it == State.EXPANDED) 45f else 0f
    }
    val boxWidth: Dp by transition.animateDp {
        if (it == State.EXPANDED) 342.dp else 52.dp
    }
    val boxHeight: Dp by transition.animateDp {
        if (it == State.EXPANDED) 52.dp else 52.dp
    }
    val searchFieldAlpha: Float by transition.animateFloat {
        if (it == State.EXPANDED) 1f else 0f
    }
    val searchIconAlpha: Float by transition.animateFloat {
        if (it == State.EXPANDED) 0f else 1f
    }

    Box(
        modifier
            .width(boxWidth)
            .height(boxHeight)
            .onPointerEvent(PointerEventType.Enter) { expanded = true }
            .onPointerEvent(PointerEventType.Exit) { expanded = keyword.isNotBlank() }
            .background(Color(0xCC1B232A), RoundedCornerShape(24.dp))
    ) {

        Icon(
            painter = painterResource("search.svg"),
            contentDescription = "Search",
            modifier = Modifier
                .onClick { expanded = true }
                .size(24.dp)
                .offset(0.dp)
                .rotate(searchIconRotation)
                .align(Alignment.Center)
                .alpha(searchIconAlpha)
        )

        TextField(
            value = keyword,
            onValueChange = {
                onSearch(it)
            },
            placeholder = { Text("Search City", color = Color(0x5CFFFFFF)) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.Gray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                textColor = Color.Gray
            ),
            modifier = Modifier.alpha(searchFieldAlpha).background(Color.Transparent),
            leadingIcon = {
                Icon(painterResource("search.svg"), contentDescription = null, tint = Color(0x5CFFFFFF))
            },
            maxLines = 1,
        )

        DropdownMenu(
            modifier = Modifier.align(Alignment.CenterEnd),
            expanded = isExpandMenuSuggestion,
            focusable = false,
            onDismissRequest = { }
        ) {
            suggestion.forEachIndexed { index, item ->
                DropdownMenuItem(
                    onClick = {
                        onSearchCitySelected(suggestion[index])
                        onDropDownMenuExpand(false)
                    }
                ) {
                    Text(item)
                }
            }
        }
    }
}


enum class State {
    COLLAPSED, EXPANDED
}