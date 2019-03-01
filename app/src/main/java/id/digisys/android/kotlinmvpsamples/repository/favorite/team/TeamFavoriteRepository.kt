package id.digisys.android.kotlinmvpsamples.repository.favorite.team

import android.content.Context
import id.digisys.android.kotlinmvpsamples.db.entity.FavoriteTeam
import id.digisys.android.kotlinmvpsamples.model.team.Team
import id.digisys.android.kotlinmvpsamples.utility.db
import io.reactivex.Flowable
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert

class TeamFavoriteRepository (private val context: Context): TeamFavoriteDataSource {

    override fun createTeamFavorite(teamId: String, teamName: String, teamBadge: String, teamManager: String, teamStadium: String, teamDescription: String) : Flowable<Long>{
        var insertedId = -1L
        context.db.use {
            insertedId = insert(FavoriteTeam.TABLE,
                FavoriteTeam.TEAM_ID to teamId,
                FavoriteTeam.TEAM_NAME to teamName,
                FavoriteTeam.TEAM_BADGE to teamBadge,
                FavoriteTeam.TEAM_MANAGER to teamManager,
                FavoriteTeam.TEAM_STADIUM to teamStadium,
                FavoriteTeam.TEAM_DESCRIPTION to teamDescription,
                FavoriteTeam.TEAM_SPORT to "Soccer")
        }
        return Flowable.just(insertedId)
    }

    override fun deleteTeamFavorite(idTeam: String) : Flowable<Boolean> {
        var isDeleted : Boolean? = null
        context.db.use{
            isDeleted = delete(FavoriteTeam.TABLE,
                    "(TEAM_ID = {idTeam})",
                    "idTeam" to idTeam) > 0
        }
        return Flowable.just(isDeleted)
    }

    override fun getTeamFavorite(idTeam: String): Flowable<Team> {
        var team = Team()
        context.db.use {
             select(FavoriteTeam.TABLE)
                    .whereSimple("${FavoriteTeam.TEAM_ID} = ?", idTeam)
                    .parseOpt(object : MapRowParser<Team> {
                        override fun parseRow(columns: Map<String, Any?>): Team {
                            val teamId = columns.getValue("TEAM_ID")
                            val teamName = columns.getValue("TEAM_NAME")
                            val teamBadge = columns.getValue("TEAM_BADGE")
                            val teamManager = columns.getValue("TEAM_MANAGER")
                            val teamStadium = columns.getValue("TEAM_STADIUM")
                            val teamDescription = columns.getValue("TEAM_DESCRIPTION")
                            val teamSport = columns.getValue("TEAM_SPORT")
                            team = Team(
                                idTeam = teamId.toString(),
                                strTeam = teamName.toString(),
                                strTeamBadge = teamBadge.toString(),
                                strManager = teamManager.toString(),
                                strStadium = teamStadium.toString(),
                                strDescriptionEN = teamDescription.toString(),
                                strSport = teamSport.toString())
                            return team
                        }
                    })
        }
        return Flowable.just(team)
    }

    override fun getAllTeamFavorite(): Flowable<List<Team>> {
        val teamList = ArrayList<Team>()
        context.db.use {
            select(FavoriteTeam.TABLE).parseList(object : MapRowParser<List<Team>> {
                override fun parseRow(columns: Map<String, Any?>): List<Team> {
                    val teamId = columns.getValue("TEAM_ID")
                    val teamName = columns.getValue("TEAM_NAME")
                    val teamBadge = columns.getValue("TEAM_BADGE")
                    val teamManager = columns.getValue("TEAM_MANAGER")
                    val teamStadium = columns.getValue("TEAM_STADIUM")
                    val teamDescription = columns.getValue("TEAM_DESCRIPTION")
                    val teamSport = columns.getValue("TEAM_SPORT")
                    teamList.add(Team(
                        idTeam = teamId.toString(),
                        strTeam = teamName.toString(),
                        strTeamBadge = teamBadge.toString(),
                        strManager = teamManager.toString(),
                        strStadium = teamStadium.toString(),
                        strDescriptionEN = teamDescription.toString(),
                        strSport = teamSport.toString()
                    ))
                    return teamList
                }

            })
        }
        return Flowable.just(teamList)
    }

}