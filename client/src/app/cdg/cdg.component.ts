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
import { saveAs }                   from 'file-saver/FileSaver';
@Component({
  selector: 'app-cdg',
  templateUrl: './cdg.component.html',
  styleUrls: ['./cdg.component.scss']
})
export class CdgComponent implements OnInit {

  mapObject: Object;
  compareMapObject:Object;
  stateList: DropdownValue<State>[];
  savedMapList:DropdownValue<any>[];
  mapTypeList:DropdownValue<String>[];
  compare:boolean;
  pauseImage:string; 
  stopImage:string; 
  genConfig:GenerationConfiguration;
  algoRunning: boolean;
  algoPaused: boolean;
  selectedStateName:string;
  selectedStateId:number;
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
    this.pauseImage = this.appProperties.getProperties().pauseImage;
    this.stopImage = this.appProperties.getProperties().stopImage;
    this.setUpLabels(this.appProperties.getProperties());
    this.mapTypeList = new Array<DropdownValue<String>>();
    this.appProperties.getProperties().mapTypeListValues.forEach(mapTypeElement => {
      this.mapTypeList.push(new DropdownValue<String>(mapTypeElement[1], mapTypeElement[0]));
    });
    this.mapService.getStateList()
    .subscribe(stateList => {});
    this.stateList = [
      new DropdownValue<State>(new State("All", "0"), "All"),
      new DropdownValue<State>(new State("Minnesota", "27"), "Minnesota"),
      new DropdownValue<State>(new State("Arizona", "04"), "Arizona"),     
      new DropdownValue<State>(new State("Mississippi", "28"), "Mississippi"),
      new DropdownValue<State>(new State("Indiana", "18"), "Indiana"),
     ] 
  }
  precinctSelected(precinct){
    this.selectedPrecinct = precinct.f ;
    console.log(this.selectedPrecinct.districtID);
  }
  changeState(event){
    if(event.value.id == "0"){
      this.genConfig = null;
      this.getUnitedStates();
    }
    else{
      this.genConfig = new GenerationConfiguration()
      this.genConfig.setState(event.label);
      this.selectedStateName = event.label;
      this.selectedStateId = event.value.id;
      this.getState(event.value.id);
    }
  }
  
  getUnitedStates() {
    this.mapService.getUnitedStates()
     .subscribe(usData =>{
     	this.mapObject = usData;
     });
  }

  getState(chosenState: string){

    this.mapService.getMap(chosenState)
    .subscribe(stateData =>{
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
    this.mapService.setType(type);
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
  }
  playGenerationClicked(){
    this.genService.playGeneration();
  }
  savedMapClick(event){
    this.mapService.saveMap()
  }
  exportMap(){
        let blob = new Blob([this.mapObject], { type: 'application/json' });
        saveAs(blob, "test");
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

}
