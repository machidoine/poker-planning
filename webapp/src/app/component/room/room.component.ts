import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {PlayerComponent} from "./player/player.component";
import {RoomService} from "./service/room.service";
import {EMPTY_ROOM, RoomModel} from "../../domain/room.model";

@Component({
  selector: 'room',
  standalone: true,
  imports: [CommonModule, PlayerComponent],
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.css']
})
export class RoomComponent implements OnInit {
  room: RoomModel = EMPTY_ROOM;

  constructor(private service: RoomService) {
  }

  ngOnInit(): void {
    this.service.getRoomById("12", "ben")
      .subscribe(room => {
        this.room = room
      })
  }

}
