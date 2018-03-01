import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'cdg-dropdown',
  templateUrl: './cdg-dropdown.component.html',
  styleUrls: ['./cdg-dropdown.component.scss']
})
export class CdgDropdownComponent implements OnInit {
  @Input() name:string;
  constructor() { }

  ngOnInit() {
  }

}
