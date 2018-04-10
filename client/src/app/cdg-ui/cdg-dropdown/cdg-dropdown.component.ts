import { Component, OnInit, Input, Output, EventEmitter, ElementRef } from '@angular/core';
import { DropdownValue } from "../../objects/dropdownvalue";

@Component({
  selector: 'cdg-dropdown',
  templateUrl: './cdg-dropdown.component.html',
  styleUrls: ['./cdg-dropdown.component.scss']
})
export class CdgDropdownComponent implements OnInit {
  @Input() 
  selected: string;

  @Input()
  name: string;

  @Input()
  values: DropdownValue<any>[];

  @Input() disabled:boolean = false;

  @Output()
  change: EventEmitter<any>;

  constructor(private ref: ElementRef){ 
    this.change = new EventEmitter<any>();
    this.values = []
  }

  ngOnInit() {
    if(this.values && this.values.length > 0)
      this.selected = this.values[0].label;
  }

  select(value:any){
    this.change.emit(value);
    this.selected = value.label;
  }
}

