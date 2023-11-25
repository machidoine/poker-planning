import {ApplicationConfig, importProvidersFrom} from '@angular/core';
import {HttpClientModule} from "@angular/common/http";
import {provideRouter} from "@angular/router";
import {routes} from "./app-routing.module";
import {provideAnimations} from '@angular/platform-browser/animations';

export const appConfig: ApplicationConfig = {
    providers: [importProvidersFrom(HttpClientModule), provideRouter(routes), provideAnimations()]
};
