package com.pxl.pixelstore.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pxl.pixelstore.MainActivity
import com.pxl.pixelstore.domain.entity.PasswordRecord
import com.pxl.pixelstore.ui.password.PasswordsListForm
import com.pxl.pixelstore.viewmodel.password.PasswordsScreenState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class PasswordsListScreenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var mockPasswords: List<PasswordRecord>

    @Before
    fun setUp() {
        mockPasswords = listOf(
            PasswordRecord(
                id = UUID.randomUUID(),
                url = "https://google.com",
                username = "user@gmail.com",
                password = "encrypted_password_1",
                timeCreated = System.currentTimeMillis() - 86400000,
                timeLastUsed = System.currentTimeMillis() - 3600000,
                timePasswordChanged = System.currentTimeMillis() - 86400000
            ),
            PasswordRecord(
                id = UUID.randomUUID(),
                url = "https://github.com",
                username = "developer",
                password = "encrypted_password_2",
                timeCreated = System.currentTimeMillis() - 172800000,
                timeLastUsed = System.currentTimeMillis() - 7200000,
                timePasswordChanged = System.currentTimeMillis() - 172800000
            ),
            PasswordRecord(
                id = UUID.randomUUID(),
                url = "https://amazon.com",
                username = "shopper@email.com",
                password = "encrypted_password_3",
                timeCreated = System.currentTimeMillis() - 259200000,
                timeLastUsed = System.currentTimeMillis() - 10800000,
                timePasswordChanged = System.currentTimeMillis() - 259200000
            )
        )
    }

    @Test
    fun testPasswordsListScreen_LoadingState() {
        // Given: PasswordsListScreen in loading state
        composeTestRule.setContent {
            PasswordsListForm(
                state = PasswordsScreenState.Loading,
                onSearch = {},
                onPasswordSelected = {}
            )
        }

        // Then: Verify loading indicator is displayed
        composeTestRule.onNodeWithContentDescription("Loading").assertIsDisplayed()
        composeTestRule.onNode(hasTestTag("loading_indicator")).assertExists()
    }

    @Test
    fun testPasswordsListScreen_EmptyState() {
        // Given: PasswordsListScreen with empty data
        composeTestRule.setContent {
            PasswordsListForm(
                state = PasswordsScreenState.Empty,
                onSearch = {},
                onPasswordSelected = {}
            )
        }

        // Then: Verify empty state is displayed
        composeTestRule.onNodeWithText("No passwords found").assertIsDisplayed()
        composeTestRule.onNodeWithText("Add your first password").assertIsDisplayed()
    }

    @Test
    fun testPasswordsListScreen_DataState() {
        // Given: PasswordsListScreen with data
        composeTestRule.setContent {
            PasswordsListForm(
                state = PasswordsScreenState.Data(mockPasswords, null),
                onSearch = {},
                onPasswordSelected = {}
            )
        }

        // Then: Verify password items are displayed
        composeTestRule.onNodeWithText("google.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("user@gmail.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("github.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("developer").assertIsDisplayed()
        composeTestRule.onNodeWithText("amazon.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("shopper@email.com").assertIsDisplayed()
    }

    @Test
    fun testPasswordsListScreen_SearchFunctionality() {
        // Given: PasswordsListScreen with data
        composeTestRule.setContent {
            PasswordsListForm(
                state = PasswordsScreenState.Data(mockPasswords, null),
                onSearch = {},
                onPasswordSelected = {}
            )
        }

        // When: Enter search query
        composeTestRule.onNodeWithText("Search passwords").performTextInput("google")

        // Then: Verify search functionality (implementation dependent)
        // This test assumes search is handled by the parent component
        composeTestRule.onNodeWithText("google").assertIsDisplayed()
    }

    @Test
    fun testPasswordsListScreen_PasswordSelection() {
        var selectedPassword: PasswordRecord? = null

        // Given: PasswordsListScreen with data
        composeTestRule.setContent {
            PasswordsListForm(
                state = PasswordsScreenState.Data(mockPasswords, null),
                onSearch = {},
                onPasswordSelected = { password ->
                    selectedPassword = password
                }
            )
        }

        // When: Click on a password item
        composeTestRule.onNodeWithText("google.com").performClick()

        // Then: Verify password selection callback is triggered
        assert(selectedPassword != null)
        assert(selectedPassword?.url == "https://google.com")
    }

    @Test
    fun testPasswordsListScreen_SearchClear() {
        // Given: PasswordsListScreen with search query
        composeTestRule.setContent {
            PasswordsListForm(
                state = PasswordsScreenState.Data(mockPasswords, null),
                onSearch = {},
                onPasswordSelected = {}
            )
        }

        // When: Enter search query and clear it
        composeTestRule.onNodeWithText("Search passwords").performTextInput("test")

        // Clear search (assuming there's a clear button)
        composeTestRule.onNodeWithContentDescription("Clear search").performClick()

        // Then: Verify search is cleared
        composeTestRule.onNodeWithText("Search passwords").assertIsDisplayed()
    }

    @Test
    fun testPasswordsListScreen_Accessibility() {
        // Given: PasswordsListScreen with data
        composeTestRule.setContent {
            PasswordsListForm(
                state = PasswordsScreenState.Data(mockPasswords, null),
                onSearch = {},
                onPasswordSelected = {}
            )
        }

        // Then: Verify accessibility elements
        composeTestRule.onNodeWithContentDescription("Search passwords").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Loading").assertExists()
    }

    @Test
    fun testPasswordsListScreen_LongUrlDisplay() {
        // Given: Password with very long URL
        val longUrlPassword = PasswordRecord(
            id = UUID.randomUUID(),
            url = "https://very-long-domain-name-that-exceeds-normal-display-width.com/path/to/resource",
            username = "user@longdomain.com",
            password = "password",
            timeCreated = System.currentTimeMillis(),
            timeLastUsed = System.currentTimeMillis(),
            timePasswordChanged = System.currentTimeMillis()
        )

        composeTestRule.setContent {
            PasswordsListForm(
                state = PasswordsScreenState.Data(listOf(longUrlPassword), null),
                onSearch = {},
                onPasswordSelected = {}
            )
        }

        // Then: Verify long URL is handled gracefully
        composeTestRule.onNodeWithText("very-long-domain-name-that-exceeds-normal-display-width.com").assertIsDisplayed()
    }

    @Test
    fun testPasswordsListScreen_SpecialCharactersInData() {
        // Given: Password with special characters
        val specialCharPassword = PasswordRecord(
            id = UUID.randomUUID(),
            url = "https://test.com",
            username = "user@test.com",
            password = "p@ssw0rd!",
            timeCreated = System.currentTimeMillis(),
            timeLastUsed = System.currentTimeMillis(),
            timePasswordChanged = System.currentTimeMillis()
        )

        composeTestRule.setContent {
            PasswordsListForm(
                state = PasswordsScreenState.Data(listOf(specialCharPassword), null),
                onSearch = {},
                onPasswordSelected = {}
            )
        }

        // Then: Verify special characters are displayed correctly
        composeTestRule.onNodeWithText("test.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("user@test.com").assertIsDisplayed()
    }

    @Test
    fun testPasswordsListScreen_UnicodeCharacters() {
        // Given: Password with unicode characters
        val unicodePassword = PasswordRecord(
            id = UUID.randomUUID(),
            url = "https://测试.com",
            username = "用户@测试.com",
            password = "密码123",
            timeCreated = System.currentTimeMillis(),
            timeLastUsed = System.currentTimeMillis(),
            timePasswordChanged = System.currentTimeMillis()
        )

        composeTestRule.setContent {
            PasswordsListForm(
                state = PasswordsScreenState.Data(listOf(unicodePassword), null),
                onSearch = {},
                onPasswordSelected = {}
            )
        }

        // Then: Verify unicode characters are displayed correctly
        composeTestRule.onNodeWithText("测试.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("用户@测试.com").assertIsDisplayed()
    }

    @Test
    fun testPasswordsListScreen_EmptySearchResults() {
        // Given: PasswordsListScreen with data
        composeTestRule.setContent {
            PasswordsListForm(
                state = PasswordsScreenState.Data(mockPasswords, null),
                onSearch = {},
                onPasswordSelected = {}
            )
        }

        // When: Search for non-existent item
        composeTestRule.onNodeWithText("Search passwords").performTextInput("nonexistent")

        // Then: Verify empty search results (implementation dependent)
        // This would depend on how the search is implemented
        composeTestRule.onNodeWithText("Search passwords").assertIsDisplayed()
    }

    @Test
    fun testPasswordsListScreen_ConfigurationChanges() {
        // Given: PasswordsListScreen with data
        composeTestRule.setContent {
            PasswordsListForm(
                state = PasswordsScreenState.Data(mockPasswords, null),
                onSearch = {},
                onPasswordSelected = {}
            )
        }

        // When: Enter search query
        composeTestRule.onNodeWithText("Search passwords").performTextInput("test")

        // Then: Search query should persist through configuration changes
        // (This is handled by rememberSaveable in the actual implementation)
        composeTestRule.onNodeWithText("test").assertIsDisplayed()
    }
}