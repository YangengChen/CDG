import { Component, OnInit, Input} from '@angular/core';

@Component({
  selector: 'cdg-slider',
  templateUrl: './cdg-slider.component.html',
  styleUrls: ['./cdg-slider.component.scss']
})
export class CdgSliderComponent implements OnInit {
  @Input() name: string;
  constructor() { }

  ngOnInit() {
  }

}
