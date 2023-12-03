import {
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from "@angular/material/dialog";
import {Component, HostListener} from "@angular/core";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";


@Component({
  selector: 'room-not-found-dialof',
  templateUrl: './room-not-found-dialog.component.html',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
  ],
})
export class RoomNotFoundDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<RoomNotFoundDialogComponent>
  ) {
  }

  @HostListener('window:keyup.Enter')
  onDialogClick(): void {
    this.dialogRef.close();
  }
}
