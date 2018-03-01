import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'cdg-button',
  templateUrl: './cdg-button.component.html',
  styleUrls: ['./cdg-button.component.scss']
})
export class CdgButtonComponent implements OnInit {
  @Input() name: string;
  constructor() { }

  ngOnInit() {
  }

}
