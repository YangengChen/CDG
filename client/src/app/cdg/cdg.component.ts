import 'hammerjs'
import {  Injectable, 
         Component, 
         OnInit, 
         Input, 
         Output,
         ViewChild
        }                     from '@angular/core';
import { Observable }                 from 'rxjs/Rx';
import { HttpClient }                 from '@angular/common/http';
import { LoginService }               from "../pages/login/login.service";
import { Router }                     from "@angular/router";
import { GenerationService,
         GenerationConfiguration }    from "./generation.service";
import { Precinct}                    from "../cdg-objects/precinct";
import { State }                      from "../cdg-objects/state";
import { CdgMap }                     from "../cdg-objects/cdgmap";
import { CdgSnackbarComponent,
         SnackbarEnum }               from "../cdg-ui/cdg-snackbar/cdg-snackbar.component";
import { CdgSavemapComponent }        from "../cdg-ui/cdg-savemap/cdg-savemap.component";
import { DropdownValue }              from "../cdg-objects/dropdownvalue";
import { CdgMapService }                 from "./map/map.service";
import { AppProperties }              from '../app.properties'
import { Constants }                  from "../constants";
import { saveAs }                     from 'file-saver/FileSaver';
import { StartGenerationSuccessComponent } from '../cdg-ui/cdg-snackbar/start-generation-success/start-generation-success.component';
import { StartGenerationFailedComponent } from '../cdg-ui/cdg-snackbar/start-generation-failed/start-generation-failed.component';
import * as mapboxgl      from 'mapbox-gl';
import { MapComponent }                 from "./map/map.component";
import { DecimalPipe } from "@angular/common"
import { PipeSort } from '../pipe-sort/pipe-sort.pipe';

@Component({
  selector: 'app-cdg',
  templateUrl: './cdg.component.html',
  styleUrls: ['./cdg.component.scss'],
})
export class CdgComponent implements OnInit {


  interval = null;
  spinnerValue:number;
  spinnerMode = "indeterminate";
  generatedConDistData:any;
  conDistData:any;
  steps: Array<Object>;
  @ViewChild("map") map:MapComponent;
  @ViewChild("generatedMap") generatedMap:MapComponent;
  mapObject: any;  
  originalMapObject:any;
  currCheck:any;

  //MAIN COMPARE MAP DATA
  compareMapObject:Object;
  compareConDistData:Object;
  compareData:any;
  compareSelectedStateId: string;
  compareSelectedMapType:string;

  //GENERATED COMPARE MAP DATA
  generatedCompareMapObject:Object;
  generatedCompareConDistData:any;
  generatedCompareData:any;
  generatedCompareSelectedStateId: string;
  generatedCompareSelectedMapType:string;
  selectedGenerationStateId:string;


  //MAIN MAP DATA
  stateData:any;
  selectedMapType:string;
  selectedStateName:string;
  selectedStateId:string;
  selectedPrecinct: Precinct;

  generateMapObject:Object;
  precinctToNum:any = {};

  //LISTS
  stateList: DropdownValue<State>[];
  savedMapList:DropdownValue<any>[];
  mapTypeList:DropdownValue<String>[];

  //CONFIG
  genConfig:GenerationConfiguration;


  // BOLEAN FLAGS
  algoRunning: boolean;
  algoPaused: boolean;
  startingGeneration:boolean = false;
  flipped:boolean=false;
  compare:boolean;
  generatedCompare:boolean;
  canGenerate: boolean;


  //LABELS 
  mapTypeListLabel:string;
  savedMapListLabel:string;
  stateListLabel:string;
  compactnessSliderLabel:string;
  contiguitySliderLabel:string;
  equalPopulationSliderLabel:string;
  partisanFairnessSliderLabel:string;
  raicalFairnessSliderLabel:string;
  generateDistrictsButtonLabel:string;
  replayGenerationButtonLabel:string;
  generateTabLabel:string;
  informationTabLabel:string;
  fileTabLabel:string;
  pauseImage:string; 
  stopImage:string; 

