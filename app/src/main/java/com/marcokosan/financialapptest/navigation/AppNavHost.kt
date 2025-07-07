package com.marcokosan.financialapptest.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.marcokosan.financialapptest.ui.home.HomeScreen
import com.marcokosan.financialapptest.ui.transactiondetail.TransactionDetailScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = AppRoute.Home,
        modifier = modifier
    ) {
        composable<AppRoute.Home> {
            HomeScreen(
                onTransactionClick = { transactionId ->
                    val route = AppRoute.TransactionDetail(transactionId = transactionId)
                    navController.navigate(route)
                }
            )
        }
        composable<AppRoute.TransactionDetail> { backStackEntry ->
            val transactionDetail: AppRoute.TransactionDetail = backStackEntry.toRoute()
            TransactionDetailScreen(
                transactionId = transactionDetail.transactionId,
                onBackPressed = navController::popBackStack
            )
        }
    }
}