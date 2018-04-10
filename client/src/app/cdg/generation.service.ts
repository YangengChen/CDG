import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
@Injectable()
export class GenerationService{
    generateUrl: string = "";
    constructor(private http: HttpClient){
    }
    startGeneration(config:GenerationConfiguration){
        return this.http.post(this.generateUrl, config.getJsonified());
    }

}

export class GenerationConfiguration {
    private state: string;
    private permConDist: Array<number>;
    private permPrecinct: Array<number>;
    private compactnessWeight:Number;
    private contiguityWeight:Number;
    private equalPopWeight: Number;
    private racialFairWeight: Number;
    constructor(){

    }

    setState(state:string){
        this.state = state;
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
    setCompactnessWeight(weight: Number){
        this.compactnessWeight = weight;
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
    getJsonified(){
        return JSON.stringify(this);
    }
}