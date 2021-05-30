export interface UserDetails {
  iduser: string;
  name: string;
  type: string;
  studyCycle: string;
}

export interface LoginResp {
  code: string;
  message: string;
  status: string;
  userDetails: UserDetails[];
}
