import { ActivatedRouteSnapshot, CanActivateFn, RouterStateSnapshot } from "@angular/router";
import { StorageHelper } from "./storage.helper";
import { EventBusService } from "../event/event-bus.service";
import { inject } from "@angular/core";
import { EventData } from "../event/event.class";

export const authGuard: CanActivateFn = (
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ) => {
    let roles = route.data["roles"] as string[];
    const status = (roles||[]).length == 0 || (StorageHelper.getToken() != null && StorageHelper.hasAnyRole(roles));
    if(!status){
      inject(EventBusService).emit(new EventData('denied', null))
    }
    return status;
  };