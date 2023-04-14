package tg.dagno2.enebla.form_validator

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
