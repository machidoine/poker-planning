import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {PlayerComponent} from "./player/player.component";
import {PlayerModel} from "../../domain/player.model";
import {RoomService} from "./room.service";
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
    // this.service.getRoom()
    //   .subscribe(room => this.room = room);

    this.service.getRoomById("12")
      .subscribe(event => {
        console.log(event)
      })
  }

}
