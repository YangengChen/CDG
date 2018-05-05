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
        this.http.post(Constants.STOP_URL, {}, {withCredentials:true});
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
    private permConDist: Array<number>;
    private permPrecinct: Array<number>;
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
        this.permConDist = [];
        this.permPrecinct = [];
    }
    setState(state:string){
        this.stateId = state;
    }
    setPermConDist(id:number){
        this.permConDist.push(id);
    }
    removePermConDist(id:number){
        if(this.permConDist.includes(id))
            this.permConDist.splice(this.permConDist.indexOf(id), 1);
    }
    setPermPrecinct(id:number){
        this.permPrecinct.push(id);
    }
    removePermPrecinct(id:number){
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
        this.permConDist = Array<number>();
        this.permPrecinct = Array<number>();
    }
}