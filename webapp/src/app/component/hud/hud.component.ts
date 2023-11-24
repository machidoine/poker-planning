import {Component} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DeckComponent} from "./deck/deck.component";
import {CardModel} from "../../domain/card.model";
import {HudService} from "./hud.service";

@Component({
  selector: 'hud',
  standalone: true,
  imports: [CommonModule, DeckComponent, DeckComponent, DeckComponent],
  templateUrl: './hud.component.html',
  styleUrls: ['./hud.component.css']
})
export class HudComponent {

  constructor(private hudService: HudService) {
  }

  cardSelected($selectedCard: CardModel) {
    this.hudService.playCard($selectedCard);
  }

  resetRoom() {
    this.hudService.resetRoom()
  }

  hideCard() {
    this.hudService.hideCard()
  }

  revealCard() {
    this.hudService.revealCard()
  }

}
