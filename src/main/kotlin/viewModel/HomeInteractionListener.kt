package viewModel

interface HomeInteractionListener {
    fun search(keyword: String)
    fun onDropDownMenuExpand(expand: Boolean)
    fun onSearchCitySelected(city: String)
    fun getData()

    fun onSearchExpand(state: Boolean)
}
