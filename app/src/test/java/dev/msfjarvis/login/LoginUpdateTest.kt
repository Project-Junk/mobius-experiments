package dev.msfjarvis.login

import com.spotify.mobius.test.NextMatchers.*
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class LoginUpdateTest {

    private val initialModel = LoginModel.default()
    private val updateSpec = UpdateSpec(LoginUpdate())

    @Test
    fun `when login button is clicked, then validate credentials`() {
        updateSpec
            .given(initialModel)
            .whenEvent(LoginEvent.LoginButtonClicked)
            .then(assertThatNext(
                hasModel(initialModel.loginInProgress()),
                hasEffects(LoginEffects.ValidateCredentials),
            ))
    }

    @Test
    fun `when credential validation fails, then show errors`() {
        val validationErrors = listOf(ValidationError.InvalidPassword)
        updateSpec
            .given(initialModel)
            .whenEvent(LoginEvent.ValidationFailure(validationErrors))
            .then(assertThatNext(
                hasModel(initialModel.validationFailed(validationErrors)),
                hasNoEffects(),
            ))
    }
}
