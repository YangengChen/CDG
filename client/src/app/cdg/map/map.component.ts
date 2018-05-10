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
  districtLockedStylePattern:any;
  precinctLockedStylePattern:any;
  restartImage:string;
  popupFilter = ['==', 'name', '']
  disableSavedMapList:boolean = false;
  numberOfDistricts:number = 8;
  lockedOpacityStops: Array<any>;
  districtLockedFillStops: Array<Array<any>>;
  precinctLockedFillStops: Array<Array<any>>;
  _mapObject:mapboxgl.GeoJSONSource;
  @Input() mapTypeListLabel: string;
  @Input() savedMapListLabel:string;
  @Input() 
  set mapObject(map:mapboxgl.GeoJSONSource){
    this.resetAndReloadStyle()
    this._mapObject = map;
  }
  get mapObject():mapboxgl.GeoJSONSource{return this._mapObject}
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
    this.lockedOpacityStops = new Array<any>();
    this.lockedOpacityStops.push(["01", .7])
    this.districtLockedFillStops = new Array<Array<any>>();
    this.districtLockedFillStops.push(["00", "black"])
    this.precinctLockedFillStops = new Array<Array<any>>();
    this.precinctLockedFillStops.push(["00", "black"])
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
    this.districtLockedFillStops = new Array<Array<any>>();
    this.districtLockedFillStops.push(["00", "black"])
    this.precinctLockedFillStops = new Array<Array<any>>();
    this.precinctLockedFillStops.push(["00", "black"])
    this.districtLockedStylePattern = {
        'line-color': {
        type:'categorical',
        property: Constants.COLOR_PROPERTY,
        stops: this.districtLockedFillStops,
        default: "black"
      },
      'line-opacity': 0
    };
    this.precinctLockedStylePattern = {
        'line-color': {
          type:'categorical',
          property: "precinctID",
          stops: this.precinctLockedFillStops,
          default: "black"
        },
        'line-opacity': .7
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
    console.log("PRECINCT: " + data.precinctID)
    console.log("DISTRICT: " + data.districtID)
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
            district : result.districtIsLocked 
          })
      }
    });
  }

  setPermStyle(precinctIsLocked, precinctID, districtIsLocked, districtID){
        if(districtIsLocked == "true"){
          if(!this.districtLockedFillStops.includes(districtID)){
            this.districtLockedFillStops.push([districtID, "red"]);
            console.log("PUSHED LINE: " + this.districtLockedFillStops[0])
          }
        }
        else{
          this.districtLockedFillStops.splice(this.districtLockedFillStops.indexOf([districtID, "red"]), 1);
        }

        if(precinctIsLocked == "true"){
          if(!this.precinctLockedFillStops.includes(precinctID)){
            this.precinctLockedFillStops.push([precinctID, "red"]);
            console.log("PUSHED PRECINCT LINE: " + this.precinctLockedFillStops[0])
          }
        }
        else{
          this.precinctLockedFillStops.splice(this.precinctLockedFillStops.indexOf([precinctID, "red"]), 1);
        }
        this.reloadStyle();
  }
  resetAndReloadStyle(){
    this.districtLockedFillStops = new Array<Array<any>>(["00", "black"]);
    this.precinctLockedFillStops = new Array<Array<any>>(["00", "black"]);
    this.reloadStyle();
  }
  reloadStyle() {
    let ob ={"01" :"red"}
    let distStops:Array<Array<any>> = this.districtLockedFillStops;
    if(distStops.length == 0)
      distStops.push(["00", "black"])
    let precStops:Array<Array<any>> = this.precinctLockedFillStops;
    if(precStops.length == 0)
      precStops.push(["00", "black"])
    console.log("PREC STOPS: " + precStops)
    console.log("DISt STOPS: " + distStops)
      this.districtLockedStylePattern = {
        'line-color': ["case", ["has", ["get", "districtID"], ["literal", ({"01":"red"})]], "red", "black"],// ['has', ['get', 'districtID'], {'01':'red'}], ['get', ['get', 'districtID'], {'01':'red'}], 'black'],
        'line-opacity': ["case", !["has", ["get", "districtID"], ["literal", ({"01":"red"})]], 0, .7]
      };
      this.precinctLockedStylePattern = {
          'line-color': {
            type:'categorical',
            property: "precinctID",
            stops: precStops,
            default: "black"
          },
          'line-opacity': 0
      };
  }

  resetMap(){
    this.mapReset.emit();
  }
}
