import { CdgMapData } from "./cdgmapdata";

export class State implements CdgMapData {
    label: String;
    mapType:String;
    id:number;

    constructor(label: string, id:number){
        this.label = name;
        this.id = id;
    }
}