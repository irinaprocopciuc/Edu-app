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
import {MatRadioModule} from '@angular/material/radio';
import {MatTableModule} from '@angular/material/table';

import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { LiveChatComponent } from './components/live-chat/live-chat.component';
import { CoursesComponent } from './components/courses/courses.component';
import { FileDetailsComponent } from './components/file-details/file-details.component';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { TextFieldModule } from '@angular/cdk/text-field';
import { UploadFileComponent } from './components/upload-file/upload-file.component';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { ThesisComponent } from './components/thesis/thesis.component';

@NgModule({
  declarations: [LoginComponent, RegisterComponent, HomepageComponent, LiveChatComponent, CoursesComponent, FileDetailsComponent, UploadFileComponent, ThesisComponent],
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
    MatInputModule,
    MatRadioModule,
    MatTableModule,
    TextFieldModule,
    PdfViewerModule,
    MatProgressSpinnerModule
  ]
})
export class FeaturesModule { }
