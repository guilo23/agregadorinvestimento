// services/stock.ts
const API_BASE_URL = 'http://localhost:8081/v1'; // Defina a URL base da sua API

export interface Stock {
  stockId: string;
  description: string;
  quantity: number;
  price: number;
  purchasePrice: number;
  // Adicione quaisquer outras propriedades que sua API retorne para um stock
}


export interface StockData {
  stockId: string;
  purchasePrice: number;
  quantity: number;
}

interface UpdateStockData {
  quantity: number;
}

export const getAccountStocks = async (accountId: string): Promise<Stock[]> => {
  try {
    const response = await fetch(`${API_BASE_URL}/accounts/${accountId}/stocksList`);
    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || `Failed to fetch stocks (status ${response.status})`);
    }
    const data = await response.json();
    return data;
  } catch (error: any) {
    console.error("Error fetching stocks:", error);
    throw error;
  }
};

export const addStock = async (accountId: string , stockData: StockData): Promise<StockData> => {
  try {
    const response = await fetch(`${API_BASE_URL}/accounts/${accountId}/stocks`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(stockData),
    });
    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || `Failed to add stock (status ${response.status})`);
    }
    const data = await response.json();
    return data;
  } catch (error: any) {
    console.error("Error adding stock:", error);
    throw error;
  }
};

export const updateStock = async (accountId: string, symbol: string, stockData: UpdateStockData): Promise<Stock> => {
  try {
    const response = await fetch(`${API_BASE_URL}/accounts/investments/${accountId}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ symbol, ...stockData }),
    });
    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || `Failed to update stock (status ${response.status})`);
    }
    const data = await response.json();
    return data;
  } catch (error: any) {
    console.error("Error updating stock:", error);
    throw error;
  }
};

export const deleteStock = async (accountId: string, symbol: string): Promise<void> => {
  try {
    const response = await fetch(`${API_BASE_URL}/accounts/investments/${accountId}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ symbol }),
    });
    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || `Failed to delete stock (status ${response.status})`);
    }
    // No need to parse JSON for a successful DELETE
  } catch (error: any) {
    console.error("Error deleting stock:", error);
    throw error;
  }
};