package com.pxl.pixelstore.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.pxl.pixelstore.ui.auth.MasterPasswordScreen
import com.pxl.pixelstore.ui.password.EditPasswordScreen
import com.pxl.pixelstore.ui.password.PasswordDetailsScreen
import com.pxl.pixelstore.ui.password.PasswordsListScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text("Passwords") },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}

@Composable
fun RootScreen(
    navController: NavHostController = rememberNavController()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val backstackEntry by navController.currentBackStackEntryAsState()

    val showTopBar = !(backstackEntry?.destination?.hasRoute(route = Destination.MasterPassword::class) ?: false)
    val showFab = backstackEntry?.destination?.hasRoute(Destination.PasswordsList::class) ?: false

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            if (showTopBar) {
                MainAppBar(
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() }
                )
            }
        },
        floatingActionButton = {
            if (showFab) {
                FloatingActionButton(
                    shape = RoundedCornerShape(12.dp),
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary,
                    onClick = {
                        navController.navigate(Destination.EditPassword())
                    }
                ) {
                    Icon(Icons.Filled.Add, "Add a new password")
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Destination.MasterPassword,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable<Destination.MasterPassword>(
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(700)
                    )
                }
            ) {
                MasterPasswordScreen(
                    snackbarHostState,
                    onMasterPasswordEntered = {
                        navController.navigate(Destination.PasswordsList()) {
                            popUpTo(0)
                        }
                    }
                )
            }

            composable<Destination.PasswordsList>(
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    )
                }
            ) {
                PasswordsListScreen(
                    navigateToEdit = {
                        navController.navigate(Destination.PasswordDetails(it.id?.toString()))
                    }
                )
            }

            composable<Destination.EditPassword>(
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    )
                }
            ) { backStackEntry ->
                val editPassword: Destination.EditPassword = backStackEntry.toRoute()
                EditPasswordScreen(
                    editPassword.recordId,
                    {
                        navController.navigate(Destination.PasswordsList()) {
                            popUpTo(0)
                        }
                    }
                )
            }

            composable<Destination.PasswordDetails>(
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    )
                }
            ) { backStackEntry ->
                val destination: Destination.PasswordDetails = backStackEntry.toRoute()

                PasswordDetailsScreen(
                    destination.recordId,
                    {
                        navController.navigate(Destination.EditPassword(it.id?.toString()))
                    },
                    {
                        navController.navigate(Destination.PasswordsList()) {
                            popUpTo(0)
                        }
                    }
                )
            }
        }
    }
}
