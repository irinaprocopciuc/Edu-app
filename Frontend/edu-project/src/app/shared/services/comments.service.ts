import { CommentDetails } from './../types/comment-details';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { EditComment } from '../types/edit-comment';

@Injectable({
  providedIn: 'root'
})
export class CommentsService {
  private baseUrl = environment.url;

  constructor(private readonly http: HttpClient) { }

  getFileComments(fileName: string, courseName: string) {
    return this.http.get(`${this.baseUrl}/comment/getComments/fileName=${fileName}&courseName=${courseName}`);
  }

  addComment(newComment: CommentDetails) {
    return this.http.post(`${this.baseUrl}/comment/addCommentToFile`, {...newComment});
  }

  deleteComment(commentId: string) {
    return this.http.delete(`${this.baseUrl}/comment/deleteComment`, {
      params: {
        commentId: commentId
      }
    });
  }

  editComment(editedComment: EditComment) {
    return this.http.put(`${this.baseUrl}/comment/editComment`, {...editedComment});
  }
}
