package com.marcokosan.financialapptest.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.marcokosan.financialapptest.ui.home.HomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = AppRoute.HOME,
        modifier = modifier
    ) {
        composable(AppRoute.HOME) {
            HomeScreen(
                onTransactionClick = { transactionId ->
//                    navController.navigate("${AppRoute.TRANSACTION_DETAIL}/$transactionId")
                }
            )
        }
//        composable(
//            route = "${AppRoute.TRANSACTION_DETAIL_ROUTE}/{${AppRoute.Key.TRANSACTION_ID}}",
//            arguments = listOf(navArgument(AppRoute.Key.TRANSACTION_ID) {
//                type = NavType.StringType
//            })
//        ) { backStackEntry ->
//            val transactionId =
//                backStackEntry.arguments?.getString(AppRoute.Key.TRANSACTION_ID)
//            TransactionDetailScreen(transactionId = transactionId)
//        }
    }
}
