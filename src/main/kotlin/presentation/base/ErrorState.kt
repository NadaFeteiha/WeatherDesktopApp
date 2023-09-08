package presentation.base

sealed interface ErrorState {

    object UnKnownError : ErrorState

}