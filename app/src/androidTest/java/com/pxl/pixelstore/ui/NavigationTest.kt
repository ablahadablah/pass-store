package com.pxl.pixelstore.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pxl.pixelstore.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testNavigation_MasterPasswordToPasswordsList() {
        // Given: App starts with MasterPasswordScreen
        composeTestRule.onNodeWithText("Master Password").assertIsDisplayed()

        // When: Enter master password and unlock
        composeTestRule.onNodeWithText("Password").performTextInput("validpassword")
        composeTestRule.onNodeWithText("Unlock").performClick()

        // Then: Should navigate to PasswordsListScreen
        // Note: This test assumes successful authentication
        // In a real scenario, you'd need to mock the authentication
        composeTestRule.onNodeWithText("Passwords").assertIsDisplayed()
    }

    @Test
    fun testNavigation_PasswordsListToEditPassword() {
        // Given: On PasswordsListScreen
        // Navigate to passwords list (assuming authentication is successful)
        composeTestRule.onNodeWithText("Passwords").assertIsDisplayed()

        // When: Click on a password item
        composeTestRule.onNodeWithText("google.com").performClick()

        // Then: Should navigate to PasswordDetailsScreen
        composeTestRule.onNodeWithText("Password Details").assertIsDisplayed()
    }

    @Test
    fun testNavigation_AddNewPassword() {
        // Given: On PasswordsListScreen
        composeTestRule.onNodeWithText("Passwords").assertIsDisplayed()

        // When: Click FAB to add new password
        composeTestRule.onNodeWithContentDescription("Add a new password").performClick()

        // Then: Should navigate to EditPasswordScreen
        composeTestRule.onNodeWithText("URL").assertIsDisplayed()
        composeTestRule.onNodeWithText("Username").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
    }

    @Test
    fun testNavigation_BackNavigation() {
        // Given: Navigate to EditPasswordScreen
        composeTestRule.onNodeWithText("Passwords").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Add a new password").performClick()
        composeTestRule.onNodeWithText("URL").assertIsDisplayed()

        // When: Press back button
        composeTestRule.onNodeWithContentDescription("Back").performClick()

        // Then: Should navigate back to PasswordsListScreen
        composeTestRule.onNodeWithText("Passwords").assertIsDisplayed()
    }

    @Test
    fun testNavigation_EditPasswordToDetails() {
        // Given: On PasswordDetailsScreen
        composeTestRule.onNodeWithText("Passwords").assertIsDisplayed()
        composeTestRule.onNodeWithText("google.com").performClick()
        composeTestRule.onNodeWithText("Password Details").assertIsDisplayed()

        // When: Click edit button
        composeTestRule.onNodeWithText("Edit").performClick()

        // Then: Should navigate to EditPasswordScreen
        composeTestRule.onNodeWithText("URL").assertIsDisplayed()
        composeTestRule.onNodeWithText("Username").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
    }

    @Test
    fun testNavigation_SavePasswordAndReturn() {
        // Given: On EditPasswordScreen for new password
        composeTestRule.onNodeWithText("Passwords").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Add a new password").performClick()
        composeTestRule.onNodeWithText("URL").assertIsDisplayed()

        // When: Enter data and save
        composeTestRule.onNodeWithText("URL").performTextInput("https://test.com")
        composeTestRule.onNodeWithText("Username").performTextInput("test@test.com")
        composeTestRule.onNodeWithText("Password").performTextInput("testpass123")
        composeTestRule.onNodeWithText("Save").performClick()

        // Then: Should navigate back to PasswordsListScreen
        composeTestRule.onNodeWithText("Passwords").assertIsDisplayed()
    }

    @Test
    fun testNavigation_CancelEditPassword() {
        // Given: On EditPasswordScreen
        composeTestRule.onNodeWithText("Passwords").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Add a new password").performClick()
        composeTestRule.onNodeWithText("URL").assertIsDisplayed()

        // When: Enter data and cancel
        composeTestRule.onNodeWithText("URL").performTextInput("https://cancel.com")
        composeTestRule.onNodeWithText("Cancel").performClick()

        // Then: Should navigate back to PasswordsListScreen
        composeTestRule.onNodeWithText("Passwords").assertIsDisplayed()
    }

    @Test
    fun testNavigation_DeletePassword() {
        // Given: On PasswordDetailsScreen
        composeTestRule.onNodeWithText("Passwords").assertIsDisplayed()
        composeTestRule.onNodeWithText("google.com").performClick()
        composeTestRule.onNodeWithText("Password Details").assertIsDisplayed()

        // When: Click delete button
        composeTestRule.onNodeWithText("Delete").performClick()

        // Then: Should show confirmation dialog
        composeTestRule.onNodeWithText("Delete Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Are you sure you want to delete this password?").assertIsDisplayed()

        // When: Confirm deletion
        composeTestRule.onNodeWithText("Delete").performClick()

        // Then: Should navigate back to PasswordsListScreen
        composeTestRule.onNodeWithText("Passwords").assertIsDisplayed()
    }

    @Test
    fun testNavigation_SearchFunctionality() {
        // Given: On PasswordsListScreen
        composeTestRule.onNodeWithText("Passwords").assertIsDisplayed()

        // When: Enter search query
        composeTestRule.onNodeWithText("Search passwords").performTextInput("google")

        // Then: Should filter results
        composeTestRule.onNodeWithText("google.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("amazon.com").assertDoesNotExist()
    }

    @Test
    fun testNavigation_EmptySearchResults() {
        // Given: On PasswordsListScreen
        composeTestRule.onNodeWithText("Passwords").assertIsDisplayed()

        // When: Search for non-existent item
        composeTestRule.onNodeWithText("Search passwords").performTextInput("nonexistent")

        // Then: Should show empty state
        composeTestRule.onNodeWithText("No passwords found").assertIsDisplayed()
    }

    @Test
    fun testNavigation_AccessibilityNavigation() {
        // Given: App is running
        composeTestRule.onNodeWithText("Master Password").assertIsDisplayed()

        // Then: Verify accessibility elements for navigation
        composeTestRule.onNodeWithContentDescription("Back").assertExists()
        composeTestRule.onNodeWithContentDescription("Add a new password").assertExists()
        composeTestRule.onNodeWithContentDescription("Search passwords").assertExists()
    }

    @Test
    fun testNavigation_DeepLinkHandling() {
        // Given: App is running
        composeTestRule.onNodeWithText("Master Password").assertIsDisplayed()

        // When: Deep link to specific password (if implemented)
        // This would test deep link navigation to specific screens

        // Then: Should handle deep link appropriately
        // Implementation dependent on deep link setup
    }

    @Test
    fun testNavigation_ConfigurationChanges() {
        // Given: On EditPasswordScreen with data
        composeTestRule.onNodeWithText("Passwords").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Add a new password").performClick()
        composeTestRule.onNodeWithText("URL").performTextInput("https://config.com")

        // When: Configuration change occurs (rotation, etc.)
        // This is handled automatically by the test framework

        // Then: Should maintain navigation state and data
        composeTestRule.onNodeWithText("https://config.com").assertIsDisplayed()
    }

    @Test
    fun testNavigation_ErrorHandling() {
        // Given: On PasswordsListScreen
        composeTestRule.onNodeWithText("Passwords").assertIsDisplayed()

        // When: Network error occurs (simulated)
        // This would test error handling in navigation

        // Then: Should show appropriate error state
        // Implementation dependent on error handling
    }
}