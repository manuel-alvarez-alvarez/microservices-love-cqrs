import {BrowserModule} from "@angular/platform-browser";
import {NgModule, ApplicationRef, ErrorHandler} from "@angular/core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {RouterModule} from "@angular/router";
import {MaterialModule, OverlayContainer, FullscreenOverlayContainer, MdSelectionModule} from "@angular/material";
import {AppComponent} from "./app.component";
import {Home} from "./components/home";
import {Shop, PurchaseDialog, HistoryDialog} from "./components/shop/shop";
import {ApiService} from "./service/app.service";
import {AppErrorHandler} from "./components/error.handler";
import {FlexLayoutModule} from "@angular/flex-layout";
import {Shipments} from "./components/shipments/shipments";
import {Store} from "./components/inventory/store";
import {MessageService} from "./service/message.service";
import {Catalog, ProductDialog} from "./components/catalog/catalog";

@NgModule({
  declarations: [
    AppComponent,
    Home,
    Shop,
    Shipments,
    Store,
    Catalog,
    PurchaseDialog,
    HistoryDialog,
    ProductDialog
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    ReactiveFormsModule,
    RouterModule.forRoot([
      {path: '', component: Home},
      {path: 'shop', component: Shop},
      {path: 'shipments', component: Shipments},
      {path: 'store', component: Store},
      {path: 'catalog', component: Catalog}
    ]),
    MaterialModule,
    MdSelectionModule,
    FlexLayoutModule
  ],
  providers: [
    ApiService,
    MessageService,
    {provide: OverlayContainer, useClass: FullscreenOverlayContainer},
    {provide: ErrorHandler, useClass: AppErrorHandler}
  ],
  entryComponents: [PurchaseDialog, HistoryDialog, ProductDialog],
  bootstrap: [AppComponent]
})
export class AppModule {

  constructor(private _appRef: ApplicationRef) {
  }

  ngDoBootstrap() {
    this._appRef.bootstrap(AppComponent);
  }

}
