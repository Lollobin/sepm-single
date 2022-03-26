import {Component, OnInit} from '@angular/core';
import {HorseService} from "../../service/horse.service";
import {Location} from "@angular/common";
import {FormBuilder} from "@angular/forms";
import {HorseParents} from "../../dto/horseParents";
import {MessageService} from "../../service/message.service";

@Component({
  selector: 'app-horse-search',
  templateUrl: './horse-search.component.html',
  styleUrls: ['./horse-search.component.scss']
})

export class HorseSearchComponent implements OnInit {
  results: HorseParents[];

  horseForm = this.formBuilder.group({
    name: null,
    description: null,
    dateOfBirth: null,
    sex: null,
    owner: null
  })

  constructor(
    private horseService: HorseService,
    private location: Location,
    private formBuilder: FormBuilder,
    private messageService: MessageService
  ) {
  }

  ngOnInit(): void {
  }

  goBack(): void {
    this.location.back();
  }

  onSubmit(): void {
    const value = this.horseForm.value;
    console.log(value)
    this.horseService.searchHorse(value.name, value.description, value.dateOfBirth, value.sex, value.owner)
      .subscribe({
        next: data => {
          this.results = data;
        },
        error: error => {
          console.error('Error fetching horses', error.message);
          this.messageService.error('Error fetching horses')
        },
        complete: () =>{
          console.log('received horses');
          this.messageService.success('Successfully received horses');
        }
      })

  }
}
