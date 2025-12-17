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
import com.example.note_app.presentation.note_list.NoteListScreen
import com.example.note_app.presentation.note_list.NoteListViewModel
import com.example.note_app.presentation.onboarding.OnboardingScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.NoteList.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.NoteList.route) {
            val viewModel = hiltViewModel<NoteListViewModel>()
            NoteListScreen(
                viewModel = viewModel,
                onNavigateToAddEditNote = { noteId ->
                    navController.navigate(Screen.AddEditNote.createRoute(noteId))
                }
            )
        }

        composable(route = Screen.Onboarding.route) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(Screen.NoteList.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
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
            // ✅ ViewModel tự lấy noteId từ SavedStateHandle
            val viewModel = hiltViewModel<AddEditNoteViewModel>()
            AddEditNoteScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}