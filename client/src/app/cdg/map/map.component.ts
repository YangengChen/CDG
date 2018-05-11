import {  Component, 
          OnInit, 
          Input, 
          Output, 
          EventEmitter }  from '@angular/core';
import { CdgMapService }     from './map.service';
import { CdgModule }      from '../cdg.module';
import { DropdownValue }  from "../../cdg-objects/dropdownvalue";
import { AppProperties }  from "../../app.properties";
import * as mapboxgl      from 'mapbox-gl';
import { Constants }      from "../../constants";
import { NgxMapboxGLModule } from 'ngx-mapbox-gl';
import {MatDialogModule, MatDialog} from '@angular/material/dialog';
import { CdgPermPickerComponent } from "../../cdg-ui/cdg-perm-picker/cdg-perm-picker.component";
import { GenerationConfiguration } from "../generation.service";
@Component({
  selector: 'map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit{
  test = 0;
  map: mapboxgl.Map;
  stateName:string;
  stylePattern:any;
  lockedStylePattern:any;
  precinctLockedStylePattern:any;
  restartImage:string;
  popupFilter = ['==', 'name', '']
  disableSavedMapList:boolean = false;
  numberOfDistricts:number = 8;
  lockedOpacityStops: Array<any>;
  lockedRegions:Object= {}
  _mapObject:any;
  @Input() mapTypeListLabel: string;
  @Input() savedMapListLabel:string;
  @Input() 
  set mapObject(map:any){
    console.log("MAP CHANGED");
    this._mapObject = map;
    this.resetAndReloadStyle()
  }
  get mapObject():any{
    return this._mapObject}
  @Input() congressionalDistricts: Object[]
  @Input() savedMapList:DropdownValue<any>[];
  @Input() mapTypeList:DropdownValue<String>[];
  @Input() popupCords:mapboxgl.LngLatLike;
  @Input() currPrecinct:any;
  @Input() mapColorPattern:any;
  @Input() flipped:boolean;
  @Input() algoPaused:boolean;
  @Input() disableMapTypeList:boolean = false;
  @Input() disableResetButton:boolean = false;
  @Input() genConfig:GenerationConfiguration;
  @Output() clicked: EventEmitter<any>
  @Output() mapTypeChanged: EventEmitter<any>;
  @Output() savedMapChanged: EventEmitter<any>;
  @Output() mapReset: EventEmitter<any>;
  @Output() updateGenConfig: EventEmitter<any>;
  constructor(
      private mapService: CdgMapService,
      private appProperties:AppProperties,
      private permPicker: CdgPermPickerComponent
      ) {
    this.clicked = new EventEmitter<any>();
    this.mapTypeChanged = new EventEmitter<any>();
    this.savedMapChanged = new EventEmitter<any>();
    this.mapReset = new EventEmitter<any>();
    this.updateGenConfig = new EventEmitter<any>();
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
        'fill-opacity': properties.mapOpacity,
      };
    this.reloadStyle();
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
    this.resetAndReloadStyle();
    this.mapTypeChanged.emit(event.value);
  }
  onSavedMapChanged(event){
    this.savedMapChanged.emit(event.value);
  }
  onMapClick(event){
    console.log("HELLO")
    let permConDist = this.genConfig.getPermConDist();
    let permPrecinct = this.genConfig.getPermPreceint()
    let data = event.features[0].properties;
    data.numberOfDistricts = 8;
    let precinctIsPerm;
    let districtIsPerm;
    // console.log("PRECINCT: " + data.precinctID)
    // console.log("DISTRICT: " + data.districtID)
    // let i;
    // for(i = 0; i < this._mapObject.features.length; i++){
    //   if(this._mapObject.features[i].properties.precinctID == data.precinctID){
    //     console.log("CHECKERINO DISTRICT: " + this._mapObject.features[i].properties.districtID)
    //   }
    // }
    if(permPrecinct.includes(data.precinctID)){
      precinctIsPerm = true;
    }
    else{
      precinctIsPerm = false;
    }
    if(permConDist.includes(data.districtID)){
      districtIsPerm = true;
    }
    else{
      districtIsPerm = false;
    }
    let ref = this.permPicker.generatePermPicker(precinctIsPerm, districtIsPerm,  data);
    ref.afterClosed().subscribe(result => {
      let res: any = result;
      if(result){
        this.setPermStyle(result.precinctIsLocked, data.precinctID, result.districtIsLocked, data.districtID)
        this.updateGenConfig.emit({
            precinct : result.precinctIsLocked, 
            precinctID : data.precinctID, 
            districtID : data.districtID,
            district : result.districtIsLocked,
            movedPrecinct: result.movedPrecinct
          })
      }
    });
  }

  setPermStyle(precinctIsLocked, precinctID, districtIsLocked, districtID){
        if(districtIsLocked == "true"){
          if(!this.lockedRegions[districtID]){
            this.lockedRegions[districtID] = "red";
            console.log("PUSHED LINE: " + this.lockedRegions)
          }
        }
        else{
          delete this.lockedRegions[districtID]
        }

        if(precinctIsLocked == "true"){
          if(!this.lockedRegions[precinctID]){
            this.lockedRegions[precinctID] = "red"
            console.log("PUSHED PRECINCT LINE: " + this.lockedRegions)
          }
        }
        else{
          delete this.lockedRegions[precinctID]
        }
        this.reloadStyle();
  }
  resetAndReloadStyle(){
    this.lockedRegions =  {}
    this.reloadStyle();
  }
  reloadStyle() {
    let ob ={"01" :"red"}
    let distStops  = this.lockedRegions;
    console.log("DISt STOPS: " +   JSON.stringify({ data: distStops}, null, 4))
      this.lockedStylePattern = {
        'line-color': ["case", ["has", ["get", "precinctID"], ["literal", (distStops)]], "red", ["has", ["get", "districtID"], ["literal", (distStops)]], "red", "black"],// ['has', ['get', 'districtID'], {'01':'red'}], ['get', ['get', 'districtID'], {'01':'red'}], 'black'],
        'line-width': ["case", ["has", ["get", "precinctID"], ["literal", (distStops)]], 1, ["has", ["get", "districtID"], ["literal", (distStops)]], 1, .05],
      };
          let properties = this.appProperties.getProperties();

      this.stylePattern =  {
        'fill-color': {
          type:'categorical',
          property: Constants.COLOR_PROPERTY,
          stops: properties.mapColorPattern,
          default: properties.defaultMapColor
        },
        'fill-opacity': properties.mapOpacity,
      };
 
  }

  resetMap(){
    this.mapReset.emit();
  }
}
