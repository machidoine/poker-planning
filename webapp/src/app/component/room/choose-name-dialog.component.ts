import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from "@angular/material/dialog";
import {Component, HostListener, Inject} from "@angular/core";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";


@Component({
  selector: 'choose-name-dialog',
  templateUrl: './choose-name-dialog.component.html',
  styleUrl: './choose-name-dialog.component.css',
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
export class ChooseNameDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<ChooseNameDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ChooseNameDialogData,
  ) {}

  @HostListener('window:keyup.Enter')
  onDialogClick(): void {
    this.dialogRef.close(this.data.name);
  }
}

export interface ChooseNameDialogData {
  name: string;
}
