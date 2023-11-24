import {Routes} from "@angular/router";
import {HomepageComponent} from "./component/homepage/homepage.component";
import {RoomComponent} from "./component/room/room.component";

export const routes: Routes = [
    {path: 'home', component: HomepageComponent},
    {path: 'room/:roomId', component: RoomComponent},
    {path: '', redirectTo: 'home', pathMatch: 'full'},
    {path: '**', redirectTo: 'home', pathMatch: 'full'}
];
