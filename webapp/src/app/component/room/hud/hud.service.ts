import {Injectable} from '@angular/core';
import {RoomService} from "../../../service/room.service";
import {HttpClient} from "@angular/common/http";


@Injectable({
  providedIn: 'root'
})
export class HudService {

  constructor(private roomService: RoomService, private http: HttpClient) {
  }



}