  constructor(
    private router        :Router, 
    private loginService  : LoginService, 
    private http          : HttpClient,
    private genService    : GenerationService,
    private mapService    : CdgMapService,
    private appProperties : AppProperties,
    private snackBar      : CdgSnackbarComponent,
    private saveModal     : CdgSavemapComponent ) { 
  }

  logout(){
    this.loginService.logout().subscribe(
      (data) =>{
        this.router.navigate(["/"]);
      },
      (err) =>{
      }
    )
  }
  ngOnInit() {
    this.flipped = false;
    this.compare = false;
    this.generatedCompare = false;
    this.algoPaused = false;
    this.algoRunning = false;
    this.canGenerate = false;
    this.selectedMapType = Constants.INIT_MAP_TYPE;
    this.selectedStateId = Constants.FULLMAP_ID;
    this.compareSelectedMapType = Constants.INIT_MAP_TYPE;
    this.compareSelectedStateId = Constants.FULLMAP_ID;
    this.generatedCompareSelectedMapType = Constants.INIT_MAP_TYPE;
    this.generatedCompareSelectedStateId = Constants.FULLMAP_ID;
    this.pauseImage = this.appProperties.getProperties().pauseImage;
    this.stopImage = this.appProperties.getProperties().stopImage;
    this.setUpLabels(this.appProperties.getProperties());
    this.mapTypeList = new Array<DropdownValue<String>>();
    this.stateList = new Array<DropdownValue<State>>();
    this.savedMapList = new Array<DropdownValue<String>>();
    this.steps = new Array<Object>();
    this.stateList.push(Constants.UNITED_STATES_DROPDOWNVALUE);
    this.mapService.getStateList()
    .subscribe((stateList:any) => {
      stateList.forEach((element:any) => {
        this.stateList.push(new DropdownValue<State>(new State(element.name, element.publicID), element.name));
      });
    });
    this.appProperties.getProperties().mapTypeListValues.forEach(mapTypeElement => {
      this.mapTypeList.push(new DropdownValue<String>(mapTypeElement[1], mapTypeElement[0]));
    });
    this.getUnitedStates();
    this.getCompareUnitedStates();
    this.generateMapObject = this.mapObject
    this.genConfig = new GenerationConfiguration();
    this.mapService.getUserMapList()
    .subscribe((savedMapList:any) => {
        savedMapList.forEach((element:any) => {
        this.savedMapList.push(new DropdownValue<String>(element.name, element.publicID));
      });
    });
  }



  precinctSelected(precinct){
    this.selectedPrecinct = precinct.f ;
    console.log(this.selectedPrecinct.districtID);
  }

  changeState(event){
    if(event.value.id == Constants.FULLMAP_ID){
      this.genConfig = null;
      this.getUnitedStates();
    }
    else{
      this.genConfig = new GenerationConfiguration()
      this.genConfig.setState(event.value.id);
      this.selectedStateName = event.label;
      this.selectedStateId = event.value.id;
      this.canGenerate = true;
      this.getState();
      this.getStateData();
      this.getCongressionalDistrictData();
    }
  }
  changeCompareState(event){
    if(event.value.id == Constants.FULLMAP_ID){
      this.compareSelectedStateId = Constants.FULLMAP_ID
      this.getCompareUnitedStates();
    }
    else{
      this.compareSelectedStateId = event.value.id;
      this.getCompareState();
      this.getCompareData();
      this.getCompareCongressionalDistrictData();
    }
  }

  changedGeneratedCompareState(event){
    if(event.value.id == Constants.FULLMAP_ID){
      this.genConfig = null;
      this.getGeneratedCompareUnitedStates();
    }
    else{
      this.genConfig = new GenerationConfiguration()
      this.generatedCompareSelectedStateId = event.value.id;
      this.getGeneratedCompareState();
      this.getGeneratedCompareData();
      this.getGeneratedCompareCongressionalDistrictData();
    }
  }

