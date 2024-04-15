// Angular modules
import { Injectable } from '@angular/core';

// Enums
import { StorageKey } from '@enums/storage-key.enum';

// Internal modules
import { environment } from '@env/environment';

// Models
import { AuthResponse } from '@models/auth-response.model';
import { UserInfo }     from '@models/user.model';

@Injectable()
export class StorageHelper {
  private static storagePrefix: string = environment.appName + '_' + environment.version + '_';

  // ----------------------------------------------------------------------------------------------
  // SECTION Methods ------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------

  // NOTE Token

  public static setAuthResponse(authResponse: AuthResponse): void {
    if (authResponse.accessToken) {
      this.setToken(authResponse.accessToken);
    }
    StorageHelper.setItem(StorageKey.AUTH_RESPONSE, authResponse);
  }

  public static setToken(token: string): void {
    StorageHelper.setItem(StorageKey.TOKEN, token);
  }

  public static removeToken(): void {
    StorageHelper.removeItem(StorageKey.AUTH_RESPONSE);
    StorageHelper.removeItem(StorageKey.TOKEN);
  }

  public static getAuthResponse(): AuthResponse {
    return StorageHelper.getItem(StorageKey.AUTH_RESPONSE);
  }

  public static getUserID(): string {
    return this.getAuthResponse().user.id;
  }

  public static getUser(): UserInfo {
    return this.getAuthResponse().user;
  }

  public static hasAnyRole(roles: string[]): boolean {

    if (roles.length == 0) {
      return true;
    }
    const response = this.getAuthResponse();
    if (!response?.user) {
      return false;
    }
    const user: UserInfo = response.user;
    return user.roles.length != 0 && user.roles.some((role: string) => roles.includes(role));
  }

  public static getToken(): string {
    return StorageHelper.getItem(StorageKey.TOKEN);
  }

  // !SECTION Methods

  // ----------------------------------------------------------------------------------------------
  // SECTION LocalStorage -------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------

  public static setItem(key: string, value: any, prefix: boolean = true): void {
    const itemKey = this.prefixer(key, prefix);
    localStorage.setItem(itemKey, JSON.stringify(value));
  }

  public static getItem(key: string, prefix: boolean = true): any {
    const itemKey = this.prefixer(key, prefix);
    const res = localStorage.getItem(itemKey);
    if (res !== 'undefined')
      return JSON.parse(res as any);
    console.error('StorageHelper : getItem -> undefined key');
    return null;
  }

  public static removeItem(key: string, prefix: boolean = true): void {
    const itemKey = this.prefixer(key, prefix);
    localStorage.removeItem(itemKey);
  }

  public static getKeys(all: boolean = false): string[] {
    const keys: string[] = [];
    // NOTE Keys
    for (const key in localStorage)
      keys.push(key);
    if (all)
      return keys;
    // NOTE Prefixed keys
    return keys.filter((item) => item.startsWith(this.storagePrefix));
  }

  public static clearItems(all: boolean = false): void {
    // NOTE Keys
    if (all) {
      localStorage.clear();
      return;
    }
    // NOTE Prefixed keys
    const prefixedKeys = this.getKeys();
    for (const prefixedKey of prefixedKeys)
      this.removeItem(prefixedKey, false);
  }

  public static clearItemsWithoutCurrentPrefix(): void {
    const allKeys = this.getKeys(true);
    for (const key of allKeys)
      if (!key.startsWith(this.storagePrefix))
        this.removeItem(key, false);
  }

  // !SECTION LocalStorage

  // NOTE Private

  private static prefixer(key: string, autoPrefix: boolean): string {
    let itemKey = key;
    if (autoPrefix)
      itemKey = this.storagePrefix + key;
    return itemKey;
  }

}
