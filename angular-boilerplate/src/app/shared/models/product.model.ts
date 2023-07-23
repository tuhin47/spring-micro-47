export class ProductModel {
  constructor(private productId: string, private productName: string, private quantity: number, private price: number) {
    this.productId = productId;
    this.productName = productName;
    this.quantity = quantity;
    this.price = price;
  }
}
