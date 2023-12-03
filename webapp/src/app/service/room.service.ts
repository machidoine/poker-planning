import {Injectable, NgZone} from '@angular/core';
import {map, Observable, tap} from "rxjs";
import {RoomModel} from "../domain/room.model";
import {SseServiceService} from "../sse-service.service";
import {PlayerIdDtoModel} from "./player-id-dto.model";
import {RoomDtoModel} from "./room-dto.model";
import {environment} from "../../environments/environment";
import {CardModel} from "../domain/card.model";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RoomService {
  private _eventSource: EventSource | undefined

  get playerId(): PlayerIdDtoModel | undefined {
    const playerId = sessionStorage.getItem('playerId')
    if (playerId) {
      return JSON.parse(playerId)
    } else {
      return undefined
    }
  }

  set playerId(id: PlayerIdDtoModel) {
    sessionStorage.setItem('playerId', JSON.stringify(id))
  }

  constructor(private zone: NgZone,
              private sseService: SseServiceService,
              private http: HttpClient) {
  }

  closeRoom(roomId: String): void {
    this._eventSource?.close()
    this.playerQuit(roomId)
    sessionStorage.removeItem('playerId')
  }

  getServerSentEvent(url: string): Observable<RoomModel> {
    return new Observable<RoomModel>(observer => {
      this._eventSource = this.sseService.getEventSource(url)
      let dispatchRoom = (e: MessageEvent<any>) => this.zone.run(() => observer.next(this.toRoomModel(e.data)));

      this._eventSource.addEventListener("new-player", dispatchRoom)
      this._eventSource.addEventListener("play-card", dispatchRoom)
      this._eventSource.addEventListener("reveal-card", dispatchRoom)
      this._eventSource.addEventListener("hide-card", dispatchRoom)
      this._eventSource.addEventListener("reset", dispatchRoom)
      this._eventSource.addEventListener("player-quit", dispatchRoom)

      // this._eventSource.onerror = error => {
      //     console.log(error)
      //     this.zone.run(() => observer.error(error))
      // }
    })
  }

  private toRoomModel(data: string): RoomModel {
    const room: RoomDtoModel = JSON.parse(data)

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

  getSse(roomId: string, playerId: string): Observable<RoomModel> {
    return this.getServerSentEvent(`${environment.apiUrl}/api/rooms/${roomId}/player/${playerId}/sse`)
  }

  createRoom(): Observable<string> {
    return this.http.post<{ id: string }>(`${environment.apiUrl}/api/rooms/create`, {})
      .pipe(map(room => room.id))
  }

  getOrCreatePlayer(roomId: string, name: string): Observable<PlayerIdDtoModel> {
    if (this.playerId) {
      return new Observable<PlayerIdDtoModel>(o => o.next(this.playerId))
    }

    return this.http.post<PlayerIdDtoModel>(`${environment.apiUrl}/api/rooms/${roomId}/player/create?name=${name}`, {})
      .pipe(tap(ids => {
        this.playerId = ids
      }))
  }

  getRoom(roomId: string): Observable<RoomModel> {
    return this.http.get<RoomModel>(`${environment.apiUrl}/api/rooms/${roomId}`, {})
  }

  playCard(roomId: String, card: CardModel) {
    if (this.playerId) {
      this.http.post(`${environment.apiUrl}/api/rooms/${roomId}/player/${this.playerId.privateId}/play-card`, card.value).subscribe();
    } else {
      console.error("playerId does not exist")
    }
  }

  playerQuit(roomId: String) {
    if (this.playerId) {
      this.http.post(`${environment.apiUrl}/api/rooms/${roomId}/player/${this.playerId.privateId}/quit`, {}).subscribe();
    }
  }

  resetRoom(roomId: String) {
    this.http.post(`${environment.apiUrl}/api/rooms/${roomId}/reset`, {}).subscribe();
  }

  revealCard(roomId: String) {
    this.http.post(`${environment.apiUrl}/api/rooms/${roomId}/reveal-card`, {}).subscribe();
  }

  hideCard(roomId: String) {
    this.http.post(`${environment.apiUrl}/api/rooms/${roomId}/hide-card`, {}).subscribe();
  }

}
