package id.digisys.android.kotlinmvpsamples.model.player

import com.google.gson.annotations.SerializedName

data class PlayerResponse(@SerializedName("player") val player : List<Player>?)