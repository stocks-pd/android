package ru.polytech.stonks.presentation.feathers.catalog.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleCoroutineScope
import coil.compose.rememberImagePainter
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.polytech.stonks.R
import ru.polytech.stonks.domain.common.model.*
import ru.polytech.stonks.domain.feathurs.catalog.model.Stock
import ru.polytech.stonks.presentation.core.compose.Click
import ru.polytech.stonks.presentation.core.compose.Compose
import ru.polytech.stonks.presentation.feathers.catalog.model.CatalogEvent
import ru.polytech.stonks.presentation.feathers.catalog.model.CatalogState
import ru.polytech.stonks.resourses.Montserrat
import ru.polytech.stonks.resourses.AppColors

@Composable
fun CatalogScreen(modelState: MutableState<CatalogState>, consumer: (CatalogEvent) -> Unit) {
    val state by remember { modelState }
    val listState = rememberLazyListState()

    Crossfade(targetState = state.isLoading) {
        if (it) {
            Scaffold(backgroundColor = AppColors.white) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        } else {
            Scaffold(
                backgroundColor = AppColors.white,
                topBar = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = if (listState.firstVisibleItemIndex != 0 ||
                                    listState.firstVisibleItemScrollOffset != 0
                                ) 6.dp else 0.dp,
                            )
                    ) {
                        Compose.ToolbarWithText(text = "Каталог")
                    }
                }
            ) {
                Box {
                    val focusRequester = remember { FocusRequester() }
                    val coroutineScope = rememberCoroutineScope()
                    LazyColumn(state = listState) {
                        item {
                            Header(
                                isFavorsEnabled = state.isFavorsEnabled,
                                isSearchEnabled = state.isSearchEnabled,
                                selectedType = state.selectedStockType,
                                searchText = state.searchText,
                                focusRequester = focusRequester,
                                onStockTypeClicked = { consumer(CatalogEvent.OnTypeChanged(it)) },
                                onSortsClicked = { consumer(CatalogEvent.OnSortsClicked) },
                                onFiltersClicked = { consumer(CatalogEvent.OnFiltersClicked) },
                                onSearchClicked = {
                                    consumer(CatalogEvent.OnSearchClicked)
                                    coroutineScope.launch {
                                        if (state.isSearchEnabled) {
                                            delay(100)
                                            focusRequester.requestFocus()
                                        }
                                    }
                                },
                                onFavorsClicked = { consumer(CatalogEvent.OnFavorsClicked) },
                                onClearSearchClick = { consumer(CatalogEvent.OnClearSearchClicked) },
                                onSearchValueChanged = { consumer(CatalogEvent.OnSearchTextValueChanged(it)) },
                            )
                        }
                        items(state.stocks.size) { index ->
                            StockItem(
                                item = state.stocks[index],
                                onClick = { consumer(CatalogEvent.OnItemClicked(state.stocks[index])) }
                            )
                        }
                    }

                    if (state.isSearchEnabled) {
                        SearchHints(
                            hints = state.searchHints,
                            listState = listState,
                            onClearClick = { consumer(CatalogEvent.OnClearSearchHistoryClicked) },
                            onHintClick = { consumer(CatalogEvent.OnHintClicked(it)) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StockItem(item: Stock, onClick: Click) {
    Row(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 14.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = AppColors.grayLight,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(
                data = item.imageUrl,
                builder = {
                    placeholder(R.drawable.ic_placeholder)
                    error(R.drawable.ic_placeholder)
                    crossfade(true)
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp, start = 6.dp, end = 9.dp)
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
        )

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight().padding(end = 8.dp).weight(1f)
        ) {
            Text(
                text = item.name,
                style = Montserrat.Bold700.SP14,
                color = AppColors.black,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
            Text(
                text = item.ticker,
                style = Montserrat.Medium500.SP11,
                color = AppColors.graySubtext,
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxHeight()
                .padding(end = 13.dp)
        ) {
            Text(
                text = item.price.displayed,
                style = Montserrat.Bold700.SP14,
                color = AppColors.black,
            )
            Text(
                text = item.price.difference.firstOrNull { it.period is Period.Day }?.displayed ?: "",
                style = Montserrat.SemiBold600.SP11,
                color = if (item.price.difference.firstOrNull { it.period is Period.Day }?.isGrows == true) {
                    AppColors.greenAccent
                } else {
                    AppColors.redAccent
                },
            )
        }
    }
}

@Composable
fun StockTypePanel(selectedType: StockType, onClick: (StockType) -> Unit) {
    Row(
        modifier = Modifier
            .height(35.dp)
            .fillMaxWidth()
            .background(
                color = AppColors.grayLight,
                shape = RoundedCornerShape(10.dp)
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        StockTypeItem(
            isSelected = selectedType == StockType.STOCK,
            text = "Акции",
            onClick = { onClick(StockType.STOCK) }
        )
        StockTypeItem(
            isSelected = selectedType == StockType.FUND,
            text = "Фонды",
            onClick = { onClick(StockType.FUND) }
        )
        StockTypeItem(
            isSelected = selectedType == StockType.OBLIGATION,
            text = "Облигации",
            onClick = { onClick(StockType.OBLIGATION) }
        )
        StockTypeItem(
            isSelected = selectedType == StockType.CURRENCY,
            text = "Валюты",
            onClick = { onClick(StockType.CURRENCY) }
        )
    }
}

@Composable
fun StockTypeItem(isSelected: Boolean, text: String, onClick: Click) {
    Box(
        modifier = Modifier
            .padding(2.dp)
            .background(
                color = if (isSelected) {
                    AppColors.greenAccent
                } else {
                    AppColors.grayLight
                },
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .clickable(
                indication = rememberRipple(),
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            ),
    ) {
        Text(
            text = text,
            style = Montserrat.Bold700.SP12,
            color = if (isSelected) AppColors.white else AppColors.black,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun FavorsButton(isEnabled: Boolean, onClick: Click) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(35.dp)
            .background(
                color = if (isEnabled) AppColors.greenAccent else AppColors.grayLight,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Icon(
            painter = painterResource(
                id = if (isEnabled) {
                    R.drawable.ic_star_favors_enabled
                } else {
                    R.drawable.ic_star_favors
                }
            ),
            contentDescription = null,
            tint = if (isEnabled) AppColors.white else AppColors.black
        )
    }
}

@Composable
fun SearchButton(isSearchEnabled: Boolean, onClick: Click) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(35.dp)
            .background(
                color = if (isSearchEnabled) AppColors.greenAccent else AppColors.grayLight,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null,
            tint = if (isSearchEnabled) AppColors.white else AppColors.black
        )
    }
}

@Composable
fun HeaderButton(@DrawableRes icon: Int, text: String, onClick: Click) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = AppColors.grayLight
        ),
        modifier = Modifier
            .height(35.dp)
            .fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = AppColors.black,
            modifier = Modifier.padding(end = 6.dp)
        )

        Text(
            text = text,
            style = Montserrat.SemiBold600.SP14,
            color = AppColors.black,
        )
    }
}

@Composable
fun HeaderDescription() {
    Row(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Название и тикер",
            style = Montserrat.SemiBold600.SP10,
            color = AppColors.graySubtext,
        )

        Text(
            text = "Цена и динамика за год",
            style = Montserrat.SemiBold600.SP10,
            color = AppColors.graySubtext,
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchField(focusRequester: FocusRequester, value: TextFieldValue, onSearchValueChanged: (TextFieldValue) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = value,
        onValueChange = onSearchValueChanged,
        singleLine = true,
        textStyle = Montserrat.Medium500.SP13,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = AppColors.grayLight,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (value.text.isBlank()) {
                    Text(
                        text = "Название или тикер",
                        style = Montserrat.Medium500.SP13,
                        color = AppColors.graySubtext
                    )
                } else {
                    innerTextField()
                }
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .focusRequester(focusRequester),
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.show() },
        )
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SearchOrFiltersPanel(
    isSearchEnabled: Boolean,
    searchText: TextFieldValue,
    focusRequester: FocusRequester,
    onSortsClicked: Click,
    onFiltersClicked: Click,
    onClearSearchClick: Click,
    onSearchValueChanged: (TextFieldValue) -> Unit
) {

    Row(modifier = Modifier.height(35.dp)) {
        if (isSearchEnabled) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                SearchField(
                    focusRequester = focusRequester,
                    value = searchText,
                    onSearchValueChanged = onSearchValueChanged
                )
                IconButton(
                    onClick = onClearSearchClick,
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_clear),
                        contentDescription = null
                    )
                }
            }
        } else {
            Box(modifier = Modifier.weight(1f)) {
                HeaderButton(
                    icon = R.drawable.ic_sorts,
                    text = "Сортировка",
                    onClick = onSortsClicked
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Box(modifier = Modifier.weight(1f)) {
                HeaderButton(
                    icon = R.drawable.ic_filters,
                    text = "Фильтры",
                    onClick = onFiltersClicked
                )
            }
        }
    }
}

@Composable
fun SearchHintsTop(onClearClick: Click) {
    Row(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "История поиска",
            style = Montserrat.SemiBold600.SP13,
            color = AppColors.grayHistory,
        )

        Text(
            text = "Очистить",
            style = Montserrat.SemiBold600.SP13,
            color = AppColors.blueAccent,
            modifier = Modifier.clickable(onClick = onClearClick)
        )
    }
}

@Composable
fun HintItem(text: String, onClick: Click) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_clock),
            contentDescription = null,
            tint = AppColors.grayHistory,
            modifier = Modifier.padding(start = 13.dp, end = 6.dp)
        )

        Text(
            text = text,
            style = Montserrat.Medium500.SP14,
            color = AppColors.grayHistory
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SearchHints(
    hints: List<String>,
    listState: LazyListState,
    onClearClick: Click,
    onHintClick: (String) -> Unit
) {

    val offsetToHide = 100
    val firstItem = listState.layoutInfo.visibleItemsInfo.first()
    val needToShow = listState.firstVisibleItemIndex == 0 &&
            listState.firstVisibleItemScrollOffset < firstItem.size - offsetToHide

    AnimatedVisibility(
        visible = needToShow,
        enter = slideInVertically() + fadeIn(),
        exit = slideOutVertically() + fadeOut()
    ) {

        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        0,
                        firstItem.size - listState.firstVisibleItemScrollOffset
                    )
                }
                .fillMaxSize()
                .alpha(
                    1f - listState.firstVisibleItemScrollOffset.toFloat() /
                            (firstItem.size - offsetToHide)
                )
                .background(color = AppColors.shading)
        )
        if (hints.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            0,
                            firstItem.size - listState.firstVisibleItemScrollOffset
                        )
                    }
                    .background(
                        color = AppColors.white,
                        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                    )
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                SearchHintsTop(onClearClick = onClearClick)
                Divider(modifier = Modifier.padding(vertical = 4.dp))

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())) {
                    hints.forEach {
                        HintItem(text = it, onClick = { onHintClick(it) })
                    }
                }
            }
        }
    }
}


