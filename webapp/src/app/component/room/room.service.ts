import {Injectable, NgZone} from '@angular/core';
import {Observable} from "rxjs";
import {RoomModel} from "../../domain/room.model";
import {ROOM_MOCK} from "./room.mock";
import {SseServiceService} from "../../sse-service.service";

@Injectable({
  providedIn: 'root'
})
export class RoomService {
  constructor(private zone: NgZone, private sseService: SseServiceService) {
  }

  getServerSentEvent(url: string): Observable<MessageEvent> {
    return new Observable<MessageEvent>(observer => {
      const eventSource = this.sseService.getEventSource(url)

      eventSource.onmessage = event => {
        this.zone.run(() => {
          observer.next(event)
        })
      }

      eventSource.onerror = error => {
        this.zone.run(() => {
          observer.error(error)
        })
      }
    })
  }

  getRoom(): Observable<RoomModel> {
    return new Observable<RoomModel>(o => o.next(ROOM_MOCK));
  }

  getRoomById(roomId: string): Observable<MessageEvent> {
    return this.getServerSentEvent(`http://localhost:8080/api/rooms/${roomId}/register-player?name=toto`)
    //return new Observable<RoomModel>(o => o.next(ROOM_MOCK));
  }
}