  /* COMPARE FUNCTIONS

    Used for manipulating all the data for the main compare map

  */
  getCompareCongressionalDistrictData(){
    this.mapService.getData(this.compareSelectedStateId, "congressional")
    .subscribe(stateData => {
      this.compareConDistData = stateData;
    })   
  }
  getCompareData(){
    this.mapService.getData(this.compareSelectedStateId, "state")
    .subscribe(stateData => {
      this.compareData = stateData;
    })   
  }
  getCompareUnitedStates() {
    this.mapService.getUnitedStates()
     .subscribe(usData =>{
     	this.compareMapObject = usData;
     });
  }
  compareMapTypeChanged(type:string){
    this.compareSelectedMapType = type;
    this.getCompareState();
  }
  getCompareState(){
    this.mapService.getMap(this.compareSelectedStateId, this.compareSelectedMapType)
    .subscribe(stateData =>{
      console.log(stateData);
        this.compareMapObject = stateData;
    });
  }
  compareToggle(event){
    this.compare = event.checked;
  }



  /* MAIN MAP GETTER FUNCTIONS

    Used for getting all the data on the main map

  */
  getUnitedStates() {
    this.canGenerate = false;
    this.mapService.getUnitedStates()
     .subscribe(usData =>{
     	this.mapObject = usData;
     });
  }
  getState(){
    this.mapService.getMap(this.selectedStateId, this.selectedMapType)
    .subscribe(stateData =>{
        this.mapObject = stateData;
        this.originalMapObject = stateData;
        this.mapReset()
    });
  }
  getStateData(){
    this.mapService.getData(this.selectedStateId, "state")
    .subscribe(stateData => {
      this.stateData = stateData;
    })
  }
  getCongressionalDistrictData(){
    this.mapService.getData(this.selectedStateId, "congressional")
    .subscribe(conData => {
      this.conDistData = conData;
    })
  }


  /* GENERTATED MAP FUNCTINOS

    Functions used for manipulating generated maps

  */
  getFinishedMap(){
    this.mapService.getFinishedMap("congressional")
    .subscribe( finishedMap => {
      this.generateMapObject = finishedMap;
    })
  }
  getFinishedData(){
    this.mapService.getFinishedData("congressional")
    .subscribe( finishedData => {
      this.generatedConDistData = finishedData;
    })  
  }


  //GENERATED COMPARE STUFF
  compareGeneratedToggle(event){
    this.generatedCompare  = event.checked;
  }
  getGeneratedCompareState(){
    this.mapService.getMap(this.selectedGenerationStateId, this.generatedCompareSelectedMapType)
    .subscribe(stateData =>{
        this.generatedCompareMapObject = stateData;
    });
  }
  generatedCompareMapTypeChanged(type:string){
    this.generatedCompareSelectedMapType = type;
    this.getGeneratedCompareState();
  }
  getGeneratedCompareCongressionalDistrictData(){
    this.mapService.getData(this.selectedGenerationStateId, "congressional")
    .subscribe(stateData => {
      this.generatedCompareConDistData = stateData;
      if(this.currCheck){
        for(let i = 0; i < this.currCheck.districtsGoodness.length; i++){
          this.generatedCompareConDistData.districts[i].goodness = this.currCheck.startDistrictsGoodness[i].goodness;
        }
      } 
    }) 
  }
  getGeneratedCompareData(){
    this.mapService.getData(this.selectedGenerationStateId, "state")
    .subscribe(stateData => {
      this.generatedCompareData = stateData;
    })   
  }
  getGeneratedCompareUnitedStates() {
    this.mapService.getUnitedStates()
     .subscribe(usData =>{
     	this.generatedCompareMapObject = usData;
     });
  }