@Composable
private fun Header(
    isFavorsEnabled: Boolean,
    isSearchEnabled: Boolean,
    selectedType: StockType,
    searchText: TextFieldValue,
    focusRequester: FocusRequester,
    onStockTypeClicked: (StockType) -> Unit,
    onSortsClicked: Click,
    onFiltersClicked: Click,
    onSearchClicked: Click,
    onClearSearchClick: Click,
    onSearchValueChanged: (TextFieldValue) -> Unit,
    onFavorsClicked: Click,
) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Row {
            Column(modifier = Modifier.weight(1f)) {
                StockTypePanel(
                    selectedType = selectedType,
                    onClick = onStockTypeClicked
                )
                Spacer(modifier = Modifier.height(8.dp))

                SearchOrFiltersPanel(
                    isSearchEnabled = isSearchEnabled,
                    searchText = searchText,
                    onSortsClicked = onSortsClicked,
                    onFiltersClicked = onFiltersClicked,
                    onClearSearchClick = onClearSearchClick,
                    onSearchValueChanged = onSearchValueChanged,
                    focusRequester = focusRequester
                )
            }

            Spacer(modifier = Modifier.width(13.dp))

            Column {
                FavorsButton(
                    isEnabled = isFavorsEnabled,
                    onClick = onFavorsClicked
                )
                Spacer(modifier = Modifier.height(8.dp))
                SearchButton(
                    onClick = onSearchClicked,
                    isSearchEnabled = isSearchEnabled
                )
            }
        }
        HeaderDescription()
    }
}