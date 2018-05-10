import { Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import { NbCardModule } from "@nebular/theme"
@Component({
  selector: 'cdg-step',
  templateUrl: './cdg-step.component.html',
  styleUrls: ['./cdg-step.component.scss']
})
export class CdgStepComponent implements OnInit {
  @Input() step:any;

  constructor() {

  }
  ngOnInit() {}

  

}