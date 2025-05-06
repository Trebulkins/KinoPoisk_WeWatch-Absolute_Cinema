package com.example.absolute_cinema_wewatch.main

import com.example.absolute_cinema_wewatch.database.LocalDataSource

class MainPresenter(
    private var viewInterface: MainContract.ViewInterface,
    private var dataSource: LocalDataSource
) {
    private val TAG = "MainPresenter"
}