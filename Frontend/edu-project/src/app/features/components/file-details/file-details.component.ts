import { CommentDetails } from './../../../shared/types/comment-details';
import { Comment } from './../../../shared/types/comment';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Component, Input, OnChanges, OnInit, ViewChild } from '@angular/core';
import { FileUploadService } from 'src/app/shared/services/file-upload.service';
import { CommentsService } from 'src/app/shared/services/comments.service';
import { ErrorHandlingService } from 'src/app/core/services/error-handling.service';

@Component({
  selector: 'app-file-details',
  templateUrl: './file-details.component.html',
  styleUrls: ['./file-details.component.scss'],
})
export class FileDetailsComponent implements OnInit, OnChanges {
  @Input() file;

  src = '';
  isLoading = true;
  commentsForm: FormGroup;
  fileName: string;
  courseName: string;
  commentsList: Comment[];
  currentUserId: string;

  constructor(
    private readonly formBuilder: FormBuilder,
    private readonly fileService: FileUploadService,
    private readonly commentsService: CommentsService,
    private readonly errorServicce: ErrorHandlingService
  ) {}

  ngOnInit(): void {
    this.currentUserId = localStorage.getItem('userId');
    this.commentsForm = this.formBuilder.group({
      comment: [null],
    });
    this.previewFile();
  }

  ngOnChanges(): void {
    this.previewFile();
  }

  editComment(comment: Comment): void {
    console.log(comment);
  }

  deleteComment(comment: Comment): void {
    console.log(comment);
    this.commentsService.deleteComment(comment.commentId).subscribe((resp) => {
      this.errorServicce.displaySuccessToast(resp['message'], '');
    });
    this.commentsList = this.commentsList.filter(
      (item) => item.commentId !== comment.commentId
    );
  }

  addComment(): void {
    console.log(this.commentsForm.getRawValue());
    let newComment: CommentDetails = {
      fileName: '',
      courseName: '',
      userId: '',
      message: '',
      date: '',
    };
    newComment.fileName = this.fileName;
    newComment.courseName = this.courseName;
    newComment.userId = this.currentUserId;
    newComment.message = this.commentsForm.get('comment').value;
    newComment.date = new Date().toISOString();
    console.log(newComment);

    this.commentsService.addComment(newComment).subscribe((res) => {
      this.errorServicce.displaySuccessToast(res['message'], '');
    });
    setTimeout(() => {
      this.retrieveComments();
      this.commentsList = this.commentsList.filter(
        (item) => item.message !== ''
      );
    }, 200);
    this.commentsForm.get('comment').setValue('');
  }

  private previewFile(): void {
    this.fileName = this.file;
    this.courseName = localStorage
      .getItem('selectedCourseName')
      .replace(/\s/g, '');
    this.fileService
      .downloadFile(this.fileName, this.courseName)
      .subscribe((fileRes) => {
        let fileUrl = URL.createObjectURL(fileRes);
        this.src = fileUrl;
        this.retrieveComments();
        console.log(fileUrl);
      });
  }

  private retrieveComments(): void {
    this.commentsService
      .getFileComments(this.fileName, this.courseName)
      .subscribe((commentsList) => {
        console.log(commentsList);
        this.commentsList = commentsList['response'];
        this.isLoading = false;
      });
  }
}
