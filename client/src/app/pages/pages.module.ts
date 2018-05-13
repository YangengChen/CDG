import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LoginComponent } from './login/login.component';
import { LoginService } from './login/login.service';
import { FlashMessagesModule } from 'ngx-flash-messages';

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
import { ManageComponent } from './manage/manage.component';
import { MapsComponent } from './maps/maps.component';
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
    FormsModule,
    FlashMessagesModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyDbo0ubVsgdziReD82-FBC9KCbuiZeFnuU' 
    })
  ],
  declarations: [
    LoginComponent,
    ManageComponent,
    MapsComponent,
  ],
  providers:[NbSidebarService,LoginService]
})
export class PagesModule { }
