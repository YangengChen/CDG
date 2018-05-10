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
    compactnessWeight:Number;
    contiguityWeight:Number;
    equalPopWeight: Number;
    racialFairWeight: Number;
    partisanFairWeight:Number;
    lockedPrecincts:any;
    lockedDistricts:Array<string>;
    constructor(){
        this.compactnessWeight = .4;
        this.contiguityWeight = .4;
        this.equalPopWeight = .2;
        this.racialFairWeight = 0.0;
        this.partisanFairWeight = 0.0;
        this.permConDist = new Array<string>();
        this.permPrecinct = new Array<string>();
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
    setCompactnessWeight(weight: Number){
        this.compactnessWeight = weight;
        console.log(this.compactnessWeight);
    }
    setContiguityWeight(weight:Number){
        this.contiguityWeight = weight;
    }
    setEqualPopWeight(weight:Number){
        this.equalPopWeight = weight;
    }
    setRacialFairWeight(weight:Number){
        this.racialFairWeight = weight;
    }
    getPartisanFairnessWeight(){
        return this.partisanFairWeight;
    }
    getCompactnessWeight(){
        return this.compactnessWeight;
    }
    getContiguityWeight(){
        return this.contiguityWeight;
    }
    getEqualPopWeight(){
        return this.equalPopWeight;
    }
    getRacialFairWeight(){
        return this.racialFairWeight;
    }
    restartConfig(){
        this.compactnessWeight = .4;
        this.contiguityWeight = .4;
        this.equalPopWeight = .2;
        this.partisanFairWeight = 0.0;
        this.permConDist = new Array<string>();
        this.permPrecinct = new Array<string>();
    }
}