import { CdgMapData } from "./cdgmapdata";

export class State implements CdgMapData {
    label: String;
    mapType: String;
    id: String;

    constructor(label: string, id: string){
        this.label = name;
        this.id = id;
    }
}