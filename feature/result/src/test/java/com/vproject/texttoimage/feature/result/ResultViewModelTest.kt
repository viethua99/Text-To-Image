package com.vproject.texttoimage.feature.result

import com.vproject.texttoimage.core.testing.util.MainDispatcherRule
import org.junit.Before
import org.junit.Rule

class ResultViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var SUT: ResultViewModel

    @Before
    fun setUp() {
        SUT = ResultViewModel()
    }
}