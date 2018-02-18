import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CdgComponent } from './cdg.component';
import { 
  NbSidebarModule, 
  NbLayoutModule, 
  NbSidebarService,
  NbThemeModule,
  NbCardModule,
 } from '@nebular/theme';
import { CdgRoutingModule } from './cdg-routing.module';
import { MapComponent } from './map/map.component';
import {AgmCoreModule} from '@agm/core';
@NgModule({
  imports: [
    CommonModule,
    NbSidebarModule, 
    NbLayoutModule,
    CdgRoutingModule,
    NbCardModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyDbo0ubVsgdziReD82-FBC9KCbuiZeFnuU' 
    })
  ],
  declarations: [
    CdgComponent,
    MapComponent
  ],
  providers:[NbSidebarService]
})
export class CdgModule { }
