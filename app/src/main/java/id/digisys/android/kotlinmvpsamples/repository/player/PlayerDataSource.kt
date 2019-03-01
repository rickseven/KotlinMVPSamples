package id.digisys.android.kotlinmvpsamples.repository.player

import id.digisys.android.kotlinmvpsamples.model.player.PlayerResponse
import io.reactivex.Flowable

interface PlayerDataSource{
    fun getAllPlayer(idTeam: String): Flowable<PlayerResponse>
    fun getPlayer(idPlayer: String): Flowable<PlayerResponse>
}