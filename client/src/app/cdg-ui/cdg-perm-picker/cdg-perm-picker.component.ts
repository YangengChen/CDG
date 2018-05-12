import { Component, OnInit } from '@angular/core';
import {MatDialogModule, MatDialog, MatDialogRef} from '@angular/material/dialog';
import { PermPickerDialogComponent } from "./perm-picker-dialog/perm-picker-dialog.component"
@Component({
  selector: 'app-cdg-perm-picker',
  templateUrl: './cdg-perm-picker.component.html',
  styleUrls: ['./cdg-perm-picker.component.scss']
})
export class CdgPermPickerComponent implements OnInit {

  constructor(private dialog: MatDialog) { }

  ngOnInit() {
  }
  
  generatePermPicker(precinctIsPerm, condistIsPerm, chosenDistrictData) : MatDialogRef<PermPickerDialogComponent>{
    let dialogData = { permPrecinct: precinctIsPerm, permConDist:condistIsPerm, districtData: chosenDistrictData };
    return this.dialog.open(PermPickerDialogComponent, {
      width: '500px',
      height:"500px",
      data: { permPrecinct: precinctIsPerm, permConDist:condistIsPerm, districtData: chosenDistrictData}
    });
  }

}
