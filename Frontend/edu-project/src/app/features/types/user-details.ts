export interface UserDetails {
  iduser: string;
  name: string;
}

export interface LoginResp {
  code: string;
  message: string;
  status: string;
  userDetails: UserDetails[];
}
