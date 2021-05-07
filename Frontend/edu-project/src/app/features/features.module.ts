import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { AppRoutingModule } from '../app-routing.module';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import {MatSelectModule} from '@angular/material/select';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatIconModule} from '@angular/material/icon';

import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { LiveChatComponent } from './components/live-chat/live-chat.component';
import { CoursesComponent } from './components/courses/courses.component';
import { FileDetailsComponent } from './components/file-details/file-details.component';
import { PdfViewerModule } from 'ng2-pdf-viewer';

@NgModule({
  declarations: [LoginComponent, RegisterComponent, HomepageComponent, LiveChatComponent, CoursesComponent, FileDetailsComponent],
  imports: [
    CommonModule,
    AppRoutingModule,
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    MatCardModule,
    MatInputModule,
    MatButtonModule,
    MatDatepickerModule,
    MatSelectModule,
    MatTooltipModule,
    MatIconModule,
    PdfViewerModule
  ]
})
export class FeaturesModule { }
