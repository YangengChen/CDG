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
  onMapClick(event){
    console.log("HELLO")
    let permConDist = this.genConfig.getPermConDist();
    let permPrecinct = this.genConfig.getPermPreceint()
    let data = event.features[0].properties;
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
    let ref = this.permPicker.generatePermPicker(precinctIsPerm, districtIsPerm, data);
    ref.afterClosed().subscribe(result => {
      let res: any = result;
      if(result){
        this.updateGenConfig.emit({
            precinct : result.precinctIsLocked, 
            precinctID : data.precinctID, 
            districtID : data.districtID,
            district : result.districtIsLocked 
          })
      }
    });
    

  }
  resetMap(){
    this.mapReset.emit();
  }
}
