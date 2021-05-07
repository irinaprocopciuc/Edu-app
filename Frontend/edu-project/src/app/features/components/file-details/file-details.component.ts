import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-file-details',
  templateUrl: './file-details.component.html',
  styleUrls: ['./file-details.component.scss']
})
export class FileDetailsComponent implements OnInit {
  src = "C:/AAA-IRINA/Disertatie/app/Backend/edu/edu-backend/courseFiles/Business Intelligence/D2 - Sprint approach and Testing processes.docx";

  constructor() { }

  ngOnInit(): void {
  }

}
