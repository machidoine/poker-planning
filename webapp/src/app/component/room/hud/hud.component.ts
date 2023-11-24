import {Component} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DeckComponent} from "./deck/deck.component";
import {CardModel} from "../../../domain/card.model";
import {RoomService} from "../../../service/room.service";

@Component({
    selector: 'hud',
    standalone: true,
    imports: [CommonModule, DeckComponent, DeckComponent, DeckComponent],
    templateUrl: './hud.component.html',
    styleUrls: ['./hud.component.css']
})
export class HudComponent {

    constructor(private roomService: RoomService) {
    }

    cardSelected($selectedCard: CardModel) {
        this.roomService.playCard($selectedCard);
    }

    resetRoom() {
        this.roomService.resetRoom()
    }

    hideCard() {
        this.roomService.hideCard()
    }

    revealCard() {
        this.roomService.revealCard()
    }

}