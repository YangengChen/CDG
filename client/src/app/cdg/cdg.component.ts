import 'hammerjs'
import {  Injectable, 
         Component, 
         OnInit, 
         Input, 
         Output }                   from '@angular/core';
import { Observable }               from 'rxjs/Rx';
import { HttpClient }               from '@angular/common/http';
import { LoginService }             from "../pages/login/login.service";
import { Router }                   from "@angular/router";
import { GenerationService,
         GenerationConfiguration }  from "./generation.service";
import { Precinct}                  from "../cdg-objects/precinct";
import { State }                    from "../cdg-objects/state";
import { CdgMap }                   from "../cdg-objects/cdgmap";
import { DropdownValue }            from "../cdg-objects/dropdownvalue";
import { MapService }               from "./map/map.service";
import { AppProperties }            from '../app.properties'
import { Constants }                from "../constants";
import { saveAs }                   from 'file-saver/FileSaver';
@Component({
  selector: 'app-cdg',
  templateUrl: './cdg.component.html',
  styleUrls: ['./cdg.component.scss']
})
export class CdgComponent implements OnInit {
  statusCode: number;
  flipped=false;
  mapObject: Object;
  compareMapObject:Object;
  compareSelectedStateId: string;
  compareSelectedMapType:string;
  stateList: DropdownValue<State>[];
  savedMapList:DropdownValue<any>[];
  mapTypeList:DropdownValue<String>[];
  compare:boolean;
  pauseImage:string; 
  stopImage:string; 
  genConfig:GenerationConfiguration;
  algoRunning: boolean;
  algoPaused: boolean;
  selectedMapType:string;
  selectedStateName:string;
  selectedStateId:string;
  selectedPrecinct: Precinct;
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
  compactness:Number;
  constructor(
    private router:Router, 
    private loginService: LoginService, 
    private http: HttpClient,
    private genService: GenerationService,
    private mapService :MapService,
    private appProperties :AppProperties) { 
      this.loginService.isUserLoggedIn().subscribe(
        data => data ? null : this.router.navigate(["/"]),
        errorCode =>  this.statusCode = errorCode
      );
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
    this.compare = false;
    this.algoPaused = false;
    this.algoRunning = false;
    this.selectedMapType = Constants.INIT_MAP_TYPE;
    this.selectedStateId = Constants.FULLMAP_ID;
    this.compareSelectedMapType = Constants.INIT_MAP_TYPE;
    this.compareSelectedStateId = Constants.FULLMAP_ID;
    this.pauseImage = this.appProperties.getProperties().pauseImage;
    this.stopImage = this.appProperties.getProperties().stopImage;
    this.setUpLabels(this.appProperties.getProperties());
    this.mapTypeList = new Array<DropdownValue<String>>();
    this.stateList = new Array<DropdownValue<State>>();
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
      this.getState();
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
    }
  }
  getCompareUnitedStates() {
    this.mapService.getUnitedStates()
     .subscribe(usData =>{
     	this.compareMapObject = usData;
     });
  }

  getUnitedStates() {
    this.mapService.getUnitedStates()
     .subscribe(usData =>{
     	this.mapObject = usData;
     });
  }

  getState(){
    this.mapService.getMap(this.selectedStateId, this.selectedMapType)
    .subscribe(stateData =>{
      console.log(stateData);
        this.mapObject = stateData;
    });
  }
  updateRacialFairness(weight:number){
    this.genConfig.setRacialFairWeight(weight);
  }
  updateCompactness(weight:number){
    this.genConfig.setCompactnessWeight(weight);
  }
  updateContiguity(weight:number){
    this.genConfig.setContiguityWeight(weight);
  }
  updateEqualPopulation(weight:number){
    this.genConfig.setEqualPopWeight(weight);
  }
  updatePartisanFairness(weight:number){
    this.genConfig.setPartisanFairness(weight);
  }
  startGeneration(){
    if(this.genConfig != null){
      this.genService.startGeneration(this.genConfig)
      .subscribe(data =>{
        //TODO: DECIDE WHAT TO DO AFTER GENERATE START RETURN
        console.log(data);
      });
    }
    else{
      //TODO: ADD POPUP WARNING: NO STATE CHOSEN
    }
    this.algoRunning = true;
  }
  beginGenerationStatusCheck(){
    Observable.interval(5).subscribe( x => {
      this.genService.checkStatus()
      .subscribe( finished =>{
        if(finished){
          this.getFinishedMap();
        }
      })
    })
  }
  getFinishedMap(){
    this.mapService.getFinishedMap()
    .subscribe( finishedMap => {
      this.mapObject = finishedMap;
    })
  }
  mapTypeChanged(type:string){
    this.selectedMapType = type;
    this.getState();
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
  savedMapChanged(savedMap:string){
    
  }
  compareSavedMapChanged(compareSavedMap:string){

  }
  compareChangeStates(event){

  }
  compareToggle(event){
    this.compare = event.checked;
  }
  pauseGenerationClicked(){
    this.genService.pauseGeneration();
  }
  stopGenerationClicked(){
    this.genService.stopGeneration();
    this.algoRunning = false;
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
    this.genConfig.restartConfig();
    this.compactness = this.genConfig.getCompactnessWeight();
    console.log(this.compactness);
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
  toggleFlip(){
    this.flipped = !this.flipped;
  }

}
