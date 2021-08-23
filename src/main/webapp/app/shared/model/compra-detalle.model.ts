import { ICompra } from 'app/shared/model/compra.model';
import { IProducto } from 'app/shared/model/producto.model';

export interface ICompraDetalle {
  id?: number;
  cantidad?: number;
  precio?: number | null;
  subTotal?: number | null;
  compra?: ICompra | null;
  producto?: IProducto | null;
}

export const defaultValue: Readonly<ICompraDetalle> = {};
