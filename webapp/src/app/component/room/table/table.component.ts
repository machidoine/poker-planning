import {Component, Input} from '@angular/core';
import {CommonModule} from '@angular/common';
import {PlayerComponent} from "./player/player.component";
import {EMPTY_ROOM, RoomModel} from "../../../domain/room.model";

@Component({
  selector: 'table',
  standalone: true,
  imports: [CommonModule, PlayerComponent],
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent {
  @Input({required: true}) room: RoomModel = EMPTY_ROOM;
}
