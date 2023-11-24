import {Component} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from "@angular/forms";
import {RoomService} from "../../service/room.service";
import {Router} from "@angular/router";

@Component({
    selector: 'app-homepage',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './homepage.component.html',
    styleUrl: './homepage.component.css'
})
export class HomepageComponent {
    roomId: string = "";
    playerName: string = "";

    constructor(private roomService: RoomService, private router: Router) {
    }

    joinRoom() {

    }

    createRoom() {
        this.roomService.createRoom()
            .subscribe(roomId => {
                this.router.navigate(['/room', roomId], {state: {playerName: this.playerName}})
            })
    }
}
