
import { User } from '@/types/user'; 
const API_BASE_URL = 'http://localhost:8081/v1';


interface CreateUserData {
  username: string;
  email: string;
  password: string; 
}
interface UpdateUserData extends Omit<User, 'userId'> {}

export const getAllUsers = async (): Promise<User[]> => {
  try {
    const response = await fetch(`${API_BASE_URL}/users/allUsers`);
    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || `Failed to fetch users (status ${response.status})`);
    }
    const data: User[] = await response.json();
    return data;
  } catch (error: any) {
    console.error("Error fetching users:", error);
    throw error;
  }
};

export const getUserById = async (userId: string): Promise<User> => {
  try {
    const response = await fetch(`${API_BASE_URL}/users/${userId}`);
    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || `Failed to fetch user (status ${response.status})`);
    }
    const data: User = await response.json();
    return data;
  } catch (error: any) {
    console.error(`Error fetching user with ID ${userId}:`, error);
    throw error;
  }
};

export const createUser = async (userData: CreateUserData): Promise<User> => {
  try {
    const response = await fetch(`${API_BASE_URL}/users`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(userData),
    });
    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || `Failed to create user (status ${response.status})`);
    }
    const data: User = await response.json();
    return data;
  } catch (error: any) {
    console.error("Error creating user:", error);
    throw error;
  }
};

export const updateUser = async (userId: string, userData: UpdateUserData): Promise<void> => {
  try {
    const response = await fetch(`${API_BASE_URL}/users/update/${userId}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(userData),
    });
    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || `Failed to update user (status ${response.status})`);
    }
    // No content on successful update (status 204)
  } catch (error: any) {
    console.error(`Error updating user with ID ${userId}:`, error);
    throw error;
  }
};

export const deleteUser = async (userId: string): Promise<void> => {
  try {
    const response = await fetch(`${API_BASE_URL}/users/delete/${userId}`, {
      method: 'DELETE',
    });
    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || `Failed to delete user (status ${response.status})`);
    }
    // No content on successful delete (status 204)
  } catch (error: any) {
    console.error(`Error deleting user with ID ${userId}:`, error);
    throw error;
  }
};