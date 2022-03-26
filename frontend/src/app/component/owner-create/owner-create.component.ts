import {Component, OnInit} from '@angular/core';
import {Location} from "@angular/common";
import {OwnerService} from "../../service/owner.service";
import {FormBuilder, Validators} from "@angular/forms";
import {Owner} from "../../dto/owner";
import {MessageService} from "../../service/message.service";

@Component({
  selector: 'app-owner-create',
  templateUrl: './owner-create.component.html',
  styleUrls: ['./owner-create.component.scss']
})
export class OwnerCreateComponent implements OnInit {

  ownerForm = this.formBuilder.group({
    firstName: '',
    lastName: '',
    email: ''
  })

  constructor(
    private location: Location,
    private ownerService: OwnerService,
    private formBuilder: FormBuilder,
    private messageService: MessageService
  ) {
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    const value = this.ownerForm.value;

    let owner: Owner = {
      firstName: value.firstName,
      lastName: value.lastName,
      email: value.email
    }

    this.ownerService.create(owner)
      .subscribe({
        next: (horse) => console.log(owner),
        error: (e) => this.messageService.error(e.error.message),
        complete: () => {
          this.messageService.success("Successfully created owner");
          this.ownerForm.reset();
        }
      })
  }

  goBack(): void {
    this.location.back();
  }
}
