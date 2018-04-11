import {Injectable, Component, OnInit, Input} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {LoginService } from "../pages/login/login.service";
import { Router } from "@angular/router";
import { GenerationService, GenerationConfiguration } from "./generation.service";
import { Precinct} from "../cdg-objects/precinct";
import { State } from "../cdg-objects/state";
import { CdgMap } from "../cdg-objects/cdgmap";
import { DropdownValue } from "../cdg-objects/dropdownvalue";
import { MapService } from "./map/map.service";
@Component({
  selector: 'app-cdg',
  templateUrl: './cdg.component.html',
  styleUrls: ['./cdg.component.scss']
})
export class CdgComponent implements OnInit {
  @Input() items:[{title:"Testing"}, {title:"menu"}];
  stateList: DropdownValue<State>[];
  savedMapList:DropdownValue<any>[];
  mapTypeList:DropdownValue<String>[];
  url = "http://localhost:8080/api/map/states";
  dat = ""

  genConfig:GenerationConfiguration;
  mapObject: Object;
  selectedStateName:string;
  selectedStateId:number;
  selectedPrecinct: Precinct;
  constructor(
    private router:Router, 
    private loginService: LoginService, 
    private http: HttpClient,
    private genService: GenerationService,
    private mapService :MapService) { 
  }
  logout(){
    // TODO: NEEDS A LOGOUT
    this.router.navigateByUrl("/");
  }
  ngOnInit() {
    this.mapTypeList = [new DropdownValue<String>("State", "State"),new DropdownValue<String>("Precinct", "Precinct") ];
    this.stateList = [
      new DropdownValue<State>(new State("All", 0), "All"),
      new DropdownValue<State>(new State("Minnesota",1000), "Minnesota"),
      new DropdownValue<State>(new State("Wisconson", 2000), "Wisconson"),
      new DropdownValue<State>(new State("Washington", 3000), "Washington")
     ] 
     this.getState("1"); 
}

  precinctSelected(precinct){
    this.selectedPrecinct = precinct.f ;
    console.log(this.selectedPrecinct.CongDist);
  }

  changeState(event){
    this.genConfig = new GenerationConfiguration()
    this.genConfig.setState(event.name);
    this.selectedStateName = event.name;
    this.selectedStateId = event.id;
  }

  getState(chosenState: string){
    this.mapService.getState(chosenState)
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


  startGeneration(){
    if(this.genConfig != null){
      this.genService.startGeneration(this.genConfig)
      .subscribe(data =>{
        //TODO: DECIDE WHAT TO DO AFTER GENERATE START RETURN

      });
    }
    else{
      //TODO: ADD POPUP WARNING: NO STATE CHOSEN
    }
  }
}
