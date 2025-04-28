
"use client"

import { useEffect, useState } from "react"
import { useParams } from "next/navigation"
import { getAccountStocks, Stock, StockData,addStock} from "@/app/service/stock"
import { Table, TableBody, TableCaption, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import { useToast } from "@/hooks/use-toast"
import { Button } from "@/components/ui/button"
import { Plus } from "lucide-react"
import AddStockDialog from "@/components/add-stock-dialog"


const AccountStocksPage = () => {
  const { accountId } = useParams()
  console.log("Current accountId from params:", accountId)
  const [createDialogOpen, setCreateDialogOpen] = useState(false)
  const [stocks, setStocks] = useState<Stock[]>([])
  const [addDialogOpen, setAddDialogOpen] = useState(false)
  const [addstocks, setAddStocks] = useState<StockData[]>([])
  const [loading, setLoading] = useState(true)
  
  const { toast } = useToast()

  const fetchStocks = async () => {
    setLoading(true)
    if (!accountId) {
      console.error("accountId is undefined")
      toast({
        title: "Error",
        description: "Account ID is missing",
        variant: "destructive",
      })
      setLoading(false)
      return
    }

    try {
      const id = Array.isArray(accountId) ? accountId[0] : accountId
      const data = await getAccountStocks(id)
      setStocks(data)
    } catch (error: any) {
      toast({
        title: "Error",
        description: error.message || "Failed to fetch stocks",
        variant: "destructive",
      })
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    if (accountId) {
      fetchStocks()
    }
  }, [accountId])
  const handleAddStock = async (stockData: StockData) => {
    const id = Array.isArray(accountId) ? accountId[0] : accountId
    if (!id) {
      toast({
        title: "Error",
        description: "Account ID is missing",
        variant: "destructive",
      })
      return
    }
    try {
      await addStock(id, stockData);
      if (accountId) {
      fetchStocks()
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
        <h1 className="text-3xl font-bold">Add Stock</h1>
        <Button onClick={() => setAddDialogOpen(true)}>
          <Plus className="mr-2 h-4 w-4" />
          Add Stock
        </Button>
      </div>
      <h1 className="text-2xl font-bold mb-4">Account Stocks</h1>
      <Table>
        <TableCaption>A list of stocks in this account.</TableCaption>
        <TableHeader>
          <TableRow>
            <TableHead className="w-[100px]">Symbol</TableHead>
            <TableHead>Name</TableHead>
            <TableHead className="text-right">Quantity</TableHead>
            <TableHead>purchasePrice</TableHead>
            <TableHead>ActualPrice</TableHead>
            <TableHead className="text-right">Total</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {loading ? (
            <>
              {Array.from({ length: 5 }).map((_, i) => (
                <TableRow key={i}>
                  <TableCell>
                    
                  </TableCell>
                  <TableCell>
                    
                  </TableCell>
                  <TableCell>
                    
                  </TableCell>
                  <TableCell className="text-right">
                  
                  </TableCell>
                </TableRow>
              ))}
            </>
          ) : stocks.length > 0 ? (
            stocks.map((stock) => (
              <TableRow key={stock.stockId}>
                <TableCell className="font-medium">{stock.stockId}</TableCell>
                <TableCell>{stock.description}</TableCell>
                <TableCell className="text-right">{stock.quantity}</TableCell>
                <TableCell>{stock.purchasePrice}</TableCell>
                <TableCell>{stock.price}</TableCell>
                <TableCell>{(stock.price * stock.quantity).toFixed(2)}</TableCell>
                
              </TableRow>
            ))
          ) : (
            <TableRow>
              <TableCell colSpan={5} className="text-center">
                No stocks found.
              </TableCell>
            </TableRow>
          )}
        </TableBody>
      </Table>
       <AddStockDialog open={addDialogOpen} onOpenChange={setAddDialogOpen} onSubmit={handleAddStock} />
    </div>
  )
}

export default AccountStocksPage
