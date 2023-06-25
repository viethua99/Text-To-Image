package com.vproject.brushai

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import androidx.navigation.testing.TestNavHostController
import com.vproject.brushai.ui.BrushAiAppState
import com.vproject.brushai.ui.rememberBrushAiAppState
import com.vproject.brushai.uitesthiltmanifest.HiltComponentActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@HiltAndroidTest
class BrushAiAppStateTest {
    /**
     * Manages the components' state and is used to perform injection on your test
     */
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    /**
     * Use a test activity to set the content on.
     */
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    private lateinit var SUT: BrushAiAppState

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun givenAppState_whenSetNewNavCurrentDestination_thenCurrentDestinationAppStateIsCorrect() =
        runTest {
            var currentDestination: String? = null

            composeTestRule.setContent {
                val testNavController = rememberTestNavController()
                SUT = remember(testNavController) {
                    BrushAiAppState(
                        navController = testNavController,
                        windowSizeClass = getCompactWindowClass()
                    )
                }

                currentDestination = SUT.currentDestination?.route

                // Navigate to test explore destination
                LaunchedEffect(Unit) {
                    testNavController.setCurrentDestination("test_explore")
                }
            }
            assertEquals("test_explore", currentDestination)
        }

    @Test
    fun givenAppState_whenSetContent_thenAllTopLevelDestinationsAreAdded() = runTest {
        composeTestRule.setContent {
            SUT = rememberBrushAiAppState(windowSizeClass = getCompactWindowClass())
        }

        assertEquals(2, SUT.topLevelDestinations.size)
        assertTrue(SUT.topLevelDestinations[0].name.contains("generate", true))
        assertTrue(SUT.topLevelDestinations[1].name.contains("explore", true))
    }

    @Test
    fun givenAppState_whenSetContentInCompactSize_thenShouldShowBottomBarIsEnabled() = runTest {
        composeTestRule.setContent {
            SUT = rememberBrushAiAppState(
                navController = NavHostController(LocalContext.current),
                windowSizeClass = getCompactWindowClass(),
            )
        }

        assertTrue(SUT.shouldShowBottomBar)
    }

    private fun getCompactWindowClass() = WindowSizeClass.calculateFromSize(DpSize(500.dp, 300.dp))
}

@Composable
private fun rememberTestNavController(): TestNavHostController {
    val context = LocalContext.current
    val navController = remember {
        TestNavHostController(context).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
            graph = createGraph(startDestination = "test_generate") {
                composable("test_generate") { }
                composable("test_explore") { }
            }
        }
    }
    return navController
}