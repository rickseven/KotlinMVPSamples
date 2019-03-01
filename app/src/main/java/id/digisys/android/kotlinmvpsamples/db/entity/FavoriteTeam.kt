package id.digisys.android.kotlinmvpsamples.db.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteTeam(
        @field:SerializedName("id")
        val id: String? = null,
        @field:SerializedName("teamId")
        val teamId: String? = null,
        @field:SerializedName("teamName")
        val teamName: String? = null,
        @field:SerializedName("teamBadge")
        val teamBadge: String? = null,
        @field:SerializedName("teamManager")
        val teamManager: String? = null,
        @field:SerializedName("teamStadium")
        val teamStadium: String? = null,
        @field:SerializedName("teamDescription")
        val teamDescription: String? = null,
        @field:SerializedName("teamSport")
        val teamSport: String? = null
): Parcelable {
    companion object {
        const val TABLE: String = "TEAM_FAVORITE"
        const val ID: String = "ID"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_BADGE: String = "TEAM_BADGE"
        const val TEAM_MANAGER: String = "TEAM_MANAGER"
        const val TEAM_STADIUM: String = "TEAM_STADIUM"
        const val TEAM_DESCRIPTION: String = "TEAM_DESCRIPTION"
        const val TEAM_SPORT: String = "TEAM_SPORT"
    }
}