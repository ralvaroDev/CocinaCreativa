package pe.ralvaro.cocinacreativa.ui

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import pe.ralvaro.cocinacreativa.domain.launcher.InitialStateUseCase
import pe.ralvaro.cocinacreativa.util.FakePreferenceDataStorage
import pe.ralvaro.cocinacreativa.util.MainCoroutineRule

/**
 * Test for [LauncherViewModel]
 */
@ExperimentalCoroutinesApi
class LauncherViewModelTest {

    // Let's override the main dispatcher
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @Test
    fun launchDestination_noCompleteOnboarding_emitInitialViewOnboarding() = destinationTest(
        isCompleteOnboarding = false, expectedDestination = InitialView.Onboarding
    )

    @Test
    fun launchDestination_CompleteOnboarding_emitInitialViewMain() = destinationTest(
        isCompleteOnboarding = true, expectedDestination = InitialView.Main
    )

    private fun destinationTest(
        isCompleteOnboarding: Boolean,
        expectedDestination: InitialView
    ) = runTest {

        val fakePref = FakePreferenceDataStorage(isOnboardingComplete = isCompleteOnboarding)
        val initialStateUseCase =
            InitialStateUseCase(
                preferenceRepository = fakePref,
                dispatcher = coroutineRule.testDispatcher
            )
        val viewModel = LauncherViewModel(initialStateUseCase)

        val result = viewModel.launchDestination.first()
        assertEquals(/* expected = */ expectedDestination, /* actual = */ result)
    }

}