  // GEN CONFIG UPDATE FUNCTINOS
  updateContiguity(weight:number){
    this.genConfig.setContiguityWeight(weight);
  }
  updateEqualPopulation(weight:number){
    this.genConfig.setEqualPopWeight(weight);
  }
  updatePartisanFairness(weight:number){
    this.genConfig.setPartisanFairness(weight);
  }
  updateSchwartz(weight:number){
    this.genConfig.setSchwartz(weight);
  }
  updateHull(weight:number){
    this.genConfig.setHull(weight);
  }
  updateReock(weight:number){
    this.genConfig.setReock(weight);
  }


  /* GENERATION FUNCTIONS

    Functions used in generating the map

  */
  startGeneration(){
    let configCheck = this.startGenerationCheck();
    if(configCheck == null){
      this.selectedGenerationStateId = this.selectedStateId;
      this.startingGeneration = true;
      this.generateMapObject = this.mapObject;
      if(this.mapObject.features != null){
        for(var i = 0; i < this.mapObject.features.length; i++){
          this.precinctToNum[this.mapObject.features[i].properties.precinctID] = i;
        }     
      } 
      this.genService.startGeneration(this.genConfig)
      .subscribe(data =>{
        let status:any = data;
        if(status == "INPROGRESS"){
          this.beginGenerationStatusCheck();
          this.snackBar.generateSnackbar(SnackbarEnum.START_GENERATION_SUCCESS)
          this.algoRunning = true;
          this.startingGeneration = false;
          this.flipped = true;
          this.getGeneratedCompareState();
          this.getGeneratedCompareCongressionalDistrictData();
          this.getGeneratedCompareData();
        }
      });
    }
    else{
          this.snackBar.generateSnackbar(configCheck);
    }
  }
  saveGeneration(){
    let ref:any = this.saveModal.generateSaveMap();
    ref.afterClosed().subscribe(result => {
      let res: any = result;
      console.log(JSON.stringify(result));
      if(result != null){
        console.log("SENDING REQUEST");
        this.genService.saveGeneration(result.savedName)
        .subscribe();
      }
    });  }
  startGenerationCheck(){
    if( ( this.genConfig.getEqualPopWeight().valueOf()
        + this.genConfig.getPartisanFairnessWeight().valueOf() 
        + this.genConfig.getReock().valueOf()
        + this.genConfig.getSchwartz().valueOf() 
        + this.genConfig.getHull().valueOf()) != 1){
            return SnackbarEnum.WEIGHT_FAILURE;
    }
    return null;
  }
  beginGenerationStatusCheck(){
    this.interval = setInterval( () => {
      this.startingGeneration = false;
      this.genService.checkStatus()
      .subscribe(data => {
        let check:any = data;
        if(check.status == "COMPLETE"){
          this.getFinishedMap()
          this.getFinishedData();
          this.getGeneratedCompareCongressionalDistrictData()
          clearInterval(this.interval)
          this.snackBar.generateSnackbar(SnackbarEnum.GENERATION_FINISHED);
          this.algoRunning = false;
        }
        else{
          this.currCheck = check;
          this.steps.push(check);
          this.showSteps(check.precinctToDistrict)
        }
      })
    }, 3000);
  }


