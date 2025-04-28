"use client"

import { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Loader2, ArrowRight, Plus } from "lucide-react";
import { useToast } from "@/hooks/use-toast";
import Link from "next/link";
import { createAccountForUser, getAccountsByUser,getAllAccounts } from "@/app/service/accounts";
import { AccountResponseDto, CreateAccountDto } from "@/types/account-dtos"; // Importe o serviço correto e o tipo
import { useParams } from "next/navigation"; // Importe useParams
import { useUserStore } from "@/hooks/userStore"; // Importe o hook de estado global
import CreateAccountDialog from "@/components/create-account-dialog"; // Importe o componente de diálogo
interface Account {
  id: string;
  userId: string;
  userName: string;
  name: string;
  balance: number;
}

export default function AccountsPage() {
    const [accounts, setAccounts] = useState<AccountResponseDto[]>([]);
    const [loading, setLoading] = useState(true);
    const [createDialogOpen, setCreateDialogOpen] = useState(false);
    const { toast } = useToast();
    const params = useParams();
    const userId = Array.isArray(params.userId) ? params.userId[0] : params.userId; // Obtém o userId de forma segura
    const createdUserId = useUserStore((state) => state.createdUserId);
  
    useEffect(() => {
      if (userId) {
        fetchAccounts(userId); // Passe o userId para fetchAccounts
      } else {
        console.error("Nenhum userId encontrado nos parâmetros da URL");
        toast({
          title: "Erro",
          description: "O ID do usuário está faltando na URL",
          variant: "destructive",
        });
        setLoading(false);
      }
    }, [userId]);
  
    const fetchAccounts = async (userId: string) => {
      setLoading(true);
      try {
        const data = await getAccountsByUser(userId);
        setAccounts(data);
      } catch (error: any) {
        toast({
          title: "Erro",
          description: error.message || "Falha ao buscar contas",
          variant: "destructive",
        });
      } finally {
        setLoading(false);
      }
    };
    const handleCreateAccount = async (accountData: CreateAccountDto) => {
      setCreateDialogOpen(false);
      try {
        await createAccountForUser(userId, accountData); 
        if (userId) {
          fetchAccounts(userId);
        }
        toast({
          title: "Sucesso",
          description: "Conta criada com sucesso",
        });
      } catch (error: any) {
        toast({
          title: "Erro",
          description: error.message || "Falha ao criar conta",
          variant: "destructive",
        });
      }
    };
   

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold">User Accounts</h1>
        <Button onClick={() => setCreateDialogOpen(true)}>
          <Plus className="mr-2 h-4 w-4" />
          Add Account
        </Button>
      </div>

      <Card>
        <CardHeader>
          <CardTitle>Accounts for User ID: {userId}</CardTitle>
          <CardDescription>Manage investment accounts for this user</CardDescription>
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
                  <TableHead>Account Name</TableHead>
                  <TableHead>Balance</TableHead>
                  <TableHead>Actions</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {accounts.length === 0 ? (
                  <TableRow>
                    <TableCell colSpan={3} className="text-center py-8 text-muted-foreground">
                      No accounts found
                    </TableCell>
                  </TableRow>
                ) : (
                  accounts.map((account) => (
                    <TableRow key={account.accountId}>
                      <TableCell className="font-medium">{account.accountName}</TableCell>
                      <TableCell>${account.balance.toFixed(2)}</TableCell>
                      <TableCell>
                        <Button variant="outline" size="sm" asChild>
                          <Link href={`/accounts/${account.accountId}/stocks`}>
                            View Stocks
                            <ArrowRight className="ml-2 h-4 w-4" />
                          </Link>
                        </Button>
                      </TableCell>
                    </TableRow>
                  ))
                )}
              </TableBody>
            </Table>
          )}
        </CardContent>
      </Card>

      <CreateAccountDialog open={createDialogOpen} onOpenChange={setCreateDialogOpen} onSubmit={handleCreateAccount} />
    </div>
  )
}