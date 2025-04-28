"use client"

import { useState, useEffect } from "react";
import { useToast } from "@/hooks/use-toast";
import { User } from "@/types/user";
import { getAllUsers, deleteUser, createUser, updateUser } from "@/app/service/user"; // Importe os serviços de usuário e a interface User
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Loader2, Plus, Pencil, Trash2} from "lucide-react";
import Link from 'next/link';
import CreateUserDialog from "@/components/create-user-dialog";
import UpdateUserDialog from "@/components/update-user-dialog";
import{useUserStore} from "@/hooks/userStore"; // Importe o hook de estado global



export default function UsersPage() {
  const [users, setUsers] = useState<User[]>([]);
  const setGlobalCreatedUserId = useUserStore((state) => state.setCreatedUserId);
  const [loading, setLoading] = useState(true);
  const [createDialogOpen, setCreateDialogOpen] = useState(false);
  const [updateDialogOpen, setUpdateDialogOpen] = useState(false);
  const [selectedUser, setSelectedUser] = useState<User | null>(null);
  const { toast } = useToast();

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    setLoading(true);
    try {
      const data = await getAllUsers(); 
      console.log("Fetched users:", data);
      setUsers(data);
    } catch (error: any) {
      toast({
        title: "Error",
        description: error.message || "Failed to fetch users",
        variant: "destructive",
      });
    } finally {
      setLoading(false);
    }
  };

  const handleDeleteUser = async (userId: string) => {
    try {
      await deleteUser(userId); // Use o serviço
      setUsers(users.filter((user) => user.userId !== userId));
      toast({
        title: "Success",
        description: "User deleted successfully",
      });
    } catch (error: any) {
      toast({
        title: "Error",
        description: error.message || "Failed to delete user",
        variant: "destructive",
      });
    }
  };

  const handleCreateUser = async (userData: Omit<User, "userId">) => {
    try {
      
      const newUser = await createUser(userData as any); // Use o serviço
      setUsers([...users, newUser]);
      console.log("Novo usuário criado:", newUser);
      setGlobalCreatedUserId(newUser.userId);
      console.log(setGlobalCreatedUserId); // Armazene o ID do usuário criado
      setCreateDialogOpen(false);
      toast({
        title: "Success",
        description: "User created successfully",
      });
      fetchUsers(); // Recarregue a lista
    } catch (error: any) {
      toast({
        title: "Error",
        description: error.message || "Failed to create user",
        variant: "destructive",
      });
    }
  };

  const handleUpdateUser = async (userId: string, userData: Omit<User, "userId">) => {
    try {
      await updateUser(userId, userData as any); // Use o serviço
      setUsers(users.map((user) => (user.userId === userId ? { ...user, ...userData } : user)));
      setUpdateDialogOpen(false);
      setSelectedUser(null);
      toast({
        title: "Success",
        description: "User updated successfully",
      });
      fetchUsers(); // Recarregue a lista
    } catch (error: any) {
      toast({
        title: "Error",
        description: error.message || "Failed to update user",
        variant: "destructive",
      });
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold">User Management</h1>
        <Button onClick={() => setCreateDialogOpen(true)}>
          <Plus className="mr-2 h-4 w-4" />
          Add User
        </Button>
      </div>

      <Card>
        <CardHeader>
          <CardTitle>Users</CardTitle>
          <CardDescription>Manage user profiles in the system</CardDescription>
        </CardHeader>
        <CardContent>
          {loading ? (
            <div className="flex justify-center py-8">
              <Loader2 className="h-8 w-8 animate-spin text-muted-foreground" />
            </div>
          ) : (
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Name</TableHead>
                  <TableHead>Email</TableHead>
                  <TableHead>Actions</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {users.length === 0 ? (
                  <TableRow>
                    <TableCell colSpan={4} className="text-center py-8 text-muted-foreground">
                      No users found
                    </TableCell>
                  </TableRow>
                ) : (
                  users.map((user) => (
                    <TableRow key={user.userId}>
                      <TableCell className="font-medium">{user.username}</TableCell>
                      <TableCell>{user.email}</TableCell>
                      <TableCell>
                        <div className="flex gap-2">
                          <Button
                            variant="outline"
                            size="icon"
                            onClick={() => {
                              setSelectedUser(user)
                              setUpdateDialogOpen(true)
                            }}
                          >
                            <Pencil className="h-4 w-4" />
                          </Button>
                          <Button variant="outline" size="icon" onClick={() => handleDeleteUser(user.userId)}>
                            <Trash2 className="h-4 w-4" />
                          </Button>
                          <Link
                            href={`/users/${user.userId}/accounts`}
                            className="inline-flex items-center justify-center gap-2 whitespace-nowrap font-medium transition-colors focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:pointer-events-none disabled:opacity-50 border border-input bg-background shadow-sm hover:bg-accent hover:text-accent-foreground h-8 rounded-md px-3 text-xs"
                          >
                            View Accounts
                          </Link>
                        </div>
                      </TableCell>
                    </TableRow>
                  ))
                )}
              </TableBody>
            </Table>
          )}
        </CardContent>
      </Card>

      <CreateUserDialog open={createDialogOpen} onOpenChange={setCreateDialogOpen} onSubmit={handleCreateUser} />

      {selectedUser && (
        <UpdateUserDialog
          open={updateDialogOpen}
          onOpenChange={setUpdateDialogOpen}
          user={selectedUser}
          onSubmit={(data) =>
            handleUpdateUser(selectedUser.userId, {
              ...data,
            //  username: selectedUser.username, // Ensure username is included
              //password: selectedUser.password // Ensure password is included
             email: ""
            })
          }
        />
      )}
    </div>
  )
}
