import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { MapService } from './map.service';
import { DropdownValue } from "../../cdg-objects/dropdownvalue";
import * as mapboxgl from 'mapbox-gl';
@Component({
  selector: 'map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit{
  map: mapboxgl.Map;
  stateName:string;
  disabled:boolean = false;

  @Input() mapObject:mapboxgl.GeoJSONSource;
  @Input() congressionalDistricts: Object[]
  @Input() savedMapList:DropdownValue<any>[];
  @Input() mapTypeList:DropdownValue<String>[];
  @Input() popupCords:mapboxgl.LngLatLike;
  @Input() currPrecinct:any;
  stops = [
    ["1", "red"],
    ["2", "blue"],
    ["3", "green"],
    ["4", "yellow"],
    ["5", "purple"],
    ["6", "orange"],
    ["7", "black"],
    ["8", "white"],
    ["9", "navy"]
  ]
  stylePattern = {
        'fill-color': {
          type:'categorical',
          property: 'CongDist',
          stops: this.stops,
          default:"green"
        },
        'fill-opacity': 0.5
      };
  popupFilter = ['==', 'name', '']
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

  onPrecinctHover(event){
    this.popupCords = event.lngLat;
    this.currPrecinct = event.features[0].properties;
    this.popupFilter = ['==', 'name', event.features[0].properties.name];
  }

  onPrecinctExit(){
    this.popupCords = null;
    this.popupFilter = ['==', 'name', '']
    this.currPrecinct = null;

  }

  mapClick(event){
    this.clicked.emit(event.feature);
  }
}
