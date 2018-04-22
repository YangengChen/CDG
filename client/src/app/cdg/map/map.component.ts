import {  Component, 
          OnInit, 
          Input, 
          Output, 
          EventEmitter }  from '@angular/core';
import { MapService }     from './map.service';
import { DropdownValue }  from "../../cdg-objects/dropdownvalue";
import { AppProperties }  from "../../app.properties";
import * as mapboxgl      from 'mapbox-gl';
import { Constants }      from "../../constants";

@Component({
  selector: 'map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit{
  map: mapboxgl.Map;
  stateName:string;
  stylePattern:any;
  restartImage:string;
  popupFilter = ['==', 'name', '']
  disableSavedMapList:boolean = false;
  @Input() mapTypeListLabel: string;
  @Input() savedMapListLabel:string;
  @Input() mapObject:mapboxgl.GeoJSONSource;
  @Input() congressionalDistricts: Object[]
  @Input() savedMapList:DropdownValue<any>[];
  @Input() mapTypeList:DropdownValue<String>[];
  @Input() popupCords:mapboxgl.LngLatLike;
  @Input() currPrecinct:any;
  @Input() mapColorPattern:any;
  @Input() algoRunning:boolean;
  @Input() algoPaused:boolean;
  @Input() disableMapTypeList:boolean = false;
  @Input() disableResetButton:boolean = false;
  @Output() clicked: EventEmitter<any>
  @Output() mapTypeChanged: EventEmitter<any>;
  @Output() savedMapChanged: EventEmitter<any>;
  @Output() mapReset: EventEmitter<any>;
  constructor(
      private mapService: MapService,
      private appProperties:AppProperties
      ) {
    this.clicked = new EventEmitter<any>();
    this.mapTypeChanged = new EventEmitter<any>();
    this.savedMapChanged = new EventEmitter<any>();
    this.mapReset = new EventEmitter<any>();
  }
  ngOnInit() { 
    let properties = this.appProperties.getProperties();
    this.restartImage = properties.restartImage;
    if(this.savedMapList == null){
      this.disableSavedMapList = true;
      this.savedMapList = [Constants.NO_SAVED_MAPS];
    }
    this.stylePattern =  {
        'fill-color': {
          type:'categorical',
          property: Constants.COLOR_PROPERTY,
          stops: properties.mapColorPattern,
          default: properties.defaultMapColor
        },
        'fill-opacity': properties.mapOpacity
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
  onMapTypeChange(event){
    this.mapTypeChanged.emit(event.value);
  }
  onSavedMapChanged(event){
    this.savedMapChanged.emit(event.value);
  }
  mapClick(event){
    this.clicked.emit(event.feature);
  }
  resetMap(){
    this.mapReset.emit();
  }
}
