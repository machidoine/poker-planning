import {Injectable} from '@angular/core';
import {CardModel} from "../../domain/card.model";
import {RoomService} from "../room/service/room.service";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { environment } from './../../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class HudService {

  constructor(private roomService: RoomService, private http: HttpClient) {
  }

  playCard(card: CardModel) {
    const playerId = this.roomService.playerId?.privateId
    const roomId = this.roomService.roomId

    this.http.post(`${environment.apiUrl}/api/rooms/${roomId}/player/${playerId}/play-card`, card.value).subscribe();
  }

  resetRoom() {
    const roomId = this.roomService.roomId
    this.http.post(`${environment.apiUrl}/api/rooms/${roomId}/reset`, {}).subscribe();
  }

  revealCard() {
    const roomId = this.roomService.roomId
    this.http.post(`${environment.apiUrl}/api/rooms/${roomId}/reveal-card`, {}).subscribe();
  }

  hideCard() {
    const roomId = this.roomService.roomId
    this.http.post(`${environment.apiUrl}/api/rooms/${roomId}/hide-card`, {}).subscribe();
  }

}
