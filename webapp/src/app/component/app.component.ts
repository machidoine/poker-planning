import {Component} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DeckComponent} from "./room/hud/deck/deck.component";
import {TableComponent} from "./room/table/table.component";
import {HudComponent} from "./room/hud/hud.component";
import {RouterModule, RouterOutlet} from "@angular/router";


@Component({
    selector: 'app-root',
    standalone: true,
    imports: [
        CommonModule,
        DeckComponent,
        TableComponent,
        HudComponent,
        RouterOutlet,
        RouterModule],
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {
    title = 'webapp';
}
