import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { MapService } from './map.service';
import { DropdownValue } from "../../cdg-objects/dropdownvalue";

@Component({
  selector: 'map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit{
  map: string;
  stateName:string;
  disabled:boolean = false;
  @Input() mapObject:Object;
  @Input() savedMapList:DropdownValue<any>[];
  @Input() mapTypeList:DropdownValue<String>[];
  @Output() clicked: EventEmitter<any>
  constructor(private mapService: MapService) {
    this.clicked = new EventEmitter<any>();
  }

  ngOnInit() {
    
    if(this.savedMapList == null){
      this.disabled = true;
      this.savedMapList = [new DropdownValue<String>("", "No Saved Maps")];
    }
  }

  mapClick(event){
    this.clicked.emit(event.feature);
  }

}
