package id.digisys.android.kotlinmvpsamples.utility

import io.reactivex.Scheduler

interface SchedulerInterface{
    fun io(): Scheduler
    fun ui(): Scheduler
}