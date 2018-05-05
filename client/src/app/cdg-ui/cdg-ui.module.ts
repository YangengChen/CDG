import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CdgDropdownComponent } from './cdg-dropdown/cdg-dropdown.component'
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CdgButtonComponent } from './cdg-button/cdg-button.component';
import { CdgSliderComponent } from './cdg-slider/cdg-slider.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { MatSliderModule } from '@angular/material';
import { FormsModule } from "@angular/forms";
import 'hammerjs';
import { CdgSnackbarComponent } from './cdg-snackbar/cdg-snackbar.component';
import { StartGenerationSuccessComponent } from './cdg-snackbar/start-generation-success/start-generation-success.component';
import { StartGenerationFailedComponent } from './cdg-snackbar/start-generation-failed/start-generation-failed.component';
import { WeightFailureComponent } from "./cdg-snackbar/weight-failure/weight-failure.component";
import { GenerationFinishedComponent } from './cdg-snackbar/generation-finished/generation-finished.component';

@NgModule({
  imports: [
    FormsModule,
    CommonModule,
    NgbModule,
    MatSliderModule,
    BrowserAnimationsModule,
  ],
  declarations: [
    CdgDropdownComponent,
    CdgButtonComponent,
    CdgSliderComponent,
    CdgSnackbarComponent,
    StartGenerationSuccessComponent,
    StartGenerationFailedComponent,
    WeightFailureComponent,
    GenerationFinishedComponent
  ],
  exports: [
    CdgDropdownComponent,
    CdgButtonComponent,
    CdgSliderComponent,
    CdgSnackbarComponent
  ],
  entryComponents:[
    StartGenerationFailedComponent,
    StartGenerationSuccessComponent,
    WeightFailureComponent,
    GenerationFinishedComponent
  ],
  providers : [CdgSnackbarComponent]
})
export class CdgUiModule { }
