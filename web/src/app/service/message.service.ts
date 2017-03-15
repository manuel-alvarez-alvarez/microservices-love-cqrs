import {Injectable} from "@angular/core";
import {MdSnackBar} from "@angular/material";

@Injectable()
export class MessageService {

  constructor(private snackBar: MdSnackBar) {

  }

  showMessage(message: string): void {
    this.snackBar.open(message, 'Info', {duration: 5000});
  }
}
