import {Component, OnInit} from '@angular/core';
import {HorseService} from "../../service/horse.service";
import {Location} from "@angular/common";
import {FormBuilder, Validators} from "@angular/forms";
import {Horse} from "../../dto/horse";

@Component({
  selector: 'app-horse-search',
  templateUrl: './horse-search.component.html',
  styleUrls: ['./horse-search.component.scss']
})
export class HorseSearchComponent implements OnInit {
  results: Horse[];

  horseForm = this.formBuilder.group({
    name: null,
    description: null,
    dateOfBirth: null,
    sex: 'unspecified',
    father: null,
    mother: null
  })

  nameFormatter = (result: Horse) => result.name;
  descriptionFormatter = (result: Horse) => result.description;

  constructor(
    private horseService: HorseService,
    private location: Location,
    private formBuilder: FormBuilder
  ) {
  }

  ngOnInit(): void {
  }

  goBack(): void {
    this.location.back();
  }

  onSubmit(): void {
    const value = this.horseForm.value;
    let searchDto: Horse = {
      name: value.name == '' ? null : value.name,
      description: value.description == '' ? null : value.description,
      dateOfBirth: value.dateOfBirth,
      sex: value.sex == 'unspecified' ? null : value.sex,
      fatherId: value.father == null ? null : value.father.id,
      motherId: value.mother == null ? null : value.mother.id
    }

    this.horseService.searchHorse(searchDto).subscribe({
      next: data => {
        this.results = data;
      }
    })

  }
}
