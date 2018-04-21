import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'cdg-button',
  templateUrl: './cdg-button.component.html',
  styleUrls: ['./cdg-button.component.scss']
})
export class CdgButtonComponent implements OnInit {
  @Input() name: string;
  @Input() img:string;
  @Input() disabled:boolean = false;
  constructor() { }
  ngOnInit() {
  }
}
