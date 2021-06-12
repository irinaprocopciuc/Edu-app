import { Message } from './../../../shared/types/message';
import { BehaviorSubject, Observable } from 'rxjs';
import { FormControl, FormGroup } from '@angular/forms';
import { HttpHeaders } from '@angular/common/http';
import { UserDetails } from './../../types/user-details';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { UserService } from 'src/app/shared/services/user.service';
import * as socketIo from 'socket.io-client';
import { Message } from 'src/app/shared/types/message';
import { ChatService } from 'src/app/shared/services/chat.service';

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

  constructor(
    private readonly userService: UserService,
    private readonly chatService: ChatService
  ) {}

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
    this.chatService.getMessages(this.currentUserId, this.selectedUser.iduser).subscribe(response => {
      console.log(response);
      let messages = response['response'];
      messages.forEach((element: Message) => {
        let displayMsg = {
          from: element.idSender,
          msg: element.message,
          date: element.date
        }
        this.receivedMessages.push(displayMsg);
      });
      this.receivedMessages.sort((a,b) => new Date(a.date).getTime() - new Date(b.date).getTime());
      console.log(this.receivedMessages);
    })
    this.receivedMessages = [];
  }

  sendMessage(): void {
    let message = this.messageForm.get('msg').value;
    let date = new Date();
    this.receivedMessages.push({
      from: this.currentUserId,
      msg: message,
      date: date,
    });
    console.log(this.receivedMessages);
    // this.sentMessages.push(message);
    // console.log(this.sentMessages)

    this.socket.emit(
      'send_message',
      JSON.stringify(this.currentUserId),
      JSON.stringify(message),
      date,
      JSON.stringify(this.selectedUser.iduser)
    );

    let messageToAddToDb: Message = {
      idSender: this.currentUserId,
      idReceiver: this.selectedUser.iduser,
      message: message,
      date: date.toISOString(),
    };

    this.chatService.addMessage(messageToAddToDb).subscribe(res => {
      console.log(res);
    });

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
