package ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import viewModel.HomeInteractionListener


@Composable
fun SearchCard(
    modifier: Modifier = Modifier,
    date: String,
    cityName: String,
    temperature: String,
    countryName: String,
    icon: String,
    keyword: String,
    suggestion: List<String>,
    isExpandMenuSuggestion: Boolean = false,
    isSearchExpanded: Boolean = false,
    listener: HomeInteractionListener,
) {

    Column(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        IconSearch(
            modifier = Modifier.align(Alignment.End),
            suggestion = suggestion,
            onSearch = listener::search,
            isExpandMenuSuggestion = isExpandMenuSuggestion,
            onDropDownMenuExpand = listener::onDropDownMenuExpand,
            onSearchCitySelected = listener::onSearchCitySelected,
            keyword = keyword,
            isSearchExpanded = isSearchExpanded,
            onSearchExpand = listener::onSearchExpand
        )

        WeatherImageLoader(modifier = Modifier.size(110.dp).padding(top = 16.dp), url = icon)

        Row(modifier = Modifier.padding(top = 8.dp)) {
            Text(temperature, style = MaterialTheme.typography.h3)
            Text("ºC", style = MaterialTheme.typography.h4)
        }

        TextWithIcon(
            icon = painterResource("location.svg"),
            text = "$cityName, $countryName",
            modifier = Modifier.align(Alignment.Start).padding(top = 16.dp)
        )

        TextWithIcon(
            icon = painterResource("calender.svg"),
            text = date,
            modifier = Modifier.align(Alignment.Start).padding(top = 16.dp)
        )
    }
}
