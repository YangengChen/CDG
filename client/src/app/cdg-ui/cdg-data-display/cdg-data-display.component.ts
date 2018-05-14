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
  @Input() originalDataLabel:any;
  @Input() compareDataLabel:any;
  @Input() compareDisable:boolean;

  constructor(
    private dataCompare: CdgCompareDataComponent
  ) { }

  ngOnInit() {
  }

  openCompare(data){
    if(data.representative && this.compareData) {
      let compare = null;
      for(let i = 0; i < this.compareData.districts.length; i++){
        if(this.compareData.districts[i].id == data.id){
          compare = this.compareData.districts[i];
          break;
        }
      }
      this.dataCompare.generateCompareData(data, compare, this.originalDataLabel, this.compareDataLabel);
    }
    else if(this.compareData){
      console.log("COMPARE: " + JSON.stringify(this.compareData.districts[0]))
      console.log("ORIGINAL: " + JSON.stringify(this.stateData.districts[0]))
      this.dataCompare.generateCompareData(this.stateData.districts[0], this.compareData.districts[0], this.originalDataLabel, this.compareDataLabel);
    }
  }
}
