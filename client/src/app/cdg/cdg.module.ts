import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CdgComponent } from './cdg.component';
import { CdgUiModule } from '../cdg-ui/cdg-ui.module';
import { MapService } from "./map/map.service"
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
  providers:[NbSidebarService,MapService]
})
export class CdgModule { }
