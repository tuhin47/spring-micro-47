// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

// Enums
import { EnvName } from '@enums/environment.enum';

// Packages
import packageInfo from '../../package.json';

const API_BASE_URL               =  '';
const API_URL                    =  API_BASE_URL + "api/";
const OAUTH2_URL                 =  "oauth2/authorization/";
const REDIRECT_URL               =  "?redirect_uri=http://localhost:4200/auth/login";

const GOOGLE_AUTH_URL            =  OAUTH2_URL + "google" + REDIRECT_URL;
const FACEBOOK_AUTH_URL          =  OAUTH2_URL + "facebook" + REDIRECT_URL;
const GITHUB_AUTH_URL            =  OAUTH2_URL + "github" + REDIRECT_URL;
const LINKEDIN_AUTH_URL          =  OAUTH2_URL + "linkedin" + REDIRECT_URL;

export const environment = {
    production: false,
    version: packageInfo.version,
    appName: 'Boilerplate tuhin47',
    envName: EnvName.LOCAL,
    defaultLanguage: 'en',
    API_URL : API_URL,
    GOOGLE_AUTH_URL: GOOGLE_AUTH_URL,
    FACEBOOK_AUTH_URL: FACEBOOK_AUTH_URL,
    GITHUB_AUTH_URL: GITHUB_AUTH_URL,
    LINKEDIN_AUTH_URL: LINKEDIN_AUTH_URL,
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
