import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { CdgModule } from './cdg/cdg.module';
import { PagesModule } from './pages/pages.module';
import { NbThemeModule } from '@nebular/theme';
import { NgxMapboxGLModule } from 'ngx-mapbox-gl';
// import { NbAuthModule,
//   NbAuthComponent,
//   NbLoginComponent,
//   NbRegisterComponent,
//   NbLogoutComponent,
//   NbRequestPasswordComponent,
//   NbResetPasswordComponent,
// } from '@nebular/auth';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from './app-routing.module';
import { HomeComponent } from './pages/home/home.component';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {
  MatAutocompleteModule,
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatCheckboxModule,
  MatChipsModule,
  MatDatepickerModule,
  MatDialogModule,
  MatDividerModule,
  MatExpansionModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatNativeDateModule,
  MatPaginatorModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatRadioModule,
  MatRippleModule,
  MatSelectModule,
  MatSidenavModule,
  MatSliderModule,
  MatSlideToggleModule,
  MatSnackBarModule,
  MatSortModule,
  MatStepperModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
  MatTooltipModule,
} from '@angular/material';
import { HttpClientModule } from '@angular/common/http';
import 'hammerjs'
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
  ],
  imports: [
    BrowserModule,
    NbThemeModule.forRoot({ name: 'default' }),
    AppRoutingModule,
    CdgModule,
    NgbModule.forRoot(),
    PagesModule,
    HttpClientModule,
    BrowserAnimationsModule,
    LeafletModule.forRoot(),
    // NbAuthModule.forRoot(),
    // NbAuthComponent,
    // NbLoginComponent,
    // NbRegisterComponent,
    // NbLogoutComponent,
    // NbRequestPasswordComponent,
    // NbResetPasswordComponent,
    MatAutocompleteModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatChipsModule,
    MatDatepickerModule,
    MatDialogModule,
    MatDividerModule,
    MatExpansionModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatRippleModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatSortModule,
    MatStepperModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
MatTooltipModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
