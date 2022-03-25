import {Component, OnInit} from '@angular/core';
import {HorseService} from "../../service/horse.service";
import {Location} from "@angular/common";
import {FormBuilder} from "@angular/forms";
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
    sex: null,
    father: null,
    mother: null
  })

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
    this.horseService.searchHorse(value.name, value.description, value.dateOfBirth, value.sex)
      .subscribe({
        next: data => {
          this.results = data;
        }
      })

  }
}
