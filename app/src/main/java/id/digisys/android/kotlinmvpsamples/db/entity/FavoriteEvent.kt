package id.digisys.android.kotlinmvpsamples.db.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteEvent(
        @SerializedName("id")
        @Expose
        val id: String? = null,

        @SerializedName("eventId")
        @Expose
        val eventId: String? = null,

        @SerializedName("eventDate")
        @Expose
        val eventDate: String? = null,

        @SerializedName("eventTime")
        @Expose
        val eventTime: String? = null,

        @SerializedName("homeTeamId")
        @Expose
        val homeTeamId: String? = null,

        @SerializedName("awayTeamId")
        @Expose
        val awayTeamId: String? = null,

        @SerializedName("homeTeamName")
        @Expose
        val homeTeamName: String? = null,

        @SerializedName("awayTeamName")
        @Expose
        val awayTeamName: String? = null,

        @SerializedName("homeTeamScore")
        @Expose
        val homeTeamScore: String? = null,

        @SerializedName("awayTeamScore")
        @Expose
        val awayTeamScore: String? = null,

        @SerializedName("eventSport")
        @Expose
        val eventSport: String? = null
) : Parcelable {
    companion object {
        const val TABLE: String = "MATCH_FAVORITE"
        const val ID: String = "ID"
        const val EVENT_ID: String = "EVENT_ID"
        const val EVENT_DATE: String = "EVENT_DATE"
        const val EVENT_TIME: String = "EVENT_TIME"
        const val HOME_TEAM_ID: String = "HOME_TEAM_ID"
        const val AWAY_TEAM_ID: String = "AWAY_TEAM_ID"
        const val HOME_TEAM_NAME: String = "HOME_TEAM_NAME"
        const val AWAY_TEAM_NAME: String = "AWAY_TEAM_NAME"
        const val HOME_TEAM_SCORE: String = "HOME_TEAM_SCORE"
        const val AWAY_TEAM_SCORE: String = "AWAY_TEAM_SCORE"
        const val EVENT_SPORT: String = "EVENT_SPORT"
    }
}