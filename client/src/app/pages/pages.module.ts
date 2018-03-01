import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule } from '@angular/forms';
import { PagesRoutingModule } from './pages-routing.module';
import { LoginComponent } from './login/login.component';
import { 
  NbSidebarModule, 
  NbLayoutModule, 
  NbSidebarService,
  NbThemeModule,
  NbCardModule,
 } from '@nebular/theme';

import {AgmCoreModule} from '@agm/core';
@NgModule({
  imports: [
    CommonModule,
    NbSidebarModule, 
    NbLayoutModule,
    PagesRoutingModule,
    NbCardModule,
    FormsModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyDbo0ubVsgdziReD82-FBC9KCbuiZeFnuU' 
    })
  ],
  declarations: [
    LoginComponent
  ],
  providers:[NbSidebarService]
})
export class PagesModule { }
