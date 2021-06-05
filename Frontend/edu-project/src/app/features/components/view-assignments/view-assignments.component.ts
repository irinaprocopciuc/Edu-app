import { Component, OnInit } from '@angular/core';
import { CoursesService } from 'src/app/shared/services/courses.service';
import { Assignment } from '../../types/assignment';
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-view-assignments',
  templateUrl: './view-assignments.component.html',
  styleUrls: ['./view-assignments.component.scss'],
})
export class ViewAssignmentsComponent implements OnInit {
  currentCourse: string;
  noAssignments = false;
  assignmentsList: Assignment[];
  displayedColumns: string[] = ['id', 'name', 'assignment', 'download'];

  constructor(private readonly coursesService: CoursesService) {}

  ngOnInit(): void {
    this.currentCourse = localStorage
      .getItem('selectedCourseName')
      .replace(/\s/g, '');
    this.getAssignments();
  }

  downloadAssignmentFile(element: Assignment): void {
    this.coursesService
      .downloadAssignment(element.filename, this.currentCourse, element.userId)
      .subscribe((blob) => saveAs(blob, element.filename));
  }

  private getAssignments(): void {
    this.coursesService.getAssignmentsForCourse(this.currentCourse).subscribe(
      (assignments) => {
        this.assignmentsList = assignments['response'];
      },
      (err) => {
        this.noAssignments = true;
      }
    );
  }
}
