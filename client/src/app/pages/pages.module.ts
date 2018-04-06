import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule } from '@angular/forms';
import { PagesRoutingModule } from './pages-routing.module';
import { LoginComponent } from './login/login.component';
import { LoginService } from './login/login.service';
import { 
  NbSidebarModule, 
  NbLayoutModule, 
  NbSidebarService,
  NbThemeModule,
  NbCardModule,
  NbTabsetModule,
  NbUserModule,
  NbMenuModule,
  NbCheckboxModule
 } from '@nebular/theme';
 import { NbAuthModule,
  NbAuthComponent,
  NbLoginComponent,
  NbRegisterComponent,
  NbLogoutComponent,
  NbRequestPasswordComponent,
  NbResetPasswordComponent,
  NbAuthBlockComponent
} from '@nebular/auth';

import {AgmCoreModule} from '@agm/core';
@NgModule({
  imports: [
    NbSidebarModule,
    NbAuthModule, 
    NbLayoutModule, 
    NbCheckboxModule,
    NbThemeModule,
    NbCardModule,
    NbTabsetModule,
    NbUserModule,
    NbMenuModule,
    CommonModule,
    PagesRoutingModule,
    FormsModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyDbo0ubVsgdziReD82-FBC9KCbuiZeFnuU' 
    })
  ],
  declarations: [
    LoginComponent,
  ],
  providers:[NbSidebarService,LoginService]
})
export class PagesModule { }
