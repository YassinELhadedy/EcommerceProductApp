package com.jumia.myapplication.domain


interface Repository<in T, out U> : ReadRepository<U>, WriteRepository<T, U> {

    // TODO: Uncomment if needed
    /*fun isCached(): Boolean*/

    // TODO: Uncomment if needed
    /*fun evict(): Observable<Unit>*/

}