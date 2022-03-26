import { Component, OnInit } from '@angular/core';
import {OwnerService} from "../../service/owner.service";
import {Owner} from "../../dto/owner";
import {MessageService} from "../../service/message.service";

@Component({
  selector: 'app-owner',
  templateUrl: './owner.component.html',
  styleUrls: ['./owner.component.scss']
})
export class OwnerComponent implements OnInit {
  owners: Owner[];

  constructor(
    private service: OwnerService,
    private messageService: MessageService
  ) { }

  ngOnInit(): void {
    this.reloadOwners();
  }

  reloadOwners(){
    this.service.getAll().subscribe({
      next: data =>{
        this.messageService.success('Successfully received owners');
        this.owners=data;
      },
      error: error=>{
        console.error('Error fetching owners', error.message);
        this.messageService.error('Error fetching owners')
    }
    })
  }

}
