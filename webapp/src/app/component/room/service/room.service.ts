import {Injectable, NgZone} from '@angular/core';
import {Observable} from "rxjs";
import {RoomModel} from "../../../domain/room.model";
import {SseServiceService} from "../../../sse-service.service";
import {PlayerIdDtoModel} from "./player-id-dto.model";
import {RoomDtoModel} from "./room-dto.model";
import {environment} from "../../../../environments/environment";
import {CookieService} from "ngx-cookie-service";

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  private _playerId: PlayerIdDtoModel | undefined
  private _roomId: string | undefined

  get playerId() {
    return this._playerId
  }

  get roomId() {
    return this._roomId
  }

  constructor(private zone: NgZone, private sseService: SseServiceService, private cookieService: CookieService) {
    if(this.cookieService.get('playerId')) {
      this._playerId = JSON.parse(this.cookieService.get('playerId'))
    }
  }

  getServerSentEvent(url: string): Observable<RoomModel> {
    return new Observable<RoomModel>(observer => {
      const eventSource = this.sseService.getEventSource(url)

      eventSource.addEventListener("new-player-id", e => {
        this._playerId = JSON.parse(e.data)
        this.cookieService.set('playerId', JSON.stringify(this._playerId))
      })

      let dispatchRoom = (e: MessageEvent<any>) => this.zone.run(() => observer.next(this.toRoomModel(e)));

      eventSource.addEventListener("new-player", dispatchRoom)
      eventSource.addEventListener("play-card", dispatchRoom)
      eventSource.addEventListener("reveal-card", dispatchRoom)
      eventSource.addEventListener("hide-card", dispatchRoom)
      eventSource.addEventListener("reset", dispatchRoom)

      eventSource.onerror = error => {
        console.log(error)
        this.zone.run(() => observer.error(error))
      }
    })
  }

  private toRoomModel(e: MessageEvent<any>): RoomModel {
    const room: RoomDtoModel = JSON.parse(e.data)

    return {
      id: room.id,
      cardRevealed: room.cardRevealed,
      players: room.players.map(p => {
        return {
          name: p.name,
          playedCard: {
            value: p.card === null ? undefined : p.card,
            isRecto: room.cardRevealed,
            played: p.hasPlayed
          }
        }
      })
    }
  }

  getRoomById(roomId: string, name: string): Observable<RoomModel> {
    this._roomId = roomId
    const playerParam = this._playerId?.privateId === undefined ? "" : `&playerId=${this._playerId?.privateId}`
    return this.getServerSentEvent(`${environment.apiUrl}/api/rooms/${roomId}/register-player?name=${name}${playerParam}`)
  }
}
