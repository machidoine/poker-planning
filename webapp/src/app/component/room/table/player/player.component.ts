import {Component, Input} from '@angular/core';
import { CommonModule } from '@angular/common';
import {EMPTY_PLAYER, PlayerModel} from "../../../../domain/player.model";
import {PlayedCardComponent} from "./played-card.component";

@Component({
  selector: 'app-player',
  standalone: true,
  imports: [CommonModule, PlayedCardComponent],
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.css']
})
export class PlayerComponent {
  @Input() player: PlayerModel = EMPTY_PLAYER;
}
