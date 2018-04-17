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
  @Input() mapTypeListLabel: string;
  @Input() savedMapListLabel:string;
  @Input() mapObject:mapboxgl.GeoJSONSource;
  @Input() congressionalDistricts: Object[]
  @Input() savedMapList:DropdownValue<any>[];
  @Input() mapTypeList:DropdownValue<String>[];
  @Input() popupCords:mapboxgl.LngLatLike;
  @Input() currPrecinct:any;
  @Input() mapColorPattern:any;
  disabled:boolean = false;

  stylePattern:any;
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
    this.stylePattern =  {
        'fill-color': {
          type:'categorical',
          property: 'districtID',
          stops: this.mapColorPattern,
          default:"green"
        },
        'fill-opacity': 0.5
      };
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
