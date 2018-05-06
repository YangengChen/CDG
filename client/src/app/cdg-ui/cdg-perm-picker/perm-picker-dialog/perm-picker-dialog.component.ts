import { Component, OnInit, Inject} from '@angular/core';
import {MatDialogModule, MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {MatRadioModule} from '@angular/material/radio';
@Component({
  selector: 'app-perm-picker-dialog',
  templateUrl: './perm-picker-dialog.component.html',
  styleUrls: ['./perm-picker-dialog.component.scss']
})
export class PermPickerDialogComponent  {
  districtData:any;
  permPrecinct:boolean;
  permConDist:boolean;
  precinctSelection:string;
  districtSelection:string;
  constructor(
    public dialogRef: MatDialogRef<PermPickerDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public info: any) { 
      this.districtData = info.districtData;
      this.permPrecinct = info.permPrecinct;
      this.permConDist = info.permConDist;
      this.precinctSelection = this.permPrecinct.valueOf().toString();
      this.districtSelection = this.permConDist.valueOf().toString();
    }

  applyChanges():void{
    this.dialogRef.close({precinctIsLocked:this.precinctSelection, districtIsLocked:this.districtSelection});
  }
  onNoClick(): void {
    this.dialogRef.close();
  }

}
