package presentation.home

interface HomeInteractionListener {

    fun search()

    fun onDropDownMenuExpand(expand: Boolean)

    fun onSearchCitySelected(city: String)

    fun getData()

    fun onSearchExpand(state: Boolean)

    fun onSearchTermChanged(term: String)

}
