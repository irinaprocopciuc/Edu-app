import { Thesis } from './../../../shared/types/thesis';
import { Component, OnInit } from '@angular/core';
import { ThesisService } from 'src/app/shared/services/thesis.service';
import { ChooseTheme } from 'src/app/shared/types/choose-theme';
import { ErrorHandlingService } from 'src/app/core/services/error-handling.service';
import { runInThisContext } from 'node:vm';

@Component({
  selector: 'app-thesis',
  templateUrl: './thesis.component.html',
  styleUrls: ['./thesis.component.scss']
})
export class ThesisComponent implements OnInit {
  thesisList: Thesis[] = [];
  displayedColumns: string[] = ['idTheme', 'type', 'name', 'details', 'technologies', 'teacherName'];
  studyCycle: string;
  chosenTheme: Thesis;
  currentUserId: string;
  hasChosenTheme = false;

  constructor(
    private readonly thesisService: ThesisService,
    private readonly errorService: ErrorHandlingService
  ) { }

  ngOnInit(): void {
    this.studyCycle = localStorage.getItem('studyCycle');
    this.currentUserId = localStorage.getItem('userId');
    this.getChosenTheme();
  }

  selectTheme(chosenTheme): void {
    this.chosenTheme = chosenTheme;
  }

  save(): void {
    let theme: ChooseTheme = {idTheme: '', idUser:''};
    theme.idTheme = this.chosenTheme.idTheme;
    theme.idUser = this.currentUserId;
    this.thesisService.chooseTheme(theme).subscribe(chooseThemeResp => {
      this.errorService.displaySuccessToast(chooseThemeResp['message'], '');
      this.hasChosenTheme = true;
    })
  }

  getChosenTheme(): void {
    this.thesisService.getChosenTheme(this.currentUserId).subscribe(resp => {
      if (resp['code'] === '200') {
        this.chosenTheme = resp['response'][0];
        this.hasChosenTheme = true;
      }
    })

    if(!this.hasChosenTheme) {
      this.getThesisList();
    }
  }

  private getThesisList(): void {
    this.thesisService.getAllThesis().subscribe(res => {
      this.thesisList = res.response.filter(item => item.type === this.studyCycle);
      this.thesisList.forEach(thesis => {
        if (thesis.idStudent === this.currentUserId) {
          this.hasChosenTheme = true;
        }
      });
      this.thesisList = this.thesisList.filter(item => item.idStudent === null);
    })
  }


}
