export class DropdownValue<T>{
    value: T;
    label:string;
    constructor(val: T, label:string){
        this.value = val;
        this.label = label;
    }
}
