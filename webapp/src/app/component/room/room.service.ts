import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {PlayerModel} from "../../domain/player.model";
import {RoomModel} from "../../domain/room.model";
import {ROOM_MOCK} from "./room.mock";

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  constructor() { }

  getRoom():Observable<RoomModel> {
    return new Observable<RoomModel>(o => o.next(ROOM_MOCK));
  }
}
