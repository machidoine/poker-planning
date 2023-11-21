import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {DeckComponent} from "./deck/deck.component";

@Component({
  selector: 'hud',
  standalone: true,
  imports: [CommonModule, DeckComponent, DeckComponent, DeckComponent],
  templateUrl: './hud.component.html',
  styleUrls: ['./hud.component.css']
})
export class HudComponent {

}
