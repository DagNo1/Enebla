package tg.dagno2.enebla.form_validator

import android.util.Patterns

class Validator {
    companion object {
        fun username(username: String): ValidationResult {
            if(username.isBlank()) {
                return ValidationResult(
                    successful = false,
                    errorMessage = "The username can't be blank"
                )
            }
            val containsOther = username.any { !it.isLetterOrDigit() }
            if(containsOther) {
                return ValidationResult(
                    successful = false,
                    errorMessage = "The username can't contain spaces or special characters"
                )
            }
            return ValidationResult(
                successful = true
            )
        }
        fun email(email: String): ValidationResult {
            if(email.isBlank()) {
                return ValidationResult(
                    successful = false,
                    errorMessage = "The email can't be blank"
                )
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                return ValidationResult(
                    successful = false,
                    errorMessage = "That's not a valid email"
                )
            }
            return ValidationResult(
                successful = true
            )
        }
        fun password(password: String): ValidationResult {
            if(password.length < 8) {
                return ValidationResult(
                    successful = false,
                    errorMessage = "The password needs to consist of at least 8 characters"
                )
            }
            val containsLettersAndDigits = password.any { it.isDigit() } &&
                    password.any { it.isLetter() }
            if(!containsLettersAndDigits) {
                return ValidationResult(
                    successful = false,
                    errorMessage = "The password needs to contain at least one letter and digit"
                )
            }
            return ValidationResult(
                successful = true
            )
        }
        fun terms(acceptedTerms: Boolean): ValidationResult {
            if(!acceptedTerms) {
                return ValidationResult(
                    successful = false,
                    errorMessage = "Please accept the terms"
                )
            }
            return ValidationResult(
                successful = true
            )
        }
    }
}