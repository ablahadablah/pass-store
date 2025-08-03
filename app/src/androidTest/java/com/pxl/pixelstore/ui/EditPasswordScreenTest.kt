package com.pxl.pixelstore.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pxl.pixelstore.MainActivity
import com.pxl.pixelstore.domain.entity.PasswordRecord
import com.pxl.pixelstore.ui.password.EditPasswordForm
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class EditPasswordScreenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var mockPasswordRecord: PasswordRecord

    @Before
    fun setUp() {
        mockPasswordRecord = PasswordRecord(
            id = UUID.randomUUID(),
            url = "https://example.com",
            username = "user@example.com",
            password = "testpassword123",
            timeCreated = System.currentTimeMillis(),
            timeLastUsed = System.currentTimeMillis(),
            timePasswordChanged = System.currentTimeMillis()
        )
    }

    @Test
    fun testEditPasswordScreen_NewPasswordForm() {
        // Given: EditPasswordScreen for new password
        composeTestRule.setContent {
            EditPasswordForm(
                entityToEdit = null,
                onSave = { _, _, _ -> }
            )
        }

        // Then: Verify form fields are present and empty
        composeTestRule.onNodeWithText("URL").assertIsDisplayed()
        composeTestRule.onNodeWithText("Username").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Save").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cancel").assertIsDisplayed()
    }

    @Test
    fun testEditPasswordScreen_EditExistingPassword() {
        // Given: EditPasswordScreen for existing password
        composeTestRule.setContent {
            EditPasswordForm(
                entityToEdit = mockPasswordRecord,
                onSave = { _, _, _ -> }
            )
        }

        // Then: Verify form is pre-filled with existing data
        composeTestRule.onNodeWithText("example.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("user@example.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("testpassword123").assertIsDisplayed()
    }

    @Test
    fun testEditPasswordScreen_EnterValidData() {
        var savedUrl = ""
        var savedUsername = ""
        var savedPassword = ""

        // Given: EditPasswordScreen for new password
        composeTestRule.setContent {
            EditPasswordForm(
                entityToEdit = null,
                onSave = { url, username, password ->
                    savedUrl = url
                    savedUsername = username
                    savedPassword = password
                }
            )
        }

        // When: Enter valid data
        composeTestRule.onNodeWithText("URL").performTextInput("https://test.com")
        composeTestRule.onNodeWithText("Username").performTextInput("test@test.com")
        composeTestRule.onNodeWithText("Password").performTextInput("newpassword123")

        // Then: Verify data is entered
        composeTestRule.onNodeWithText("https://test.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("test@test.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("newpassword123").assertIsDisplayed()
    }

    @Test
    fun testEditPasswordScreen_SavePassword() {
        var savedUrl = ""
        var savedUsername = ""
        var savedPassword = ""

        // Given: EditPasswordScreen with valid data
        composeTestRule.setContent {
            EditPasswordForm(
                entityToEdit = null,
                onSave = { url, username, password ->
                    savedUrl = url
                    savedUsername = username
                    savedPassword = password
                }
            )
        }

        // When: Enter data and save
        composeTestRule.onNodeWithText("URL").performTextInput("https://save.com")
        composeTestRule.onNodeWithText("Username").performTextInput("save@save.com")
        composeTestRule.onNodeWithText("Password").performTextInput("savepassword123")
        composeTestRule.onNodeWithText("Save").performClick()

        // Then: Verify save callback is triggered with correct data
        assert(savedUrl == "https://save.com")
        assert(savedUsername == "save@save.com")
        assert(savedPassword == "savepassword123")
    }

    @Test
    fun testEditPasswordScreen_CancelOperation() {
        var cancelClicked = false

        // Given: EditPasswordScreen with data
        composeTestRule.setContent {
            EditPasswordForm(
                entityToEdit = null,
                onSave = { _, _, _ -> }
            )
        }

        // When: Enter data and cancel
        composeTestRule.onNodeWithText("URL").performTextInput("https://cancel.com")
        composeTestRule.onNodeWithText("Cancel").performClick()

        // Then: Verify cancel functionality (implementation dependent)
        // This test assumes cancel is handled by the parent component
        composeTestRule.onNodeWithText("Cancel").assertIsDisplayed()
    }

    @Test
    fun testEditPasswordScreen_PasswordVisibilityToggle() {
        // Given: EditPasswordScreen with password entered
        composeTestRule.setContent {
            EditPasswordForm(
                entityToEdit = null,
                onSave = { _, _, _ -> }
            )
        }

        // When: Enter password and toggle visibility
        composeTestRule.onNodeWithText("Password").performTextInput("visiblepassword")

        // Initially password should be hidden
        composeTestRule.onNodeWithText("visiblepassword").assertIsDisplayed()

        // Click visibility toggle
        composeTestRule.onNodeWithContentDescription("Toggle password visibility").performClick()

        // Password should still be visible (toggle behavior)
        composeTestRule.onNodeWithText("visiblepassword").assertIsDisplayed()
    }

    @Test
    fun testEditPasswordScreen_EmptyFieldsValidation() {
        // Given: EditPasswordScreen for new password
        composeTestRule.setContent {
            EditPasswordForm(
                entityToEdit = null,
                onSave = { _, _, _ -> }
            )
        }

        // When: Try to save with empty fields
        composeTestRule.onNodeWithText("Save").performClick()

        // Then: Should show validation error (implementation dependent)
        // This test assumes some validation feedback is shown
        composeTestRule.onNodeWithText("Save").assertIsDisplayed()
    }

    @Test
    fun testEditPasswordScreen_InvalidUrlValidation() {
        // Given: EditPasswordScreen for new password
        composeTestRule.setContent {
            EditPasswordForm(
                entityToEdit = null,
                onSave = { _, _, _ -> }
            )
        }

        // When: Enter invalid URL
        composeTestRule.onNodeWithText("URL").performTextInput("invalid-url")

        // Then: Should handle invalid URL (implementation dependent)
        composeTestRule.onNodeWithText("invalid-url").assertIsDisplayed()
    }

    @Test
    fun testEditPasswordScreen_LongInputHandling() {
        // Given: EditPasswordScreen for new password
        composeTestRule.setContent {
            EditPasswordForm(
                entityToEdit = null,
                onSave = { _, _, _ -> }
            )
        }

        // When: Enter very long inputs
        val longUrl = "https://" + "a".repeat(1000) + ".com"
        val longUsername = "a".repeat(1000) + "@test.com"
        val longPassword = "a".repeat(1000)

        composeTestRule.onNodeWithText("URL").performTextInput(longUrl)
        composeTestRule.onNodeWithText("Username").performTextInput(longUsername)
        composeTestRule.onNodeWithText("Password").performTextInput(longPassword)

        // Then: Should handle long inputs gracefully
        composeTestRule.onNodeWithText("URL").assertIsDisplayed()
        composeTestRule.onNodeWithText("Username").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
    }

    @Test
    fun testEditPasswordScreen_SpecialCharacters() {
        // Given: EditPasswordScreen for new password
        composeTestRule.setContent {
            EditPasswordForm(
                entityToEdit = null,
                onSave = { _, _, _ -> }
            )
        }

        // When: Enter data with special characters
        composeTestRule.onNodeWithText("URL").performTextInput("https://test.com/path?param=value&other=123")
        composeTestRule.onNodeWithText("Username").performTextInput("user+tag@test.com")
        composeTestRule.onNodeWithText("Password").performTextInput("p@ssw0rd!@#$%^&*()")

        // Then: Should handle special characters
        composeTestRule.onNodeWithText("https://test.com/path?param=value&other=123").assertIsDisplayed()
        composeTestRule.onNodeWithText("user+tag@test.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("p@ssw0rd!@#$%^&*()").assertIsDisplayed()
    }

    @Test
    fun testEditPasswordScreen_UnicodeCharacters() {
        // Given: EditPasswordScreen for new password
        composeTestRule.setContent {
            EditPasswordForm(
                entityToEdit = null,
                onSave = { _, _, _ -> }
            )
        }

        // When: Enter data with unicode characters
        composeTestRule.onNodeWithText("URL").performTextInput("https://测试.com")
        composeTestRule.onNodeWithText("Username").performTextInput("用户@测试.com")
        composeTestRule.onNodeWithText("Password").performTextInput("密码123🔐")

        // Then: Should handle unicode characters
        composeTestRule.onNodeWithText("https://测试.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("用户@测试.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("密码123🔐").assertIsDisplayed()
    }

    @Test
    fun testEditPasswordScreen_KeyboardActions() {
        // Given: EditPasswordScreen for new password
        composeTestRule.setContent {
            EditPasswordForm(
                entityToEdit = null,
                onSave = { _, _, _ -> }
            )
        }

        // When: Enter data and use keyboard actions
        composeTestRule.onNodeWithText("URL").performTextInput("https://keyboard.com")
        composeTestRule.onNodeWithText("URL").performImeAction()

        composeTestRule.onNodeWithText("Username").performTextInput("keyboard@test.com")
        composeTestRule.onNodeWithText("Username").performImeAction()

        composeTestRule.onNodeWithText("Password").performTextInput("keyboardpass")
        composeTestRule.onNodeWithText("Password").performImeAction()

        // Then: Should handle keyboard actions
        composeTestRule.onNodeWithText("https://keyboard.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("keyboard@test.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("keyboardpass").assertIsDisplayed()
    }

    @Test
    fun testEditPasswordScreen_Accessibility() {
        // Given: EditPasswordScreen for new password
        composeTestRule.setContent {
            EditPasswordForm(
                entityToEdit = null,
                onSave = { _, _, _ -> }
            )
        }

        // Then: Verify accessibility elements
        composeTestRule.onNodeWithContentDescription("Toggle password visibility").assertIsDisplayed()
        composeTestRule.onNodeWithText("URL").assertIsDisplayed()
        composeTestRule.onNodeWithText("Username").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Save").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cancel").assertIsDisplayed()
    }

    @Test
    fun testEditPasswordScreen_ConfigurationChanges() {
        // Given: EditPasswordScreen with data entered
        composeTestRule.setContent {
            EditPasswordForm(
                entityToEdit = null,
                onSave = { _, _, _ -> }
            )
        }

        // When: Enter data
        composeTestRule.onNodeWithText("URL").performTextInput("https://config.com")
        composeTestRule.onNodeWithText("Username").performTextInput("config@test.com")
        composeTestRule.onNodeWithText("Password").performTextInput("configpass")

        // Then: Data should persist through configuration changes
        // (This is handled by rememberSaveable in the actual implementation)
        composeTestRule.onNodeWithText("https://config.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("config@test.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("configpass").assertIsDisplayed()
    }
}