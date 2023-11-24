import {ApplicationConfig, importProvidersFrom} from '@angular/core';
import {HttpClientModule} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";

export const appConfig: ApplicationConfig = {
  providers: [importProvidersFrom(HttpClientModule), CookieService]
};
