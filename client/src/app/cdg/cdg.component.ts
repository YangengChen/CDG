import { Component, OnInit, Input} from '@angular/core';



@Component({
  selector: 'app-cdg',
  templateUrl: './cdg.component.html',
  styleUrls: ['./cdg.component.scss']
})
export class CdgComponent implements OnInit {
  @Input() items:[{title:"Testing"}, {title:"menu"}];
  constructor() { 
    
  };

  ngOnInit() {
  }

}
