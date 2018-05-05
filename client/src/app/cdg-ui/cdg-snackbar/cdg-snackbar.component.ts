import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material';
import { StartGenerationFailedComponent } from "./start-generation-failed/start-generation-failed.component"
import { StartGenerationSuccessComponent } from "./start-generation-success/start-generation-success.component"
import { WeightFailureComponent } from "./weight-failure/weight-failure.component";
import { GenerationFinishedComponent } from "./generation-finished/generation-finished.component"
@Component({
  selector: 'app-cdg-snackbar',
  templateUrl: './cdg-snackbar.component.html',
  styleUrls: ['./cdg-snackbar.component.scss'],
  entryComponents:[
    StartGenerationFailedComponent,
    StartGenerationSuccessComponent,
    WeightFailureComponent
  ]
})
export class CdgSnackbarComponent implements OnInit {
  options = {duration:3000}
  constructor(private snackBar: MatSnackBar) { }

  ngOnInit() {
  }

  generateSnackbar(type : SnackbarEnum){
    if(type == SnackbarEnum.START_GENERATION_SUCCESS){
      this.snackBar.openFromComponent(StartGenerationSuccessComponent, this.options)
    }
    else if (type == SnackbarEnum.START_GENERATION_FAILED){
      this.snackBar.openFromComponent(StartGenerationFailedComponent, this.options)
    }
    else if (type == SnackbarEnum.WEIGHT_FAILURE){
      this.snackBar.openFromComponent(WeightFailureComponent, this.options)
    }
    else if ( type = SnackbarEnum.GENERATION_FINISHED){
      this.snackBar.openFromComponent(GenerationFinishedComponent, this.options)
    }
  }
}


export enum SnackbarEnum{
  START_GENERATION_SUCCESS,
  START_GENERATION_FAILED,
  WEIGHT_FAILURE,
  GENERATION_FINISHED
}