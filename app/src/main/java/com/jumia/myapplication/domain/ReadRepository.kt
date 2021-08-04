package com.jumia.myapplication.domain


interface ReadRepository<out T> : GetRepository<T>, GetAllRepository<T>