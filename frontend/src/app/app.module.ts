import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './component/header/header.component';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HorseComponent} from './component/horse/horse.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {HorseCreateComponent} from './component/horse-create/horse-create.component';
import {MessageComponent} from './component/message/message.component';
import { HorseEditComponent } from './component/horse-edit/horse-edit.component';
import { HorseDeleteComponent } from './component/horse-delete/horse-delete.component';
import { HorseDetailsComponent } from './component/horse-details/horse-details.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HorseComponent,
    HorseCreateComponent,
    MessageComponent,
    HorseEditComponent,
    HorseDeleteComponent,
    HorseDetailsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
