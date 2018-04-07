import { Component, OnInit, Input, Output, EventEmitter, ElementRef } from '@angular/core';

export class DropdownValue {
  value:string;
  label:string;

  constructor(value:string, label:string){
    this.value = value;
    this.label = label;
  }
}

@Component({
  selector: 'cdg-dropdown',
  templateUrl: './cdg-dropdown.component.html',
  styleUrls: ['./cdg-dropdown.component.scss']
})
export class CdgDropdownComponent implements OnInit {
  @Input() 
  chosen: string;

  @Input()
  name: string;

  @Input()
  values: DropdownValue[];


  @Output()
  change: EventEmitter<string>;

  constructor(private ref: ElementRef){ 
    this.change = new EventEmitter<string>();
  }

  ngOnInit() {
  }

  select(value){
    this.change.emit(value);
  }
}
