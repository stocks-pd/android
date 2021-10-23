package ru.polytech.stonks.presentation.feathers.catalog.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
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
    Column {
        Compose.ToolbarWithText(text = "Каталог")

        LazyColumn {
            item {
                Header()
            }
            items(state.stocks.size) { index ->
                StockItem(item = state.stocks[index])
            }
        }
    }
}

@Composable
private fun StockItem(item: Stock) {
    Row(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .height(50.dp)
            .background(
                color = AppColors.grayLight,
                shape = RoundedCornerShape(10.dp)
            ),
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
            modifier = Modifier.fillMaxHeight()
        ) {
            Text(
                text = item.name,
                style = Montserrat.Bold700.SP14,
                color = AppColors.black,
            )
            Text(
                text = item.ticker,
                style = Montserrat.Medium500.SP11,
                color = AppColors.graySubtext,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            verticalArrangement = Arrangement.Center,
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
                text = item.price.difference[Period.Day]?.displayed ?: "",
                style = Montserrat.SemiBold600.SP11,
                color = if (item.price.difference[Period.Day]?.isGrows == true) {
                    AppColors.greenAccent
                } else {
                    AppColors.redAccent
                },
            )
        }
    }
}

@Composable
fun StockTypePanel(selectedType: StockType) {
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
        StockTypeItem(isSelected = selectedType == StockType.STOCK, text = "Акции", onClick = {})
        StockTypeItem(isSelected = selectedType == StockType.FUND, text = "Фонды", onClick = {})
        StockTypeItem(
            isSelected = selectedType == StockType.OBLIGATION,
            text = "Облигации",
            onClick = {})
        StockTypeItem(
            isSelected = selectedType == StockType.CURRENCY,
            text = "Валюты",
            onClick = {})
    }
}

@Composable
fun StockTypeItem(isSelected: Boolean, text: String, onClick: Click) {
    val modifierSelected = Modifier
        .padding(2.dp)
        .background(
            color = AppColors.greenAccent,
            shape = RoundedCornerShape(10.dp)
        )
    Box(
        modifier = if (isSelected) modifierSelected else Modifier.padding(2.dp),
        contentAlignment = Alignment.Center
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
fun FavorsButton(isActive: Boolean, onClick: Click) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(35.dp)
            .background(
                color = if (isActive) AppColors.greenAccent else AppColors.grayLight,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Icon(
            painter = painterResource(
                id = if (isActive) {
                    R.drawable.ic_star_favors_enabled
                } else {
                    R.drawable.ic_star_favors
                }
            ),
            contentDescription = null,
            tint = if (isActive) AppColors.white else AppColors.black
        )
    }
}

@Composable
fun SearchButton(onClick: Click) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(35.dp)
            .background(
                color = AppColors.grayLight,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null,
            tint = AppColors.black
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


@Composable
private fun Header() {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Row {
            Column(modifier = Modifier.weight(1f)) {
                StockTypePanel(StockType.STOCK)
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Box(modifier = Modifier.weight(1f)) {
                        HeaderButton(icon = R.drawable.ic_sorts, text = "Сортировка") {}
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        HeaderButton(icon = R.drawable.ic_filters, text = "Фильтры") {}
                    }
                }
            }

            Spacer(modifier = Modifier.width(13.dp))

            Column {
                FavorsButton(isActive = false) {}
                Spacer(modifier = Modifier.height(8.dp))
                SearchButton {}
            }
        }
        HeaderDescription()
    }
}

@Preview
@Composable
fun ItemPreview() {
    StockItem(
        item = Stock(
            ticker = "GGLE",
            name = "Google ",
            price = Price(
                value = 123.12,
                currency = Currency.Dollar,
                difference = mapOf(
                    Period.Day to PriceDifference(percent = 23.1, isGrows = true)
                )
            ),
            imageUrl = "https://pbs.twimg.com/media/EBEaNVtUcAEZp3E.png",
            type = StockType.STOCK
        )
    )
}

@Preview
@Composable
fun HeaderPreview() {
    Header()
}