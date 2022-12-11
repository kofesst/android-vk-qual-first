package me.kofesst.android.vkqualfirst.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import me.kofesst.android.vkqualfirst.R
import me.kofesst.android.vkqualfirst.features.domain.models.Category

@Composable
fun ChooseCategoriesScreen(
    modifier: Modifier = Modifier,
    viewModel: ChooseCategoriesViewModel,
) {
    LaunchedEffect(Unit) {
        viewModel.loadCategories()
    }

    val categories by viewModel.categoriesState
    val chosenCategories by viewModel.chosenCategoriesState
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
    ) {
        GreetingsHeader(modifier = Modifier.fillMaxWidth())
        CategoriesFlowRow(
            categories = categories,
            chosenCategories = chosenCategories,
            onCategorySelect = viewModel::onCategorySelect,
            onCategoryUnselect = viewModel::onCategoryUnselect,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .weight(1.0f)
        )
        ContinueButton(
            hasChosenCategory = chosenCategories.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun GreetingsHeader(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
    ) {
        Text(
            text = "Ваши интересы",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Отметьте те категории, которые Вам интересны, чтобы настроить Дзен под Вас",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CategoriesFlowRow(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    chosenCategories: List<Category>,
    onCategorySelect: (Category) -> Unit,
    onCategoryUnselect: (Category) -> Unit,
) {
    FlowRow(
        mainAxisSpacing = 10.dp,
        modifier = modifier
    ) {
        categories.forEach { category ->
            val selected = chosenCategories.contains(category)
            CategoryChip(
                category = category,
                selected = selected,
                onSelect = {
                    onCategorySelect(category)
                },
                onUnselect = {
                    onCategoryUnselect(category)
                }
            )
        }
    }
}

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryChip(
    category: Category,
    selected: Boolean,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    onSelect: () -> Unit,
    onUnselect: () -> Unit,
) {
    FilterChip(
        selected = selected,
        label = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = category.title,
                    style = textStyle
                )
                CategoryChipStateIcon(
                    selected = selected,
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        onClick = {
            if (selected) {
                onUnselect()
            } else {
                onSelect()
            }
        }
    )
}

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
private fun CategoryChipStateIcon(
    modifier: Modifier = Modifier,
    selected: Boolean,
) {
    val vector = AnimatedImageVector.animatedVectorResource(id = R.drawable.ic_category_chip_select)
    Icon(
        painter = rememberAnimatedVectorPainter(vector, selected),
        contentDescription = null,
        modifier = modifier
    )
}

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
private fun ContinueButton(
    modifier: Modifier = Modifier,
    hasChosenCategory: Boolean,
    onClick: () -> Unit = {},
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            val vector = AnimatedImageVector.animatedVectorResource(R.drawable.ic_continue_later)
            Icon(
                painter = rememberAnimatedVectorPainter(vector, hasChosenCategory),
                contentDescription = null
            )
            Crossfade(
                targetState = hasChosenCategory,
                animationSpec = tween(300)
            ) { state ->
                if (state) {
                    Text(
                        text = "Продолжить",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Text(
                        text = "Возможно, позже",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}