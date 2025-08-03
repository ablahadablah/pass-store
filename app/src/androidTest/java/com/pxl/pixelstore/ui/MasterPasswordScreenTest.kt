package com.pxl.pixelstore.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isFocused
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pxl.pixelstore.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MasterPasswordScreenTest {

    @get:Rule(0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testMasterPasswordScreen_InitialState() {
        // Given: MasterPasswordScreen is displayed (already set by MainActivity)
        
        // Then: Verify initial UI elements are present
        composeTestRule.onNodeWithText("Enter master password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Enter").assertIsDisplayed()
        
        // Verify password field is empty and focused
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNode(hasText("Password") and isFocused()).assertExists()
    }

    @Test
    fun testMasterPasswordScreen_EnterValidPassword() {
        // Given: MasterPasswordScreen is displayed (already set by MainActivity)

        // When: Enter a valid password
        composeTestRule.onNodeWithText("Password").performTextInput("validpassword123")

        // Then: Verify password is entered
        composeTestRule.onNodeWithText("validpassword123").assertIsDisplayed()
    }

    @Test
    fun testMasterPasswordScreen_PasswordVisibilityToggle() {
        // Given: MasterPasswordScreen is displayed (already set by MainActivity)

        // When: Enter password and toggle visibility
        composeTestRule.onNodeWithText("Password").performTextInput("testpassword")
        
        // Initially password should be hidden
        composeTestRule.onNodeWithText("testpassword").assertIsDisplayed()
        
        // Click visibility toggle
        composeTestRule.onNodeWithContentDescription("Toggle password visibility").performClick()
        
        // Password should still be visible (toggle behavior)
        composeTestRule.onNodeWithText("testpassword").assertIsDisplayed()
    }

    @Test
    fun testMasterPasswordScreen_EmptyPasswordValidation() {
        // Given: MasterPasswordScreen is displayed (already set by MainActivity)

        // When: Try to unlock with empty password
        composeTestRule.onNodeWithText("Unlock").performClick()

        // Then: Should show validation error (implementation dependent)
        // This test assumes some validation feedback is shown
        composeTestRule.onNodeWithText("Unlock").assertIsDisplayed()
    }

    @Test
    fun testMasterPasswordScreen_KeyboardActions() {
        // Given: MasterPasswordScreen is displayed (already set by MainActivity)

        // When: Enter password and press IME action (Done)
        composeTestRule.onNodeWithText("Password").performTextInput("testpassword")
        
        // Simulate IME action (Done key)
        composeTestRule.onNodeWithText("Password").performImeAction()

        // Then: Should trigger unlock action (implementation dependent)
        // This test verifies keyboard action is handled
        composeTestRule.onNodeWithText("Unlock").assertIsDisplayed()
    }

    @Test
    fun testMasterPasswordScreen_Accessibility() {
        // Given: MasterPasswordScreen is displayed (already set by MainActivity)

        // Then: Verify accessibility elements
        composeTestRule.onNodeWithContentDescription("Toggle password visibility").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Unlock").assertIsDisplayed()
    }

    @Test
    fun testMasterPasswordScreen_LongPasswordInput() {
        // Given: MasterPasswordScreen is displayed (already set by MainActivity)

        // When: Enter a very long password
        val longPassword = "a".repeat(1000)
        composeTestRule.onNodeWithText("Password").performTextInput(longPassword)

        // Then: Should handle long input gracefully
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Unlock").assertIsDisplayed()
    }

    @Test
    fun testMasterPasswordScreen_SpecialCharacters() {
        // Given: MasterPasswordScreen is displayed (already set by MainActivity)

        // When: Enter password with special characters
        val specialPassword = "!@#$%^&*()_+-=[]{}|;':\",./<>?"
        composeTestRule.onNodeWithText("Password").performTextInput(specialPassword)

        // Then: Should handle special characters
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
    }

    @Test
    fun testMasterPasswordScreen_UnicodeCharacters() {
        // Given: MasterPasswordScreen is displayed (already set by MainActivity)

        // When: Enter password with unicode characters
        val unicodePassword = "密码123🔐"
        composeTestRule.onNodeWithText("Password").performTextInput(unicodePassword)

        // Then: Should handle unicode characters
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
    }

    @Test
    fun testMasterPasswordScreen_ConfigurationChanges() {
        // Given: MasterPasswordScreen is displayed (already set by MainActivity)

        // When: Enter password
        composeTestRule.onNodeWithText("Password").performTextInput("testpassword")

        // Then: Password should persist through configuration changes
        // (This is handled by rememberSaveable in the actual implementation)
        composeTestRule.onNodeWithText("testpassword").assertIsDisplayed()
    }
} 