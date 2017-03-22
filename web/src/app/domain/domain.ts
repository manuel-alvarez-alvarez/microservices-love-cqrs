export class Product {
  id: string;
  name: string;
  picture: string;
  price: string;
}

export class Purchase {
  id: string;
  sold: Date;
  items: Array<PurchaseItem> = [];
  price: string;
  status: Status;
}

export class PurchaseItem {
  id: string;
  item: Product;
  amount: number = 0;
  price: string;
  product: string;
}

export class Shipment {
  id: string;
  purchase: string;
  timestamp: Date;
  status: Status;
}

export class Inventory {
  id: string;
  product: string;
  available: number;
}

export class ProductWithInventory {
  constructor(public product: Product, public inventory: Inventory, public amount: number = 0) {
  }
}

export class InventoryAdded {
  id: string;
  product: string;
  amount: number;
}

export class InventoryRemoved {
  id: string;
  product: string;
  amount: number;
}

export class ItemsPurchased {
  id: string;
  sold: Date;
  price: string;
  items: Item[];
}

export class Item {
  product: string;
  price: string;
  amount: number;
}

export class ShipmentShipped {
  id: string;
}

export class ShipmentDelivered {
  id: string;
}

export class ProductAdded {
  id: string;
  name: string;
  picture: string;
  price: string;
}

enum Status {
  NEW,
  SHIPPED,
  DELIVERED
}
