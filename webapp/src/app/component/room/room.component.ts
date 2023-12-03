import {Component, HostListener, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HudComponent} from "./hud/hud.component";
import {TableComponent} from "./table/table.component";
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogTitle} from "@angular/material/dialog";
import {ChooseNameDialogComponent} from "./choose-name-dialog.component";
import {RoomService} from "../../service/room.service";
import {EMPTY_ROOM, RoomModel} from "../../domain/room.model";
import {MatInputModule} from "@angular/material/input";
import {catchError, EMPTY} from "rxjs";
import {MatButtonModule} from "@angular/material/button";
import {RoomNotFoundDialogComponent} from "./room-not-found-dialog.component";

@Component({
  selector: 'room',
  standalone: true,
  imports: [CommonModule, HudComponent, TableComponent, MatInputModule, MatDialogTitle, MatDialogContent, MatButtonModule, MatDialogClose, MatDialogActions],
  templateUrl: './room.component.html',
  styleUrl: './room.component.css'
})
export class RoomComponent implements OnInit {
  _playerName: string = "";
  room: RoomModel = EMPTY_ROOM;

  get playerName(): string {
    return this._playerName
  }

  constructor(private route: ActivatedRoute,
              public dialog: MatDialog,
              private service: RoomService,
              private router:Router) {
    let name = sessionStorage.getItem('playerName');
    if (name) {
      this._playerName = name
    }
  }

  ngOnInit(): void {
    let roomId = <string>this.route.snapshot.paramMap.get('roomId')
    if (!this.playerName) {
      const chooseNameDialog = this.dialog.open(ChooseNameDialogComponent, {
        data: {name: this._playerName},
      });

      chooseNameDialog.afterClosed().subscribe(playerName => {
        this._playerName = playerName;
        sessionStorage.setItem('playerName', this._playerName)

        this.initPlayerAndSse(roomId)
      });
    } else {
      this.initPlayerAndSse(roomId)
    }
  }

  initPlayerAndSse(roomId: string) {
    this.service.getRoom(roomId)
      .pipe(catchError((err) => {
        if(err.status == 404) {
          this.dialog.open(RoomNotFoundDialogComponent).afterClosed().subscribe(() => {
            this.router.navigate(['/home'])
          })
        }
        return EMPTY;
      }))
      .subscribe(
        room => {
          this.room = room
          this.service.getOrCreatePlayer(roomId, this.playerName)
            .subscribe(playerId => {
              this.service.getSse(roomId, playerId.privateId)
                .subscribe(room => {
                  this.room = room
                })
            })
        })

  }

  @HostListener("window:unload")
  close(): void {
    this.service.closeRoom(this.room.id)
  }

}

