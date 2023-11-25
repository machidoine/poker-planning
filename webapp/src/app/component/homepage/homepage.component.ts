import {Component} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from "@angular/forms";
import {RoomService} from "../../service/room.service";
import {Router} from "@angular/router";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";

@Component({
    selector: 'app-homepage',
    standalone: true,
  imports: [CommonModule, FormsModule, MatInputModule, MatButtonModule, MatCardModule],
    templateUrl: './homepage.component.html',
    styleUrl: './homepage.component.css'
})
export class HomepageComponent {
    roomId: string = "";

    constructor(private roomService: RoomService, private router: Router) {
    }

    joinRoom() {
      this.router.navigate(['/room', this.roomId])
    }

    createRoom() {
        this.roomService.createRoom()
            .subscribe(roomId => {
                this.router.navigate(['/room', roomId])
            })
    }
}
