import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HudComponent} from "./hud/hud.component";
import {TableComponent} from "./table/table.component";
import {ActivatedRoute} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {ChooseNameDialogComponent} from "./choose-name-dialog.component";

@Component({
  selector: 'room',
  standalone: true,
  imports: [CommonModule, HudComponent, TableComponent],
  templateUrl: './room.component.html',
  styleUrl: './room.component.css'
})
export class RoomComponent implements OnInit {
  _playerName: string = "";

  get roomId(): string {
    return <string>this.route.snapshot.paramMap.get('roomId')
  }

  get playerName(): string {
    return this._playerName
  }

  constructor(private route: ActivatedRoute,
              public dialog: MatDialog) {
    let name = sessionStorage.getItem('playerName');
    if (name) {
      this._playerName = name
    }
  }

  ngOnInit(): void {
    if (!this.playerName) {
      const chooseNameDialog = this.dialog.open(ChooseNameDialogComponent, {
        data: {name: this._playerName},
      });

      chooseNameDialog.afterClosed().subscribe(playerName => {
        console.log('The dialog was closed');
        console.log(playerName)
        this._playerName = playerName;
        sessionStorage.setItem('playerName', this._playerName)
      });
    }
  }
}

