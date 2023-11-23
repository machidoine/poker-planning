import {Injectable, NgZone} from '@angular/core';
import {Observable} from "rxjs";
import {RoomModel} from "../../../domain/room.model";
import {ROOM_MOCK} from "../room.mock";
import {SseServiceService} from "../../../sse-service.service";
import {PlayerIdDtoModel} from "./player-id-dto.model";
import {RoomDtoModel} from "./room-dto.model";

@Injectable({
  providedIn: 'root'
})
export class RoomService {
  constructor(private zone: NgZone, private sseService: SseServiceService) {
  }

  getServerSentEvent(url: string, roomId: string): Observable<RoomModel> {
    return new Observable<RoomModel>(observer => {
      const eventSource = this.sseService.getEventSource(url)

      eventSource.addEventListener("new-player-id", e => {
        const playerId: PlayerIdDtoModel = JSON.parse(e.data)
        console.log(playerId)
      })

      let dispatchRoom = (e: MessageEvent<any>) => {
        console.log(e)
        this.zone.run(() => {
          observer.next(this.toRoomModel(e))
        })
      };

      eventSource.addEventListener("new-player", dispatchRoom)
      eventSource.addEventListener("play-card", dispatchRoom)
      eventSource.addEventListener("reveal-card", dispatchRoom)
      eventSource.addEventListener("hide-card", dispatchRoom)
      eventSource.addEventListener("reset", dispatchRoom)

      eventSource.onerror = error => {
        console.log(error)
        this.zone.run(() => {
          observer.error(error)
        })
      }

      console.log(eventSource)
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

  getRoom(): Observable<RoomModel> {
    return new Observable<RoomModel>(o => o.next(ROOM_MOCK));
  }

  getRoomById(roomId: string, name: string): Observable<RoomModel> {
    return this.getServerSentEvent(`http://localhost:8080/api/rooms/${roomId}/register-player?name=${name}`, roomId)
    //return new Observable<RoomModel>(o => o.next(ROOM_MOCK));
  }
}
