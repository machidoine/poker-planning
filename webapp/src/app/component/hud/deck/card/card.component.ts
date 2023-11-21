import {Component, HostBinding, HostListener, Input} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CardModel} from "../../../../domain/card.model";

@Component({
  selector: 'card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent {
  @Input({required: true}) card!: CardModel;

  @HostBinding("class.selected")
  get selected() {
    return this.card.selected;
  }

  @HostListener("click") onClick() {
    this.card.selected = !this.card.selected;
  }
}
