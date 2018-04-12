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
  stylePattern: mapboxgl.FillPaint = 
  {
      'fill-color': {
        property:'CongDist',
        stops: [[20000, '#fff'], [120000, '#f00']]
      },
    };
  districtToColor = {
    1:'red',
    2:'green',
    3:'blue',
    4:'red',
    5:'green',
    6:'blue',
    7:'red',
    8:'green',
    9:'blue'
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
