package com.koflox.weather._di

import org.koin.core.module.Module

val weatherModules: List<Module> = listOf(
    networkModule,
    dataModule,
    useCaseModule,
    viewModelModule,
)
