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
//  import { NbAuthModule,
//   NbAuthComponent,
//   NbLoginComponent,
//   NbRegisterComponent,
//   NbLogoutComponent,
//   NbRequestPasswordComponent,
//   NbResetPasswordComponent,
// } from '@nebular/auth';

import {AgmCoreModule} from '@agm/core';
@NgModule({
  imports: [
    CommonModule,
    // NbAuthModule,
    // NbAuthComponent,
    // NbLoginComponent,
    // NbRegisterComponent,
    // NbLogoutComponent,
    // NbRequestPasswordComponent,
    // NbResetPasswordComponent,
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
