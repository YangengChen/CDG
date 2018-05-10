import { Component, OnInit, Inject} from '@angular/core';
import {MatDialogModule, MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {MatRadioModule} from '@angular/material/radio';
import { DropdownValue } from "../../../cdg-objects/dropdownvalue"
import { CongressionalDistrict } from "../../../cdg-objects/congressionaldistrict"

@Component({
  selector: 'app-perm-picker-dialog',
  templateUrl: './perm-picker-dialog.component.html',
  styleUrls: ['./perm-picker-dialog.component.scss']
})
export class PermPickerDialogComponent {
  districtData:any;
  permPrecinct:boolean;
  permConDist:boolean;
  precinctSelection:string;
  districtSelection:string;
  startingDistrict:String;
  movedTo:String = null;
  districtDropdownValues: Array<DropdownValue<CongressionalDistrict>>;
  constructor(
    public dialogRef: MatDialogRef<PermPickerDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public info: any) { 
      console.log("Info: " +   JSON.stringify({ data: info}, null, 4))
      this.districtData = info.districtData;
      this.permPrecinct = info.permPrecinct;
      this.permConDist = info.permConDist;
      this.startingDistrict = info.districtData.districtID;
      this.districtDropdownValues = new Array<DropdownValue<CongressionalDistrict>>();
      for(var i = 1; i <= 8; i++){
        console.log("MAKING DISTRICT DROPDOWN " + i.toString())
        let label = "District " + i.toString()
        this.districtDropdownValues.push(new DropdownValue<CongressionalDistrict>(new CongressionalDistrict(label, "0" + i.toString()), label));
      }
      this.precinctSelection = this.permPrecinct.valueOf().toString();
      this.districtSelection = this.permConDist.valueOf().toString();
    }

  applyChanges():void{
    let starting = this.startingDistrict;
    let moved = this.movedTo;
    this.dialogRef.close({
      precinctIsLocked:this.precinctSelection, 
      districtIsLocked:this.districtSelection,
      movedPrecinct: [starting,  moved]
    });
  }
  choseNewDistrict(event){
    this.movedTo = event.value.districtNum;
  }
  onNoClick(): void {
    this.dialogRef.close();
  }

}
