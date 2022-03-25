import { Component, OnInit } from '@angular/core';
import {OwnerService} from "../../service/owner.service";
import {Owner} from "../../dto/owner";

@Component({
  selector: 'app-owner',
  templateUrl: './owner.component.html',
  styleUrls: ['./owner.component.scss']
})
export class OwnerComponent implements OnInit {
  owners: Owner[];

  constructor(
    private service: OwnerService
  ) { }

  ngOnInit(): void {
    this.reloadOwners();
  }

  reloadOwners(){
    this.service.getAll().subscribe({
      next: data =>{
        this.service.log('received owners');
        this.owners=data;
      },
      error: error=>{
        this.service.log('Error fetching owners')
    }
    })
  }

}
