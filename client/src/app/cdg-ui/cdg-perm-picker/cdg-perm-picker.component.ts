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
    return this.dialog.open(PermPickerDialogComponent, {
      width: '350px',
      height:"300px",
      data: { permPrecinct: precinctIsPerm, permConDist:condistIsPerm, districtData: chosenDistrictData }
    });
  }

}
