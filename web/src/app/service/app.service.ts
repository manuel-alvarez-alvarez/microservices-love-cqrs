import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {environment} from "../../environments/environment";
import "rxjs/add/operator/map";
import "rxjs/add/operator/timeout";
import "rxjs/add/operator/toPromise";
import {
  Product,
  Purchase,
  Shipment,
  Inventory,
  ProductWithInventory,
  InventoryAdded,
  InventoryRemoved,
  ItemsPurchased,
  ShipmentShipped,
  ShipmentDelivered,
  ProductAdded
} from "../domain/domain";
import {MessageService} from "./message.service";

@Injectable()
export class ApiService {

  constructor(private http: Http, private messageService: MessageService) {
  }

  getProductsWithInventory(): Observable<ProductWithInventory[]> {
    return this.getProducts().mergeMap(products =>
      this.getInventory().map(inventories => {
        let inventoryMap = inventories.reduce((result, item) => {
          result[item.product] = item;
          return result;
        }, {});
        return products.map(product => new ProductWithInventory(product, inventoryMap[product.id]));
      }));
  }

  getInventory(): Observable<Inventory[]> {
    return this.http
      .get(environment.api.url + "/inventory")
      .map(response => response.json() as Inventory[])
  }

  addToInventory(item: ProductWithInventory): Promise<InventoryAdded> {
    return this.http
      .post(environment.api.url + "/inventory/add", {
        id: item.inventory.id,
        product: item.product.id,
        amount: item.amount
      })
      .toPromise()
      .then(response => response.json() as InventoryAdded)
      .catch((error) => this.handleError(error))
  }

  removeFromInventory(item: ProductWithInventory): Promise<InventoryRemoved> {
    return this.http
      .post(environment.api.url + "/inventory/remove", {
        id: item.inventory.id,
        product: item.product.id,
        amount: item.amount
      })
      .toPromise()
      .then(response => response.json() as InventoryRemoved)
      .catch((error) => this.handleError(error))
  }

  getProducts(): Observable<Product[]> {
    return this.http
      .get(environment.api.url + "/catalog")
      .map(response => response.json() as Product[])
  }

  addProduct(product: Product): Promise<ProductAdded> {
    return this.http
      .post(environment.api.url + "/catalog", product)
      .toPromise()
      .then(response => response.json() as ProductAdded)
      .catch((error) => this.handleError(error))
  }

  getPurchases(): Observable<Purchase[]> {
    return this.http
      .get(environment.api.url + "/sales")
      .map(response => response.json() as Purchase[])
  }

  doPurchase(purchase: Purchase): Promise<ItemsPurchased> {
    return this.http
      .post(environment.api.url + "/sales", purchase)
      .toPromise()
      .then(response => response.json() as ItemsPurchased)
      .catch((error) => this.handleError(error))
  }

  getShipments(): Observable<Shipment[]> {
    return this.http
      .get(environment.api.url + "/shipping")
      .map(response => response.json() as Shipment[])
  }

  doShip(shipment: Shipment): Promise<ShipmentShipped> {
    return this.http
      .post(environment.api.url + "/shipping/ship", shipment)
      .toPromise()
      .then(response => response.json() as ShipmentShipped)
      .catch((error) => this.handleError(error))
  }

  doDeliver(shipment: Shipment): Promise<ShipmentDelivered> {
    return this.http
      .post(environment.api.url + "/shipping/deliver", shipment)
      .toPromise()
      .then(response => response.json() as ShipmentDelivered)
      .catch((error) => this.handleError(error))
  }

  private handleError(error: any): any {
    let message = error.text ? error.text() : error.message;
    this.messageService.showMessage(message || 'Oops! something went wrong');
    throw error;
  }
}
