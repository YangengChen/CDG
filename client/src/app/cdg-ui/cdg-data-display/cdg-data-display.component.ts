import { Component, OnInit, Input } from '@angular/core';
import { CdgCompareDataComponent } from '../cdg-compare-data/cdg-compare-data.component';
@Component({
  selector: 'cdg-data-display',
  templateUrl: './cdg-data-display.component.html',
  styleUrls: ['./cdg-data-display.component.scss']
})
export class CdgDataDisplayComponent implements OnInit {
  @Input() stateData:any;
  @Input() compareData:any;
  @Input() compareDisable:boolean;

  constructor(
    private dataCompare: CdgCompareDataComponent
  ) { }

  ngOnInit() {
  }

  openCompare(){
    if(this.compareData)
    this.dataCompare.generateCompareData(this.stateData, this.compareData);
  }
}
