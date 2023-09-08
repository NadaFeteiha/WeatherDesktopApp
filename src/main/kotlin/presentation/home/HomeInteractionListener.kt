package presentation.home

import presentation.base.BaseInteractionListener

interface HomeInteractionListener : BaseInteractionListener {

    fun search()

    fun onDropDownMenuExpand(expand: Boolean)

    fun onSearchCitySelected(city: String)

    fun getData()

    fun onSearchExpand(state: Boolean)

    fun onSearchTermChanged(term: String)

}
