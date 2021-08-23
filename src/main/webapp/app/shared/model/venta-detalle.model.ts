import { IVenta } from 'app/shared/model/venta.model';
import { IProducto } from 'app/shared/model/producto.model';

export interface IVentaDetalle {
  id?: number;
  talla?: string | null;
  color?: string | null;
  urlImagen?: string | null;
  precio?: number | null;
  cantidad?: number;
  subTotal?: number | null;
  venta?: IVenta | null;
  producto?: IProducto | null;
}

export const defaultValue: Readonly<IVentaDetalle> = {};
