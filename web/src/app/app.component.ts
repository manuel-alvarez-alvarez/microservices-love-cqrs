import {Component, ViewEncapsulation} from "@angular/core";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class AppComponent {
  navItems = [
    {name: 'Home', icon: 'home', route: ''},
    {name: 'Shop', icon: 'shop', route: 'shop'},
    {name: 'Shipments', icon: 'local_shipping', route: 'shipments'},
    {name: 'Store', icon: 'store', route: 'store'},
    {name: 'Catalog', icon: 'view_carousel', route: 'catalog'},
  ];

}
