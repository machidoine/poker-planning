import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DeckModel} from "../../../../domain/deck.model";
import {DeckService} from "./deck.service";
import {CardComponent} from "./card/card.component";
import {CardModel} from "../../../../domain/card.model";

@Component({
  selector: 'deck',
  standalone: true,
  imports: [CommonModule, CardComponent],
  templateUrl: './deck.component.html',
  styleUrls: ['./deck.component.css']
})
export class DeckComponent implements OnInit {
  deck: DeckModel | undefined;

  constructor(private deckService: DeckService) {
  }

  @Output() hasBeenSelected = new EventEmitter<CardModel>();

  ngOnInit() {
    this.deckService.getDeck()
      .subscribe(deck => this.deck = deck);
  }

  cardSelected($event: CardModel) {
    this.deck?.cards.forEach(c => c.selected = false)
    this.hasBeenSelected.emit($event);
  }


}
