import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CdgComponent } from './cdg.component';
import { CdgUiModule } from '../cdg-ui/cdg-ui.module';
import { MapService } from "./map/map.service";
import { HttpClientModule } from "@angular/common/http"
import { GenerationService } from "./generation.service";
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
  providers:[NbSidebarService,MapService,GenerationService]
})
export class CdgModule { }
