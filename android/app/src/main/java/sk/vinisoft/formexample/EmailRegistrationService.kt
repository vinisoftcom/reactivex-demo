package sk.vinisoft.formexample

open class EmailRegistrationService {

    fun isEmailFree(email: String): Boolean {
        return !USED_EMAILS.contains(email)
    }

    companion object {
        val USED_EMAILS = listOf("lenkojan@gmail.com", "info@vinisoft.com")
    }
}