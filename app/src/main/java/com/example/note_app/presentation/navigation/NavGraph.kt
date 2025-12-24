package com.example.note_app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.note_app.presentation.add_edit_note.AddEditNoteScreen
import com.example.note_app.presentation.add_edit_note.AddEditNoteViewModel
import com.example.note_app.presentation.dashboard_screen.DashboardScreen
import com.example.note_app.presentation.dashboard_screen.DashBoardViewModel
import com.example.note_app.presentation.note_list_screen.NoteListScreen
import com.example.note_app.presentation.note_list_screen.NoteListViewModel
import com.example.note_app.presentation.onboarding.OnboardingScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.DashBoard.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Onboarding.route) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(Screen.DashBoard.route) {
                        popUpTo(Screen.DashBoard.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.DashBoard.route) {
            DashboardScreen(
                viewModel = hiltViewModel<DashBoardViewModel>(),
                navController = navController
            )
        }

        composable(
            route = Screen.AddEditNote.route,
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            val viewModel = hiltViewModel<AddEditNoteViewModel>()
            AddEditNoteScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.NoteList.route,
            arguments = listOf(
                navArgument("noteType") {
                    type = NavType.StringType
                }
            )
        ) {
            val viewModel = hiltViewModel<NoteListViewModel>()
            NoteListScreen(
                viewModel = viewModel,
                navController = navController,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}