import { Thesis } from './../../../shared/types/thesis';
import { Component, Input, OnInit } from '@angular/core';
import { ThesisService } from 'src/app/shared/services/thesis.service';
import { ChooseTheme } from 'src/app/shared/types/choose-theme';
import { ErrorHandlingService } from 'src/app/core/services/error-handling.service';

@Component({
  selector: 'app-thesis',
  templateUrl: './thesis.component.html',
  styleUrls: ['./thesis.component.scss']
})
export class ThesisComponent implements OnInit {
  @Input() userType: string;

  thesisList: Thesis[] = [];
  displayedColumns: string[] = ['type', 'name', 'details', 'technologies', 'teacherName'];
  studyCycle: string;
  chosenTheme: Thesis;
  currentUserId: string;
  hasChosenTheme = false;
  displayTable: boolean;

  constructor(
    private readonly thesisService: ThesisService,
    private readonly errorService: ErrorHandlingService
  ) { }

  ngOnInit(): void {
    this.studyCycle = localStorage.getItem('studyCycle');
    this.currentUserId = localStorage.getItem('userId');
    if (this.userType === 'student') {
      this.displayedColumns.unshift('idTheme');
      this.getChosenTheme();
    } else {
      this.getThemesForTeacher();
    }
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
      this.displayTable = false;
    })
  }

  private isTableDisplayed(): boolean {
    return (!this.hasChosenTheme && this.userType === 'student') || (this.hasChosenTheme && this.userType === 'teacher') as boolean;
  }

  private getThemesForTeacher(): void {
    this.thesisService.getThemesForTeacher(this.currentUserId).subscribe(themes => {
      if (themes['code'] === '200') {
        let themesList = themes['response'];
        if (themesList.length > 0 ) {
          this.hasChosenTheme = true;
        } else {
          this.hasChosenTheme = false;
        }
        this.displayTable = this.isTableDisplayed();
        this.thesisList = themesList;
        console.log(this.thesisList);
      }
    })
  }


  private getChosenTheme(): void {
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
      this.displayTable = this.isTableDisplayed();
      this.thesisList = this.thesisList.filter(item => item.idStudent === null);
    })
  }


}
