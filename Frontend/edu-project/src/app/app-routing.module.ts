import { FileDetailsComponent } from './features/components/file-details/file-details.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CoursesComponent } from './features/components/courses/courses.component';
import { HomepageComponent } from './features/components/homepage/homepage.component';
import { LiveChatComponent } from './features/components/live-chat/live-chat.component';
import { LoginComponent } from './features/components/login/login.component';
import { RegisterComponent } from './features/components/register/register.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'register',
    component: RegisterComponent,
  },
  {
    path: ':user_id',
    component: HomepageComponent,
  },
  {
    path: ':user_id/homepage',
    component: HomepageComponent,
    children: [
      { path: '', redirectTo: 'courses', pathMatch: 'full' },
      {
        path: 'courses',
        component: CoursesComponent,
        children: [
          { path: '', redirectTo: 'courses', pathMatch: 'full' },
          {
            path: 'file-details',
            component: FileDetailsComponent,
          },
        ]
      },
      {
        path: 'chat',
        component: LiveChatComponent,
      }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
