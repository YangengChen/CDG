import { Component, OnInit, Input } from '@angular/core';
import { MapService } from './map.service';
import { DropdownValue } from "../../objects/dropdownvalue";

@Component({
  selector: 'map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit{

  constructor(private mapService: MapService) {

  }

  map: string;
  mapObject:Object;
  stateName:string;
  disabled:boolean = false;
  @Input() savedMapList:DropdownValue<any>[];
  @Input() mapTypeList:DropdownValue<String>[];

  ngOnInit() {
    if(this.savedMapList == null){
      this.disabled = true;
      this.savedMapList = [new DropdownValue<String>("", "No Saved Maps")];
    }
  }
  
  showMap(){
    this.mapService.getMap()
    .subscribe(data => this.map = data.toString())
  }

  setMapData(mapObject: Object){
    this.mapObject = mapObject;
  }


  mapclick(e:Event){
    console.log(this.mapObject);
  }

}
