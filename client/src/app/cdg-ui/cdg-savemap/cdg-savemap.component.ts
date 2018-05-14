import { Component, OnInit } from '@angular/core';
import {MatDialogModule, MatDialog, MatDialogRef} from '@angular/material/dialog';
import { CdgSaveDialogComponent } from "./cdg-save-dialog/cdg-save-dialog.component";
@Component({
  selector: 'app-cdg-savemap',
  templateUrl: './cdg-savemap.component.html',
  styleUrls: ['./cdg-savemap.component.scss']
})
export class CdgSavemapComponent implements OnInit {

  constructor(
    private dialog:MatDialog
  ) { }

  ngOnInit() {
  }
  generateSaveMap() : MatDialogRef<CdgSaveDialogComponent>{
    return this.dialog.open(CdgSaveDialogComponent, {
      width: '500px',
      height:"250px",
      data: {}
    });
  }
}
