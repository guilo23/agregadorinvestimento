// store/userStore.ts
import { create } from 'zustand';

interface UserState {
  createdUserId: string | null;
  setCreatedUserId: (userId: string | null) => void;
}

export const useUserStore = create<UserState>((set) => ({
  createdUserId: null,
  setCreatedUserId: (userId) => set({ createdUserId: userId }),
}));