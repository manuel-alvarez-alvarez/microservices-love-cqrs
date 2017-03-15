import {Component, OnInit} from "@angular/core";
import {ApiService} from "../../service/app.service";
import {Observable} from "rxjs";
import {ProductWithInventory} from "../../domain/domain";
import {MessageService} from "../../service/message.service";

@Component({
  selector: 'store',
  templateUrl: 'store.html',
  styleUrls: ['store.scss']
})
export class Store implements OnInit {

  private inventory: Observable<ProductWithInventory[]>;

  constructor(private api: ApiService, private messageService: MessageService,) {
  }

  ngOnInit(): void {
    this.getInventory();
  }

  getInventory(): void {
    this.inventory = this.api.getProductsWithInventory();
  }

  add(item: ProductWithInventory): void {
    this.api.addToInventory(item)
      .then(result => {
        item.inventory.available += result.amount;
        item.amount = 0;
        this.messageService.showMessage('Nice the inventory is growing!');
      });
  }

  remove(item: ProductWithInventory): void {
    this.api.removeFromInventory(item)
      .then(result => {
        item.inventory.available -= result.amount;
        item.amount = 0;
        this.messageService.showMessage('You are making the inventory smaller :(');
      });
  }
}



