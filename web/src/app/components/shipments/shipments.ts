import {Component, OnInit} from "@angular/core";
import {ApiService} from "../../service/app.service";
import {MdSnackBar} from "@angular/material";
import {Observable} from "rxjs";
import {Shipment} from "../../domain/domain";
import {MessageService} from "../../service/message.service";

@Component({
  selector: 'shipments',
  templateUrl: 'shipments.html',
  styleUrls: ['shipments.scss']
})
export class Shipments implements OnInit {

  private shipments: Observable<Shipment[]>;

  constructor(private api: ApiService, private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.getShipments();
  }

  getShipments(): void {
    this.shipments = this.api.getShipments();
  }

  doShip(shipment: Shipment): void {
    this.api.doShip(shipment)
      .then(result => {
        this.messageService.showMessage('The shipment is now on its way, do not lose it!!!');
        this.getShipments();
      });
  }

  doDeliver(shipment: Shipment): void {
    this.api.doDeliver(shipment)
      .then(result => {
        this.messageService.showMessage('The shipment has been delivered, thanks!!!');
        this.getShipments();
      });
  }
}



