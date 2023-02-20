// Enums
import { EnvName } from '@enums/environment.enum';

// Packages
import packageInfo from '../../package.json';

const API_URL = ($ENV.API_BASE || "api/");
const OAUTH2_URL = "oauth2/authorization/";

const REDIRECT_URL = $ENV.OAUTH_REDIRECT_URL ? "?redirect_uri=" + $ENV.OAUTH_REDIRECT_URL : "";

const GOOGLE_AUTH_URL   = OAUTH2_URL + "google" + REDIRECT_URL;
const FACEBOOK_AUTH_URL = OAUTH2_URL + "facebook" + REDIRECT_URL;
const GITHUB_AUTH_URL   = OAUTH2_URL + "github" + REDIRECT_URL;
const LINKEDIN_AUTH_URL = OAUTH2_URL + "linkedin" + REDIRECT_URL;

export const environment = {
  production      : true,
  version         : packageInfo.version,
  appName         : 'Boilerplate tuhin47',
  envName         : $ENV.ENVIRONMENT || EnvName.PROD,
  defaultLanguage : 'en',
  apiBaseUrl      : '/',
  API_URL,
  GOOGLE_AUTH_URL,
  FACEBOOK_AUTH_URL,
  GITHUB_AUTH_URL,
  LINKEDIN_AUTH_URL
};
