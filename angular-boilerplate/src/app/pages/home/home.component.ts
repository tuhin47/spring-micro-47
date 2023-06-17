// Angular modules
import {Component, OnInit} from '@angular/core';
import {ProductService} from "@services/product.service";
import {UntilDestroy, untilDestroyed} from "@ngneat/until-destroy";
import {saveAs} from "file-saver";
import {Utils} from "@helpers/utils";
import {ToastManager} from "@blocks/toast/toast.manager";
import {HttpErrorResponse} from "@angular/common/http";

@UntilDestroy()
@Component({
  selector    : 'app-home',
  templateUrl : './home.component.html',
  styleUrls   : ['./home.component.scss']
})
export class HomeComponent implements OnInit
{
  public isLoading : boolean = true;


  constructor
  (
    private productService: ProductService,
    private toastManager: ToastManager
  ) {

  }

  public ngOnInit() : void
  {
    setTimeout(_ =>
    {
      this.isLoading = false;
    }, 2000);
  }

  exportExcel() {
    this.productService.getExcelData()
      .pipe(untilDestroyed(this))
      .subscribe(
        {
          next : (response: any) => {
            const contentDispositionHeader = response.headers.get('Content-Disposition');
            const fileName = Utils.getFileNameFromContentDisposition(contentDispositionHeader);
            saveAs(response.body, fileName);
          },
          error : (error : HttpErrorResponse) => {
            this.toastManager.quickShow(error.statusText);
            console.error('Error:', error);
          }
        }
      );
  }
}
