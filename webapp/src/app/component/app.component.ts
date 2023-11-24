import {Component} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DeckComponent} from "./hud/deck/deck.component";
import {RoomComponent} from "./room/room.component";
import {HudComponent} from "./hud/hud.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, DeckComponent, RoomComponent, HudComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'webapp';
}
