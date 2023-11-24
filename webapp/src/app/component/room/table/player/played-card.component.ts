import {Component, HostBinding, Input} from '@angular/core';
import {CommonModule} from '@angular/common';
import {EMPTY_PLAYED_CARD, PlayedCardModel} from "../../../../domain/played-card.model";

@Component({
  selector: 'played-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './played-card.component.html',
  styleUrls: ['./played-card.component.css']
})
export class PlayedCardComponent {

  @Input() playedCard: PlayedCardModel = EMPTY_PLAYED_CARD;

  @HostBinding("class.not-played")
  get notPlayed() {
    return !this.played
  }

  @HostBinding("class.played")
  get played() {
    return this.playedCard.played
  }

  @HostBinding("class.recto")
  get isRecto() {
    return this.playedCard.isRecto
  }

  @HostBinding("class.verso")
  get isVerso() {
    return !this.playedCard.isRecto
  }

}
