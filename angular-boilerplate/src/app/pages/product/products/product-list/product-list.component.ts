import { Component } from '@angular/core';
import { SearchOperationEnum } from '@enums/search-operation.enum';
import { ProductModel } from '@models/product.model';
import { UntilDestroy, untilDestroyed } from '@ngneat/until-destroy';
import { ProductService } from '@services/product.service';
import * as FileSaver from 'file-saver';
import html2canvas from 'html2canvas';
import jspdf from 'jspdf';


interface ExportColumn {
  title: string;
  dataKey: string;
}

@UntilDestroy()
@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
})
export class ProductListComponent {
  products!: ProductModel[];
  first = 0;
  rows = 10;
  columns: any[] = [
    {
      header: 'Product Id',
      field: 'productId',
      width: '',
      noSort: false,
      noFilter: false,
    },
    {
      header: 'Product Name',
      field: 'productName',
      width: '',
      noSort: true,
      noFilter: true,
    },
    {
      header: 'Quantity',
      field: 'quantity',
      width: '',
      noSort: false,
      noFilter: false,
    },
    {
      header: 'Price',
      field: 'price',
      width: '',
      noSort: false,
      noFilter: false,
    },
  ];
  title = 'List of Products';
  exportColumns!: ExportColumn[];

  constructor(private productService: ProductService) {
  }

  ngOnInit() {
    this.getProducts();
    this.exportColumns = this.columns.map((col) => ({ title: col.header, dataKey: col.field }));
  }

  next() {
    this.first = this.first + this.rows;
  }

  prev() {
    this.first = this.first - this.rows;
  }

  reset() {
    this.first = 0;
  }

  isLastPage(): boolean {
    return this.products
      ? this.first === this.products.length - this.rows
      : true;
  }

  isFirstPage(): boolean {
    return this.products ? this.first === 0 : true;
  }

  exportPdf() {
    import('jspdf').then((jsPDF) => {
      import('jspdf-autotable').then((x) => {
        const doc = new jsPDF.default('p', 'px', 'a4');
        (doc as any).autoTable(this.exportColumns, this.products);
        doc.save('products.pdf');
      });
    });
  }

  captureScreen(dom:any)
  {
    const data = dom.el?.nativeElement?.querySelector("div.p-datatable-wrapper");

    if(data){
      html2canvas(data).then(canvas => {
        // Few necessary setting options
        const imgWidth = 208;
        const imgHeight = canvas.height * imgWidth / canvas.width;
        // const pageHeight = 295;
        // const heightLeft = imgHeight;

        const contentDataURL = canvas.toDataURL('image/png')
        const pdf = new jspdf('p', 'mm', 'a4'); // A4 size page of PDF
        const position = 0;
        pdf.addImage(contentDataURL, 'PNG', 0, position, imgWidth, imgHeight)
        pdf.save('MYPdf.pdf'); // Generated PDF
      });
    }

  }

  exportExcel() {
    import('xlsx').then((xlsx) => {
      const worksheet = xlsx.utils.json_to_sheet(this.products);
      const workbook = { Sheets: { data: worksheet }, SheetNames: ['data'] };
      const excelBuffer: any = xlsx.write(workbook, { bookType: 'xlsx', type: 'array' });
      this.saveAsExcelFile(excelBuffer, 'products');
    });
  }

  saveAsExcelFile(buffer: any, fileName: string): void {
    let EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
    let EXCEL_EXTENSION = '.xlsx';
    const data: Blob = new Blob([buffer], {
      type: EXCEL_TYPE
    });
    FileSaver.saveAs(data, fileName + '_export_' + new Date().getTime() + EXCEL_EXTENSION);
  }

  private getProducts() {
    let param = [
      {
        key: 'productName',
        operation: SearchOperationEnum.MATCH,
        value: 'a',
        groupId: 'G1',
      },
      /* {
      key: 'id',
      operation: SearchOperationEnum.MATCH,
      value: '2c97808a8976',
      // groupId: 'G1'
    },  */ {
        key: 'createdAt',
        operation: SearchOperationEnum.GREATER_THAN,
        value: new Date(2022, 10, 10),
        // groupId: 'G1'
      },
      {
        key: 'price',
        operation: SearchOperationEnum.GREATER_THAN,
        value: 5.5,
        className: 'java.lang.Double',
        groupId: 'G2',
      },
      {
        key: 'price',
        operation: SearchOperationEnum.LESS_THAN,
        value: 5000,
        className: 'java.lang.Long',
        groupId: 'G2',
      },
      /*   {
          key: 'company.name',
          operation: SearchOperationEnum.MATCH,
          value: "abc",
        }, */
    ];

    this.productService
      .getAllData(param) // TODO params should be generated
      .pipe(untilDestroyed(this))
      .subscribe({
        next: (data: any) => {
          this.products = data.content;
        },
        error: (err) => console.error(err),
      });
  }
}
