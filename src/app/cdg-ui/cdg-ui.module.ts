import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CdgDropdownComponent } from './cdg-dropdown/cdg-dropdown.component'
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CdgButtonComponent } from './cdg-button/cdg-button.component';
import { CdgSliderComponent } from './cdg-slider/cdg-slider.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { MatSliderModule } from '@angular/material';
import 'hammerjs';
@NgModule({
  imports: [
    CommonModule,
    NgbModule,
    MatSliderModule,
    BrowserAnimationsModule,
  ],
  declarations: [
    CdgDropdownComponent,
    CdgButtonComponent,
    CdgSliderComponent,
  ],
  exports: [
    CdgDropdownComponent,
    CdgButtonComponent,
    CdgSliderComponent,
  ]
})
export class CdgUiModule { }
