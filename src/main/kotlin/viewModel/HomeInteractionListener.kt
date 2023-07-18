package viewModel

interface HomeInteractionListener {
    fun getData()
    fun search(keyword: String)

    fun onDropDownMenuExpand(expand: Boolean)
}
