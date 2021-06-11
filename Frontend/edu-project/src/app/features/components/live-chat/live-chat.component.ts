import { BehaviorSubject, Observable } from 'rxjs';
import { FormControl, FormGroup } from '@angular/forms';
import { HttpHeaders } from '@angular/common/http';
import { UserDetails } from './../../types/user-details';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { UserService } from 'src/app/shared/services/user.service';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { environment } from 'src/environments/environment';
import * as socketIo from 'socket.io-client';

@Component({
  selector: 'app-live-chat',
  templateUrl: './live-chat.component.html',
  styleUrls: ['./live-chat.component.scss'],
})
export class LiveChatComponent implements OnInit, OnDestroy {
  users: UserDetails[];
  isUserSelected = false;
  selectedUser: UserDetails;
  currentUserId: string;
  activeUsername: string;
  stompClient;
  url = 'http://127.0.0.1:5000';
  socket;

  messageForm: FormGroup = new FormGroup({
    msg: new FormControl(''),
  });

  onlineUsers;
  receivedMessages = [];
  sentMessages = [];

  constructor(private readonly userService: UserService) {}

  ngOnInit(): void {
    this.activeUsername = localStorage.getItem('username');
    this.currentUserId = localStorage.getItem('userId');
    this.socket = socketIo(this.url);

    this.socket.on('connect', () => {
      this.socket.emit('connected', JSON.stringify(this.currentUserId));
    });

    this.getUsersList();

    this.socket.on('receive_users', (data) => {
      this.onlineUsers = data;
      console.log(this.onlineUsers);
    });

    this.socket.on('receive_message', (data) => {
      let message = data;
      this.receivedMessages.push(message);
      console.log(this.receivedMessages);
    });

    this.socket.emit('get_users');
  }

  ngOnDestroy(): void {
    console.log('yes');
    this.socket.disconnect();
  }

  selectUser(user: UserDetails): void {
    this.selectedUser = user;
    this.isUserSelected = true;
    this.receivedMessages = [];
  }

  sendMessage(): void {
    let message = this.messageForm.get('msg').value;
    this.receivedMessages.push({
      from: this.currentUserId,
      msg: message
    });
    console.log(this.receivedMessages);
    // this.sentMessages.push(message);
    // console.log(this.sentMessages)
    this.socket.emit(
      'send_message',
      JSON.stringify(this.currentUserId),
      JSON.stringify(message),
      JSON.stringify(this.selectedUser.iduser)
    );
    this.messageForm.get('msg').setValue('');

  }

  private getUsersList(): void {
    this.userService.getUsers().subscribe((usersList) => {
      this.users = usersList['response'].filter(
        (res) => res.name !== this.activeUsername
      );
    });
  }
}
