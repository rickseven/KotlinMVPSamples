package id.digisys.android.kotlinmvpsamples.model.event

import com.google.gson.annotations.SerializedName

data class EventResponse(@SerializedName("events") val events: List<Event>?)