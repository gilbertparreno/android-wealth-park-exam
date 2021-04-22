package com.wealthpark.exam.main.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.TestObserver
import com.jraska.livedata.test
import com.wealthpark.exam.core.events.managers.FoodsAndCitiesSynchronizeManager
import com.wealthpark.exam.core.networking.taskStatus.TaskStatus
import com.wealthpark.exam.core.room.repositories.CityRepository
import com.wealthpark.exam.core.room.repositories.FoodRepository
import com.wealthpark.exam.main.entities.MainListData
import com.wealthpark.exam.utils.TestCoroutineRule
import com.wealthpark.exam.utils.providers.TestCoroutineContextProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantTestExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    private lateinit var cityRepository: CityRepository

    @MockK
    private lateinit var foodRepository: FoodRepository

    @MockK(relaxUnitFun = true)
    private lateinit var foodsAndCitiesSynchronizeManager: FoodsAndCitiesSynchronizeManager

    private lateinit var viewModel: MainViewModel
    private lateinit var testCoroutineContextProvider: TestCoroutineContextProvider

    private lateinit var testMainListStatusData: TestObserver<TaskStatus<List<MainListData>>>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        testCoroutineContextProvider = TestCoroutineContextProvider(testCoroutineRule)
        viewModel = MainViewModel(
            cityRepository,
            foodRepository,
            testCoroutineContextProvider,
            foodsAndCitiesSynchronizeManager
        ).apply {
            testMainListStatusData = statusEvent.test()
        }
    }

    @Test
    fun `getLatestFoodsAndCities(false) - SUCCESS`() = testCoroutineRule.runBlockingTest {
        coEvery { cityRepository.findAll() } returns listOf()
        coEvery { foodRepository.findAll() } returns listOf()
        viewModel.getLatestFoodsAndCities(false)
        advanceTimeBy(250)
        coVerifyOrder {
            foodsAndCitiesSynchronizeManager.synchronize(false)
            foodRepository.findAll()
            cityRepository.findAll()
        }
        testMainListStatusData.assertValue(TaskStatus.success(listOf()))
    }

    @Test
    fun `getLatestFoodsAndCities(true) - SUCCESS`() = testCoroutineRule.runBlockingTest {
        coEvery { cityRepository.findAll() } returns listOf()
        coEvery { foodRepository.findAll() } returns listOf()
        viewModel.getLatestFoodsAndCities(true)
        advanceTimeBy(250)
        coVerifyOrder {
            foodRepository.findAll()
            cityRepository.findAll()
        }
        testMainListStatusData.assertValue(TaskStatus.success(listOf()))
    }

    @Test
    fun `getLatestFoodsAndCities(false) - FAILURE`() = testCoroutineRule.runBlockingTest {
        val error = Error("This is a test exception")
        coEvery { foodsAndCitiesSynchronizeManager.synchronize(false) } throws error
        viewModel.getLatestFoodsAndCities(false)
        advanceTimeBy(250)
        coVerify {
            foodsAndCitiesSynchronizeManager.synchronize(false)
        }
        testMainListStatusData.assertValue(TaskStatus.error(error))
    }
}