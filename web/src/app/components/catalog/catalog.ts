import {Component, OnInit} from "@angular/core";
import {ApiService} from "../../service/app.service";
import {MdDialog, MdDialogRef} from "@angular/material";
import {Observable} from "rxjs";
import {Product} from "../../domain/domain";
import {MessageService} from "../../service/message.service";

@Component({
  selector: 'catalog',
  templateUrl: 'catalog.html',
  styleUrls: ['catalog.scss']
})
export class Catalog implements OnInit {

  private products: Observable<Product[]>;

  constructor(private api: ApiService,
              private messageService: MessageService,
              private dialog: MdDialog) {
  }

  ngOnInit(): void {
    this.getProduts();
  }

  getProduts(): void {
    this.products = this.api.getProducts();
  }

  showAddProductDialog(): void {
    let dialog = this.dialog.open(ProductDialog);
    dialog.afterClosed().subscribe(result => {
      if (result !== "CLOSE") {
        this.addProduct(result as Product, dialog.componentInstance as ProductDialog);
      }
    });
  }

  addProduct(product: Product, productDialog: ProductDialog): void {
    this.api.addProduct(product)
      .then(result => {
        this.messageService.showMessage('Nice!, another product in our beautiful store');
        this.getProduts();
        productDialog.product.name = '';
        productDialog.product.price = '';
        productDialog.product.picture = '';

      });
  }
}

@Component({
  selector: 'product-dialog',
  templateUrl: 'product.html',
})
export class ProductDialog {

  public product: Product = new Product();

  constructor(private dialogRef: MdDialogRef<ProductDialog>) {

  }

}



