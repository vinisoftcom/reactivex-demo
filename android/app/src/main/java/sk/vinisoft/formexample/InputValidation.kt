package sk.vinisoft.formexample

data class InputValidation(
    val inputText: String,
    val isValid: Boolean = false,
    val isAvailable: Boolean = false
)