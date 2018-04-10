import { CdgMap } from "./cdgmap";

export class State implements CdgMap {
    label: String;
    mapType:String;
    id:number;

    constructor(label: string, id:number){
        this.label = name;
        this.id = id;
    }
}