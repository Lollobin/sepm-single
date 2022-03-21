import {Component, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import {FormBuilder, Validators} from '@angular/forms';

import {HorseService} from "../service/horse.service";

@Component({
  selector: 'app-horse-form',
  templateUrl: './horse-form.component.html',
  styleUrls: ['./horse-form.component.scss']
})
export class HorseFormComponent implements OnInit {

  horseForm = this.formBuilder.group({
    name: ['', Validators.required],
    description: '',
    dateOfBirth: {year: 2000, month: 1, day: 1},
    sex: 'male'
  })

  constructor(
    private location: Location,
    private horseService: HorseService,
    private formBuilder: FormBuilder
  ) {
  }

  ngOnInit(): void {
  }

  goBack(): void {
    this.location.back();
  }

  onSubmit(): void{
    this.horseService.submit({
      ...this.horseForm.value,
      dateOfBirth: new Date(this.horseForm.value.dateOfBirth)
    }).subscribe({next: (horse) => console.log(horse)})

    //console.info('The horse has been submitted', this.horseForm.value);
    //this.horseForm.reset();
  }

  get name() { return this.horseForm.get('name'); }
}
