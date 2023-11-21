import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DeckModel} from "../../../domain/deck.model";
import {DeckService} from "./deck.service";
import {CardComponent} from "./card/card.component";

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

  ngOnInit() {
    this.deckService.getDeck()
      .subscribe(deck => this.deck = deck);
  }

}
