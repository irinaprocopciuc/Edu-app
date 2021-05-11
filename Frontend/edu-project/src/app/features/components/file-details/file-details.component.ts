import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-file-details',
  templateUrl: './file-details.component.html',
  styleUrls: ['./file-details.component.scss']
})
export class FileDetailsComponent implements OnInit {
   src = "../../../../assets/test.pdf";
   //src = "file:///C:/Users/iprocopc/Downloads/MEGAtrends_Biblio.pdf";

   commentsForm: FormGroup;

  constructor(private readonly formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.commentsForm = this.formBuilder.group({
      comment: [null]
    });
  }

  addComment(): void {
    console.log(this.commentsForm.getRawValue());
  }
}
