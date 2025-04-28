
export interface CreateAccountDto {
  accountName: string;
}


export interface AccountResponseDto {
  accountId: string;
  userName: string;
  accountName: string;
  balance: number;
 
}