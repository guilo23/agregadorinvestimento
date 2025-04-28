"use client"

import { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Loader2, ArrowRight } from "lucide-react";
import { useToast } from "@/hooks/use-toast";
import Link from "next/link";
import { getAccountsByUser,getAllAccounts } from "@/app/service/accounts";
import { AccountResponseDto, CreateAccountDto } from "@/types/account-dtos"; // Importe o serviço correto e o tipo
import { useParams } from "next/navigation"; // Importe useParams
import { useUserStore } from "@/hooks/userStore"; // Importe o hook de estado global
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
  const { toast } = useToast();
  const params = useParams();
  const userId = params.userId as string;
  const createdUserId = useUserStore((state) => state.createdUserId);// Obtenha o userId dos parâmetros da rota

 useEffect(() => {
   fetchAccounts();
    }, [userId]);
  
    const fetchAccounts = async () => {
      setLoading(true);
      try {
        const data = await getAllAccounts(); 
        setAccounts(data);
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

  return (
    <div className="space-y-6">
      <h1 className="text-3xl font-bold">User Accounts</h1> {/* Título mais específico */}

      <Card>
        <CardHeader>
          <CardTitle>Investment Accounts for User ID: {userId}</CardTitle> {/* Título mais específico */}
          <CardDescription>Overview of investment accounts for this user</CardDescription> {/* Descrição mais específica */}
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
                  <TableHead>User</TableHead>
                  <TableHead>Balance</TableHead>
                  <TableHead>Actions</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {accounts.length === 0 ? (
                  <TableRow>
                    <TableCell colSpan={4} className="text-center py-8 text-muted-foreground">
                      No accounts found for this user
                    </TableCell>
                  </TableRow>
                ) : (
                  accounts.map((account) => (
                    <TableRow key={account.accountId}>
                      <TableCell className="font-medium">{account.accountName}</TableCell>
                      <TableCell>{account.userName}</TableCell>
                      <TableCell>${account.balance}</TableCell>
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
    </div>
  );
}
