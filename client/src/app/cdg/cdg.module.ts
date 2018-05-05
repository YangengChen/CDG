import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CdgComponent } from './cdg.component';
import { CdgUiModule } from '../cdg-ui/cdg-ui.module';
import { CdgMapService } from "./map/map.service";
import { HttpClientModule } from "@angular/common/http"
import {  MatSliderModule, MatSlideToggleModule} from "@angular/material";
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { GenerationService } from "./generation.service";
import { NgxMapboxGLModule, MapService } from 'ngx-mapbox-gl';

import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { BrowserModule } from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { 
  NbSidebarModule, 
  NbLayoutModule, 
  NbSidebarService,
  NbThemeModule,
  NbCardModule,
  NbTabsetModule,
  NbUserModule,
  NbMenuModule,
 } from '@nebular/theme';
import { CdgRoutingModule } from './cdg-routing.module';
import { MapComponent } from './map/map.component';
import {AgmCoreModule} from '@agm/core';
@NgModule({
  imports: [  
    NgxMapboxGLModule, 
    BrowserModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatProgressSpinnerModule,
    LeafletModule,
    HttpClientModule,
    CommonModule,
    NbSidebarModule, 
    NbLayoutModule,
    CdgRoutingModule,
    NbCardModule,
    NbTabsetModule,
    CdgUiModule,
    NbUserModule,
    NbMenuModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyDbo0ubVsgdziReD82-FBC9KCbuiZeFnuU' 
    })
  ],
  declarations: [
    CdgComponent,
    MapComponent
  ],
  providers:[NbSidebarService,CdgMapService,GenerationService, MapService]
})
export class CdgModule { }
