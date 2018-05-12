import { Injectable, Input, Output } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Constants } from "../constants";
@Injectable()

export class GenerationService{
    constructor(private http: HttpClient){
    }
    startGeneration(config:GenerationConfiguration){
        return this.http.post(Constants.START_URL, JSON.stringify(config),{headers:{'Content-Type': 'application/json'},  withCredentials:true});
    }
    stopGeneration(){
        console.log("STOPPING")
        return this.http.post(Constants.STOP_URL, {}, {withCredentials:true});
    }
    pauseGeneration(){
        this.http.post(Constants.PAUSE_URL, {}, {withCredentials:true});
    }
    playGeneration(){
        this.http.post(Constants.PLAY_URL, {}, {withCredentials:true});
    }
    checkStatus(){
        return this.http.get(Constants.STATUS_URL, {withCredentials:true});
    }
}
export class GenerationConfiguration {
    private stateId: string;
    private permConDist: Array<string>;
    private permPrecinct: Array<string>;

    schwartzbergWeight:Number;
    hullRatioWeight:Number;
    reockWeight:Number;
    contiguityWeight:Number;
    equalPopWeight: Number;
    partisanFairWeight:Number;
    lockedPrecincts:any;
    lockedDistricts:Array<string>;
    precinctToDistrict:Map<String, String>;
    constructor(){
        this.contiguityWeight = .4;
        this.equalPopWeight = .2;
        this.partisanFairWeight = 0.4;
        this.reockWeight = 0.0;
        this.schwartzbergWeight = 0.0;
        this.hullRatioWeight = 0.0;
        this.permConDist = new Array<string>();
        this.permPrecinct = new Array<string>();
        this.precinctToDistrict = new Map<String, String>()
    }

    addMovedPrecinct(starting, movedTo){
        this.precinctToDistrict.set(starting, movedTo);
    }
    setState(state:string){
        this.stateId = state;
    }
    setPermConDist(id:string){
        if(!this.permConDist.includes(id))
            this.permConDist.push(id);
    }
    getPermConDist(){
        return this.permConDist;
    }
    getPermPreceint(){
        return this.permPrecinct;
    }
    removePermConDist(id:string){
        if(this.permConDist.includes(id))
            this.permConDist.splice(this.permConDist.indexOf(id), 1);
    }
    setPermPrecinct(id:string){
        if(!this.permPrecinct.includes(id))
            this.permPrecinct.push(id);
    }
    removePermPrecinct(id:string){
        if(this.permPrecinct.includes(id))
            this.permPrecinct.splice(this.permPrecinct.indexOf(id), 1);
    }
    setPartisanFairness(weight:Number){
        this.partisanFairWeight = weight;
    }

    setContiguityWeight(weight:Number){
        this.contiguityWeight = weight;
    }
    setEqualPopWeight(weight:Number){
        this.equalPopWeight = weight;
    }
    getPartisanFairnessWeight(){
        return this.partisanFairWeight;
    }
    getContiguityWeight(){
        return this.contiguityWeight;
    }
    getEqualPopWeight(){
        return this.equalPopWeight;
    }
    getSchwartz(){
        return this.schwartzbergWeight;
    }
    setSchwartz(weight){
        this.schwartzbergWeight = weight;
    }
    getHull(){
        return this.hullRatioWeight;
    }
    setHull(weight){
        this.hullRatioWeight = weight;
    }
    getReock(){
        return this.reockWeight;
    }
    setReock(weight){
        this.reockWeight = weight;
    }
    restartConfig(){
        this.contiguityWeight = 0.4;
        this.equalPopWeight = 0.2;
        this.partisanFairWeight = 0.4;
        this.reockWeight = 0.0;
        this.schwartzbergWeight = 0.0;
        this.hullRatioWeight = 0.0;
        this.permConDist = new Array<string>();
        this.permPrecinct = new Array<string>();
    }
}