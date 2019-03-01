package id.digisys.android.kotlinmvpsamples.repository.player

import id.digisys.android.kotlinmvpsamples.api.service.TheSportDbService
import id.digisys.android.kotlinmvpsamples.model.player.PlayerResponse
import io.reactivex.Flowable

class PlayerRepository(var theSportDbService: TheSportDbService): PlayerDataSource{
    override fun getAllPlayer(idTeam: String): Flowable<PlayerResponse> {
        return theSportDbService.getAllPlayer(idTeam)
    }

    override fun getPlayer(idPlayer: String): Flowable<PlayerResponse> {
        return theSportDbService.getPlayer(idPlayer)
    }

}