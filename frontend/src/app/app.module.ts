import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './component/header/header.component';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HorseComponent} from './component/horse/horse.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {HorseCreateComponent} from './horse-create/horse-create.component';
import {MessageComponent} from './component/message/message.component';
import { HorseEditComponent } from './horse-edit/horse-edit.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HorseComponent,
    HorseCreateComponent,
    MessageComponent,
    HorseEditComponent,
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
