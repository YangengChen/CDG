import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CdgComponent } from './cdg.component';
import { CdgUiModule } from '../cdg-ui/cdg-ui.module';
import { MapService } from "./map/map.service";
import { HttpClientModule } from "@angular/common/http"
import {  MatSliderModule, MatSlideToggleModule} from "@angular/material";
import { GenerationService } from "./generation.service";
import { NgxMapboxGLModule } from 'ngx-mapbox-gl';
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
    BrowserModule,
    MatSliderModule,
    MatSlideToggleModule,
    LeafletModule,
    NgxMapboxGLModule.forRoot({
      accessToken:"pk.eyJ1IjoiYnJvYmljaGVhdSIsImEiOiJjamZ2cGlsZXczaHA5MzNtZG52MWoxMjJtIn0.jnvZ-TdxfZ5Qm8zoWzO65g"
    }),    HttpClientModule,
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
  providers:[NbSidebarService,MapService,GenerationService]
})
export class CdgModule { }