  showSteps(moved){
    let newMap:any = this.mapObject;
    let features:Array<any> = newMap.features;
    let i;
    for(i = 0; i < moved.length; i++){
      this.mapObject.features[this.precinctToNum[moved[i].precinctID]].properties.districtID = moved[i].districtID;
    }
    this.changeGeneratedMap(this.mapObject);
  }
  changeGeneratedMap(theNewMap:any){
    console.log("CHANING MAP: " + theNewMap.type);
    //this.generatedMap.mapObject = theNewMap;
    //this.generateMapObject = theNewMap;
    this.generatedMap.setData(theNewMap);
    //this.generatedMap.resetAndReloadStyle();
  }
  changeMap(theNewMap:any){
    // console.log("REALLY CHANGING MAP: " + theNewMap.type)
    // this.mapObject = theNewMap;
    this.map.setData(theNewMap);
    // this.map.resetAndReloadStyle();
  }
  mapTypeChanged(type:string){
    this.selectedMapType = type;
    this.getState();
  }
  savedMapChanged(mapName){
    this.mapService.getUserMap(mapName, "precinct")
    .subscribe(stateData =>{
    this.mapObject = stateData;
    this.originalMapObject = stateData;
    this.mapReset()
    });  
  }
  pauseGenerationClicked(){
    this.genService.pauseGeneration();
  }
  stopGenerationClicked(){
    this.genService.stopGeneration()
    .subscribe(data =>{
      // this.algoRunning = false;
      // clearInterval(this.interval);
    });
  }
  playGenerationClicked(){
    this.genService.playGeneration();
  }
  savedMapClick(event){
    this.mapService.saveMap()
  }
  exportMap(){
        let blob = new Blob([JSON.stringify(this.mapObject)], { type: Constants.EXPORT_HEADERS });
        saveAs(blob, this.appProperties.getProperties().exportMapName);
  }
  saveMap(){

  }
  mapReset(){
    let newMap:string = this.originalMapObject;
    this.changeMap(newMap);
    this.map.reloadStyle();
    this.genConfig.restartConfig();
  }
  toggleFlip(){
    this.flipped = !this.flipped;
  }
  updateGenConfig(updates){
      if(updates.precinct === "true"){
        this.genConfig.setPermPrecinct(updates.precinctID)
      }
      else{
        this.genConfig.removePermPrecinct(updates.precinctID)
      }

      if (updates.district == "true"){
        this.genConfig.setPermConDist(updates.districtID);
      }
      else {
        this.genConfig.removePermConDist(updates.districtID);
      }
      console.log(updates.movedPrecinct);
      if(updates.movedPrecinct && updates.movedPrecinct[1] != null){
        this.genConfig.setPrecinctToDistrict(updates.movedPrecinct[0], updates.movedPrecinct[1])
        let newMap:any = this.mapObject;
        let features:Array<any> = newMap.features;
        let i;
        for( i = 0; i < features.length; i++){
          //console.log("PRECINCTID: " + JSON.stringify(features[i], null, 4))
          if(features[i].properties.precinctID == updates.precinctID){
            features[i].properties.districtID = updates.movedPrecinct[1];
            break;
          }
        }
        this.changeMap(newMap);
        //console.log("YASS: " + JSON.stringify({data:this.map.mapObject.features[i].properties}, null, 4))
      }
      // console.log("PRECINCT LOCK: " + updates.precinctID)
      // console.log("DISTRICT LOCK: " + updates.districtID)
      // console.log("NEW DISTRICT LOCKED: " + this.genConfig.getPermConDist())
      // console.log("NEW PRECINCT LOCKED: " + this.genConfig.getPermPreceint())
  }
  
  backToFront(){
    this.flipped = false;
  }

  exitGeneration(){
    this.steps = new Array<Object>();
    this.genConfig.restartConfig();
    this.selectedMapType = "state";
    this.getUnitedStates();
    this.backToFront();
  }


  countyToggle(event){
    this.genConfig.setSameCounty(event.checked);
  }




  setUpLabels(properties:any){
    this.mapTypeListLabel = properties.mapTypeListLabel;
    this.savedMapListLabel = properties.savedMapListLabel;
    this.stateListLabel = properties.stateListLabel;
    this.compactnessSliderLabel = properties.compactnessSliderLabel;
    this.contiguitySliderLabel = properties.contiguitySliderLabel;
    this.equalPopulationSliderLabel = properties.equalPopulationSliderLabel;
    this.partisanFairnessSliderLabel = properties.partisanFairnessSliderLabel;
    this.raicalFairnessSliderLabel = properties.raicalFairnessSliderLabel;
    this.generateDistrictsButtonLabel = properties.generateDistrictsButtonLabel;
    this.replayGenerationButtonLabel = properties.replayGenerationButtonLabel;
    this.generateTabLabel = properties.generateTabLabel;
    this.informationTabLabel = properties.informationTabLabel;
    this.fileTabLabel = properties.fileTabLabel;
  }
}
