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
  selectedUserId: string;
  activeUsername: string;
  stompClient;
  url = 'http://127.0.0.1:5000';
  socket;

  messageForm: FormGroup = new FormGroup({
    msg: new FormControl('')
  });

  constructor(private readonly userService: UserService) {}

  ngOnInit(): void {
    this.activeUsername = localStorage.getItem('username');
    this.socket = socketIo(this.url);

    this.socket.on('connect', () => {
      this.socket.emit('connected', JSON.stringify(localStorage.getItem('userId')));
    });

    this.getUsersList();
  }

  ngOnDestroy(): void {
    console.log('yes');
    this.socket.disconnect();
  }
  selectUser(user: UserDetails): void {
     this.selectedUser = user;
     this.isUserSelected = true;
  }

  sendMessage(): void {
    console.log(this.messageForm.getRawValue().msg, this.selectedUser.iduser);

    this.socket.emit('message', JSON.stringify(this.messageForm.getRawValue().msg),JSON.stringify(this.selectedUser.iduser));
    this.socket.emit('mai', JSON.stringify(this.messageForm.getRawValue().msg),JSON.stringify(this.selectedUser.iduser));

  }

  private getUsersList(): void {
    this.userService.getUsers().subscribe((usersList) => {
      this.users = usersList['response'].filter(
        (res) => res.name !== this.activeUsername
      );
    });
  }
}
