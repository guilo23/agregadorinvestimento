
import { Account } from '@/types/account'; 
import { AccountResponseDto, CreateAccountDto } from '@/types/account-dtos';

const API_BASE_URL = 'http://localhost:8081/v1';

export const getAccountsByUser = async (userId: string | null): Promise<AccountResponseDto[]> => {
  try {
    const response = await fetch(`${API_BASE_URL}/users/${userId}/accounts`);
    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || `Failed to fetch accounts for user ${userId} (status ${response.status})`);
    }
    const data: AccountResponseDto[] = await response.json();
    return data;
  } catch (error: any) {
    console.error(`Error fetching accounts for user ${userId}:`, error);
    throw error;
  }
};
export const getAllAccounts = async (): Promise<AccountResponseDto[]> => {
  try {
    const response = await fetch(`${API_BASE_URL}/accounts/allAccounts`);
    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || `Failed to fetch users (status ${response.status})`);
    }
    const data: AccountResponseDto[] = await response.json();
    return data;
  } catch (error: any) {
    console.error("Error fetching users:", error);
    throw error;
  }
};

export const createAccountForUser = async (userId: string | undefined, accountData: CreateAccountDto): Promise<void> => {
  try {
    const response = await fetch(`${API_BASE_URL}/users/${userId}/accounts`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(accountData),
    });
    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || `Failed to create account for user ${userId} (status ${response.status})`);
    }
  } catch (error: any) {
    console.error(`Error creating account for user ${userId}:`, error);
    throw error;
  }
};
