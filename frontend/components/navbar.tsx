"use client"

import Link from "next/link"
import { usePathname } from "next/navigation"
import { Button } from "@/components/ui/button"
import { TrendingUp } from "lucide-react"

export default function Navbar() {
  const pathname = usePathname()

  const navItems = [
    { href: "/", label: "Dashboard" },
    { href: "/users", label: "Users" },
    { href: "/accounts", label: "Accounts" },
    { href: "/stocks", label: "Stocks" },
  ]

  return (
    <header className="border-b">
      <div className="container mx-auto flex h-16 items-center px-4">
        <Link href="/" className="flex items-center gap-2 font-semibold">
          <TrendingUp className="h-5 w-5" />
          <span>Investment Manager</span>
        </Link>
        <nav className="ml-auto flex gap-4">
          {navItems.map((item) => (
            <Button key={item.href} asChild variant={pathname === item.href ? "default" : "ghost"}>
              <Link href={item.href}>{item.label}</Link>
            </Button>
          ))}
        </nav>
      </div>
    </header>
  )
}
