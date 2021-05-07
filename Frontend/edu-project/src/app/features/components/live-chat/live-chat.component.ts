import { UserDetails } from './../../types/user-details';
import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-live-chat',
  templateUrl: './live-chat.component.html',
  styleUrls: ['./live-chat.component.scss']
})
export class LiveChatComponent implements OnInit {
  users: UserDetails[];
  isUserSelected = false;
  activeUsername: string;

  constructor(private readonly userService: UserService) { }

  ngOnInit(): void {
    this.activeUsername = localStorage.getItem('username');
    this.getUsersList();
  }

  selectUser(user: UserDetails): void {
    this.isUserSelected = true;
  }

  private getUsersList(): void {
    this.userService.getUsers().subscribe(usersList => {
      console.log(usersList);
      this.users = usersList['response'].filter(res => res.name !== this.activeUsername);
    })
  }
}
