import {Component} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HudComponent} from "./hud/hud.component";
import {TableComponent} from "./table/table.component";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
    selector: 'room',
    standalone: true,
    imports: [CommonModule, HudComponent, TableComponent],
    templateUrl: './room.component.html',
    styleUrl: './room.component.css'
})
export class RoomComponent {
    _playerName: string = "";

    get roomId(): string {
        return <string>this.route.snapshot.paramMap.get('roomId')
    }

    get playerName(): string {
        return this._playerName
    }

    constructor(private route: ActivatedRoute, private router: Router) {
        this._playerName = this.router.getCurrentNavigation()?.extras?.state?.['playerName']
    }
}
