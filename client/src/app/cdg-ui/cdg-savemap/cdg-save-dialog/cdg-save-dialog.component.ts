import { Component, OnInit, Inject } from '@angular/core';
import {MatDialogModule, MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {MatRadioModule} from '@angular/material/radio';
import { DropdownValue } from "../../../cdg-objects/dropdownvalue"
@Component({
  selector: 'app-cdg-save-dialog',
  templateUrl: './cdg-save-dialog.component.html',
  styleUrls: ['./cdg-save-dialog.component.scss']
})
export class CdgSaveDialogComponent implements OnInit {
  saveInput:any;
  constructor(
    public dialogRef: MatDialogRef<CdgSaveDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public info: any) { }


  ngOnInit() {
  }
  onSave(){
    this.dialogRef.close({savedName: this.saveInput});
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
}
