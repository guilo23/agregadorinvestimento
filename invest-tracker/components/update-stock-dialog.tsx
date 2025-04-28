"use client"

import type React from "react"

import { useState, useEffect } from "react"
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogFooter } from "@/components/ui/dialog"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"

interface Stock {
  symbol: string
  quantity: number
  purchasePrice: number
  currentPrice: number
}

interface UpdateStockDialogProps {
  open: boolean
  onOpenChange: (open: boolean) => void
  stock: Stock
  onSubmit: (data: { quantity: number; purchasePrice: number }) => void
}

export default function UpdateStockDialog({ open, onOpenChange, stock, onSubmit }: UpdateStockDialogProps) {
  const [formData, setFormData] = useState({
    quantity: 0,
    purchasePrice: 0,
  })

  useEffect(() => {
    if (stock) {
      setFormData({
        quantity: stock.quantity,
        purchasePrice: stock.purchasePrice,
      })
    }
  }, [stock])

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target
    setFormData((prev) => ({ ...prev, [name]: Number(value) }))
  }

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    onSubmit(formData)
  }

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="sm:max-w-[425px]">
        <DialogHeader>
          <DialogTitle>Update Stock: {stock.symbol}</DialogTitle>
        </DialogHeader>
        <form onSubmit={handleSubmit}>
          <div className="grid gap-4 py-4">
            <div className="grid gap-2">
              <Label htmlFor="quantity">Quantity</Label>
              <Input
                id="quantity"
                name="quantity"
                type="number"
                min="1"
                value={formData.quantity || ""}
                onChange={handleChange}
                required
              />
            </div>
            <div className="grid gap-2">
              <Label htmlFor="purchasePrice">Purchase Price ($)</Label>
              <Input
                id="purchasePrice"
                name="purchasePrice"
                type="number"
                step="0.01"
                min="0.01"
                value={formData.purchasePrice || ""}
                onChange={handleChange}
                required
              />
            </div>
          </div>
          <DialogFooter>
            <Button type="submit">Update Stock</Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  )
}
