package ru.polytech.stonks.presentation.feathers.catalog.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import ru.polytech.stonks.R
import ru.polytech.stonks.domain.common.model.Currency
import ru.polytech.stonks.domain.common.model.Period
import ru.polytech.stonks.domain.common.model.Price
import ru.polytech.stonks.domain.common.model.PriceDifference
import ru.polytech.stonks.domain.feathurs.catalog.model.Stock
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
                SearchPanel()
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
            .fillMaxWidth()
            .height(60.dp)
            .background(
                color = AppColors.grayLight,
                shape = RoundedCornerShape(10.dp)
            ),
    ) {
        Image(
            painter = rememberImagePainter(
                data = item.imageUrl,
                builder = {
                    placeholder(R.color.white)
                    error(R.color.white)
                    crossfade(true)
//                    transformations(RoundedCornersTransformation(10.dp.value))
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 14.dp)
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
        )

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxHeight()
        ) {
            Text(
                text = item.name,
                style = Montserrat.Bold700.SP18,
                color = AppColors.black,
//                modifier = Modifier.padding(top = 10.dp)
            )
            Text(
                text = item.ticker,
                style = Montserrat.Medium500.SP14,
                color = AppColors.grayPlaceholder,
//                modifier = Modifier.padding(bottom = 10.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxHeight()
                .padding(end = 8.dp)
        ) {
            Text(
                text = item.price.displayed,
                style = Montserrat.Bold700.SP18,
                color = AppColors.black,
//                modifier = Modifier.padding(top = 10.dp)
            )
            Text(
                text = item.price.difference[Period.Day]?.displayed ?: "",
                style = Montserrat.SemiBold600.SP18,
                color = AppColors.greenAccent,
//                modifier = Modifier.padding(bottom = 10.dp)
            )
        }
    }
}

@Composable
private fun SearchPanel() {
    Column {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .height(35.dp)
                    .background(
                        color = AppColors.white,
                        shape = RoundedCornerShape(30.dp)
                    )
                    .border(
                        border = BorderStroke(1.dp, AppColors.black),
                        shape = RoundedCornerShape(30.dp)
                    )
                    .weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    tint = AppColors.gray,
                )
                Text(
                    text = "Поиск акций",
                    style = Montserrat.SemiBold600.SP16,
                    color = AppColors.gray,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(start = 18.dp)
                    .size(35.dp)
                    .background(
                        color = AppColors.greenAccent,
                        shape = RoundedCornerShape(10.dp)
                    ),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    tint = AppColors.black,
                )
            }

            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(35.dp)
                    .background(
                        color = AppColors.grayLight,
                        shape = RoundedCornerShape(10.dp)
                    ),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    tint = AppColors.black,
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Название и тикер",
                style = Montserrat.SemiBold600.SP10,
                color = AppColors.grayPlaceholder,
            )

            Text(
                text = "Цена и динамика за год",
                style = Montserrat.SemiBold600.SP10,
                color = AppColors.grayPlaceholder,
            )
        }
    }


}

//@Preview(showBackground = true)
//@Composable
//private fun ScreenPreview() {
//    CatalogScreen(
//
//    )
//}

@Preview
@Composable
fun ItemPreview() {
    StockItem(
        item = Stock(
            ticker = "GGLE",
            name = "Google",
            price = Price(
                value = 123.12,
                currency = Currency.Dollar,
                difference = mapOf(
                    Period.Day to PriceDifference(percent = 23.1, isGrows = true)
                )
            ),
            imageUrl = "https://pbs.twimg.com/media/EBEaNVtUcAEZp3E.png"
        )
    )
}