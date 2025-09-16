package com.fjvid.taskbox.ui.components.utils

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.fjvid.taskbox.navigation.BottomNavItem
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentRoute = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        tonalElevation = 8.dp
    ) {
        BottomNavItem.items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = currentRoute.intValue == index,
                onClick = {
                    currentRoute.intValue = index
                    navController.navigate(item.route)
                },
                icon = { Icon(item.icon, stringResource(item.titleResource)) },
                label = { Text(stringResource(item.titleResource)) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}
