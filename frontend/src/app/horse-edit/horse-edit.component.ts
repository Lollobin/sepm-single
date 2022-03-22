import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-horse-edit',
  templateUrl: './horse-edit.component.html',
  styleUrls: ['./horse-edit.component.scss']
})
export class HorseEditComponent implements OnInit {

  constructor(
    private activatedRoute: ActivatedRoute
  ) {
  }


  ngOnInit(): void {
  }

}
