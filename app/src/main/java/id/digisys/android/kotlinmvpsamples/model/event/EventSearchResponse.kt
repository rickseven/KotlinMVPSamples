package id.digisys.android.kotlinmvpsamples.model.event

import com.google.gson.annotations.SerializedName

data class EventSearchResponse(@SerializedName("event") val event: List<Event>?)