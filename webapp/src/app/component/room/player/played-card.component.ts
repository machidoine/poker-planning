import {Component, HostBinding, Input} from '@angular/core';
import {CommonModule} from '@angular/common';
import {EMPTY_PLAYER, PlayerModel} from "../../../domain/player.model";
import {EMPTY_PLAYED_CARD, PlayedCardModel} from "../../../domain/played-card.model";

@Component({
  selector: 'played-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './played-card.component.html',
  styleUrls: ['./played-card.component.css']
})
export class PlayedCardComponent {

  @Input() playedCard: PlayedCardModel = EMPTY_PLAYED_CARD;

  @HostBinding("class.no-value")
  get noValue() {
    return this.playedCard.value === undefined
  }

  @HostBinding("class.with-value")
  get withValue() {
    return this.playedCard.value !== undefined
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
