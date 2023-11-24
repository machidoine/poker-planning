import {Component, Input, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {PlayerComponent} from "./player/player.component";
import {RoomService} from "../../../service/room.service";
import {EMPTY_ROOM, RoomModel} from "../../../domain/room.model";

@Component({
  selector: 'table',
  standalone: true,
  imports: [CommonModule, PlayerComponent],
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent implements OnInit {
  room: RoomModel = EMPTY_ROOM;
  @Input({required: true}) roomId:string = "";
  @Input({required: true}) playerName:string = "";

  constructor(private service: RoomService) {
  }

  ngOnInit(): void {
    this.service.getRoomById(this.roomId, this.playerName)
      .subscribe(room => {
        this.room = room
      })
  }

}
