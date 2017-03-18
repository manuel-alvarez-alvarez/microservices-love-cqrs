import {Component, OnInit} from "@angular/core";
import {ApiService} from "../../service/app.service";
import {Observable} from "rxjs";
import {MdDialog, MdDialogRef} from "@angular/material";
import * as bigjs from "big.js";
import {Purchase, PurchaseItem, ProductWithInventory} from "../../domain/domain";
import "rxjs/add/operator/mergeMap";
import {MessageService} from "../../service/message.service";

@Component({
  selector: 'shop',
  templateUrl: 'shop.html',
  styleUrls: ['shop.scss']
})
export class Shop implements OnInit {

  private products: Observable<ProductWithInventory[]>;
  private purchase: Purchase = new Purchase();

  constructor(private api: ApiService,
              private messageService: MessageService,
              private dialog: MdDialog) {
  }

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts(): void {
    this.products = this.api.getProductsWithInventory();
  }

  addToCart(productWithInventory: ProductWithInventory): void {
    if (productWithInventory.inventory.available > 0) {
      let product = productWithInventory.product;
      let inventory = productWithInventory.inventory;
      let products = this.purchase.items.filter((value) => value.product === product.id);
      let purchase;
      if (products.length === 0) {
        purchase = new PurchaseItem();
        this.purchase.items.push(purchase);
      } else {
        purchase = products[0];
      }
      purchase.product = product.id;
      purchase.item = product;
      purchase.amount += 1;
      purchase.price = bigjs(purchase.item.price).times(bigjs(purchase.amount));
      this.purchase.price = this.purchase.items.reduce((previous, current) => previous.plus(current.price), Big(0)).toString();
      inventory.available -= 1;
    }
  }

  getCartSize(): number {
    return this.purchase.items.reduce((previous, current) => previous + current.amount, 0);
  }

  showCart(): void {
    let dialog = this.dialog.open(PurchaseDialog);
    dialog.componentInstance.update(this.purchase);
    dialog.afterClosed().subscribe(result => {
      if (result === "PURCHASE") {
        this.doPurchase();
      }
    });
  }

  showHistory(): void {
    this.dialog.open(HistoryDialog);
  }

  doPurchase(): void {
    this.api.doPurchase(this.purchase)
      .then(result => {
        this.messageService.showMessage('Well done! you have just spent ' + result.price + ' $');
        this.purchase.items.length = 0;
        this.purchase.price = "0";
      });
  }
}

@Component({
  selector: 'purchase-dialog',
  templateUrl: 'purchase.html',
})
export class PurchaseDialog {

  private purchase: Purchase;
  private total: number;

  constructor(private dialogRef: MdDialogRef<PurchaseDialog>) {

  }

  update(thePurchase: Purchase): void {
    this.purchase = thePurchase;
    this.total = this.purchase.items.reduce((previous, current) => previous + current.amount, 0);
  }
}

@Component({
  selector: 'history-dialog',
  templateUrl: 'history.html',
})
export class HistoryDialog implements OnInit {

  private purchases: Observable<Purchase[]>;

  constructor(private dialogRef: MdDialogRef<HistoryDialog>, private api: ApiService) {

  }

  ngOnInit(): void {
    this.getPurchases();
  }

  getPurchases(): void {
    this.purchases = this.api.getPurchases();
  }

  getItems(purchase: Purchase): number {
    return purchase.items.map(it => it.amount).reduce((a, b) => a + b, 0);
  }
}



