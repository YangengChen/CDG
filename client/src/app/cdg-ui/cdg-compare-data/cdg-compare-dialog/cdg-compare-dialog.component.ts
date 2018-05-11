import { Component, OnInit, Inject} from '@angular/core';
import {MatDialogModule, MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector: 'cdg-compare-dialog',
  templateUrl: './cdg-compare-dialog.component.html',
  styleUrls: ['./cdg-compare-dialog.component.scss']
})
export class CdgCompareDialogComponent  {
  stateData:any;
  compareData:any;
  constructor(
    public dialogRef: MatDialogRef<CdgCompareDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public info: any) { 
      console.log("Info: " +   JSON.stringify({ data: info}, null, 4))
      this.stateData = info.stateData;
      this.compareData = info.compareData;
    }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
