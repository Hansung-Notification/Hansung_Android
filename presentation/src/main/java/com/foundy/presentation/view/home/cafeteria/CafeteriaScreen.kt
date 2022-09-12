package com.foundy.presentation.view.home.cafeteria

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foundy.domain.model.cafeteria.CafeteriaData
import com.foundy.domain.model.cafeteria.DailyMenu
import com.foundy.domain.model.cafeteria.MenuGroup
import com.foundy.presentation.R
import com.foundy.presentation.model.CafeteriaUiState
import com.foundy.presentation.view.common.FailureContent
import com.foundy.presentation.view.common.LoadingContent
import com.foundy.presentation.view.common.pagerTabIndicatorOffset
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.joda.time.DateTimeConstants
import org.joda.time.chrono.ISOChronology
import java.util.*

@Composable
fun CafeteriaScreen(viewModel: CafeteriaViewModel) {
    when (val uiState = viewModel.uiState) {
        is CafeteriaUiState.Success -> {
            CafeteriaContent(uiState.data, viewModel.dayOfWeek)
        }
        is CafeteriaUiState.Failure -> {
            FailureContent(
                e = uiState.e,
                onClickRetry = viewModel::fetchCafeteriaData
            )
        }
        is CafeteriaUiState.Loading -> {
            LoadingContent()
        }
    }
}

fun getMonToFriTabIndexFrom(dayOfWeek: Int): Int {
    var result = (dayOfWeek % 7) - 1
    result = maxOf(DateTimeConstants.MONDAY - 1, result)
    return minOf(DateTimeConstants.FRIDAY - 1, result)
}

/**
 * [dayOfWeek]는 [DateTimeConstants]의 요일 값이다.
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun CafeteriaContent(data: CafeteriaData, dayOfWeek: Int) {
    Column {
        val dayOfWeekUtil = remember { ISOChronology.getInstance().dayOfWeek() }
        val pagerState = rememberPagerState(getMonToFriTabIndexFrom(dayOfWeek))
        val coroutineScope = rememberCoroutineScope()

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            }
        ) {
            for (weekday in DateTimeConstants.MONDAY..DateTimeConstants.FRIDAY) {
                val index = getMonToFriTabIndexFrom(weekday)

                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(text = dayOfWeekUtil.getAsShortText(weekday, Locale.KOREAN)) }
                )
            }
        }
        HorizontalPager(count = 5, state = pagerState) { index ->
            DailyMenuContent(dailyMenu = data.dailyMenus[index])
        }
    }
}

@Composable
fun DailyMenuContent(dailyMenu: DailyMenu) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = dailyMenu.date.toString(
                stringResource(R.string.daily_menu_date_pattern),
                Locale.KOREAN
            ),
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
            ),
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 4.dp)
        )
        for (menuGroup in dailyMenu.menuGroups) {
            MenuGroupCard(menuGroup = menuGroup)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuGroupCard(menuGroup: MenuGroup) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(
                menuGroup.name,
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                menuGroup.content,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                ),
                lineHeight = 32.sp,
                letterSpacing = 0.8.sp
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun EmptyMenuGroupCardPreview() {
    MenuGroupCard(
        MenuGroup(
            "국&찌개",
            """
        치즈치킨카레동ⓣ 5,000 
        에비카레동ⓣ 4,500 
        소시지카레동ⓣ 4,500 
        목살강된장비빔밥 ⓣ 4,800 
        새우튀김알밥ⓣ 4,500 
        매콤참치마요ⓣ 4,000 
        참치마요ⓣ 3,800 
        육회비빔밥 5,500 
        제육덮밥ⓣ 4,300 



        볶음밥&오므라이스&돈까스 

        고구마돈까스 4,500 
        소떡소떡 3,000 
        오므라이스ⓣ 3,500 
        함박오므라이스ⓣ 5,000 
        치크치킨오므라이스ⓣ 5,500 
    """.trimIndent()
        )
    )
}