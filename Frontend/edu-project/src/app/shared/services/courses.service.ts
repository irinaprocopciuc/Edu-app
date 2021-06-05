import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Injectable } from '@angular/core';
import { Course } from '../types/course';

@Injectable({
  providedIn: 'root',
})
export class CoursesService {
  private baseURL = environment.url;

  constructor(private readonly http: HttpClient) {}

  getCourses(userId: string): Observable<Course> {
    return this.http.get<Course>(`${this.baseURL}/courses/userId=${userId}`);
  }

  getCoursesForTeacher(teacherId: string): Observable<Course> {
    return this.http.get<Course>(
      `${this.baseURL}/courses/teacherId=${teacherId}`
    );
  }

  getCouseFiles(courseName: string): Observable<any> {
    return this.http.get<any>(
      `${this.baseURL}/courses/getCourseFiles/courseName=${courseName}`
    );
  }

  getCourseProjects(courseName: string, userId: string): Observable<any> {
    return this.http.get<any>(
      `${this.baseURL}/courses/getCourseUserProjects/courseName=${courseName}&userId=${userId}`
    );
  }

  getAssignmentsForCourse(courseName: string): Observable<any> {
    return this.http.get(
      `${this.baseURL}/courses/getCourseProjects/courseName=${courseName}`
    );
  }

  downloadAssignment(
    fileName: string,
    courseName: string,
    userId: string
  ): Observable<any> {
    return this.http.get(
      `${this.baseURL}/courses/downloadFile/fileName=${fileName}&courseName=${courseName}&userId=${userId}`,
      { responseType: 'blob' as 'json' }
    );
  }
}
