import { Component, OnInit, Input, Output, EventEmitter} from '@angular/core';

@Component({
  selector: 'cdg-slider',
  templateUrl: './cdg-slider.component.html',
  styleUrls: ['./cdg-slider.component.scss']
})
export class CdgSliderComponent implements OnInit {
  sliderValue:number = 50;
  @Input() name: string;
  @Input() disabled:boolean = false;
  @Output() change:EventEmitter<number>;
  constructor() {
    this.change = new EventEmitter<number>();
  }
  ngOnInit() {}
  onChange(event){
    this.change.emit(event.value);
  }
  onMove(event){
    this.sliderValue = event.value;
  }
}
