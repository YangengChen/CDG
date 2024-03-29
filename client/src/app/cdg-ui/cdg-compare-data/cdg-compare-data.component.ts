import { Component, OnInit } from '@angular/core';
import {MatDialogModule, MatDialog, MatDialogRef} from '@angular/material/dialog';
import { CdgCompareDialogComponent } from './cdg-compare-dialog/cdg-compare-dialog.component'
import { DecimalPipe } from "@angular/common"

@Component({
  selector: 'app-cdg-compare-data',
  templateUrl: './cdg-compare-data.component.html',
  styleUrls: ['./cdg-compare-data.component.scss']
})
export class CdgCompareDataComponent implements OnInit {

  constructor(
    private dialog:MatDialog
  ) { }

  ngOnInit() {
  }

  generateCompareData(stateData, compareData, originalDataLabel, compareDataLabel) : MatDialogRef<CdgCompareDialogComponent>{
    let dialogData = { 
      stateData: stateData, 
      compareData:compareData,
      originalDataLabel: originalDataLabel,
      compareDataLabel: compareDataLabel
    };
    return this.dialog.open(CdgCompareDialogComponent, {
      width: '950px',
      height:"700px",
      data: dialogData
    });
  }
}
