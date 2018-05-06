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
    constructor(){
        this.compactnessWeight = .1;
        this.contiguityWeight = .1;
        this.equalPopWeight = .1;
        this.racialFairWeight = .3;
        this.partisanFairWeight = .5;
        this.permConDist = new Array<string>();
        this.permConDist.push();
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
        this.compactnessWeight = 50;
        this.contiguityWeight = 50;
        this.equalPopWeight = 50;
        this.racialFairWeight = 50;
        this.partisanFairWeight = 50;
        this.permConDist = new Array<string>();
        this.permPrecinct = new Array<string>();
    }
}