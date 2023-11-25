import {Component, Input} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DeckComponent} from "./deck/deck.component";
import {CardModel} from "../../../domain/card.model";
import {RoomService} from "../../../service/room.service";
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import {EMPTY_ROOM, RoomModel} from "../../../domain/room.model";

@Component({
    selector: 'hud',
    standalone: true,
  imports: [CommonModule, DeckComponent, DeckComponent, DeckComponent, MatButtonModule, MatCardModule],
    templateUrl: './hud.component.html',
    styleUrls: ['./hud.component.css']
})
export class HudComponent {
    @Input({required: true}) room: RoomModel = EMPTY_ROOM;
    constructor(private roomService: RoomService) {
    }

    cardSelected($selectedCard: CardModel) {
        this.roomService.playCard(this.room.id, $selectedCard);
    }

    resetRoom() {
        this.roomService.resetRoom(this.room.id)
    }

    hideCard() {
        this.roomService.hideCard(this.room.id)
    }

    revealCard() {
        this.roomService.revealCard(this.room.id)
    }

}
