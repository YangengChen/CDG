import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'cdg-button',
  templateUrl: './cdg-button.component.html',
  styleUrls: ['./cdg-button.component.scss']
})
export class CdgButtonComponent implements OnInit {
  @Input() name: string;
  @Input() disabled:boolean = false;
  @Output()
  chosen: EventEmitter<string> = new EventEmitter<string>();

  constructor() { }

  ngOnInit() {
  }

  

}
