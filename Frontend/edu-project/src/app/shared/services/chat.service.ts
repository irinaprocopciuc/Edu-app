import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Message } from '../types/message';

@Injectable({
  providedIn: 'root',
})
export class ChatService {
  baseUrl = environment.url;

  constructor(private readonly http: HttpClient) {}

  addMessage(message: Message): Observable<any> {
    return this.http.post(`${this.baseUrl}/chat/addMessage`, { ...message });
  }

  getMessages(user1: string, user2: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/chat/getMessages/user1=${user1}&user2=${user2}`);
  }
}
