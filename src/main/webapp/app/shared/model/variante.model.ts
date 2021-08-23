import { IProducto } from 'app/shared/model/producto.model';
import { IImagen } from 'app/shared/model/imagen.model';

export interface IVariante {
  id?: number;
  sku?: string | null;
  talla?: string;
  color?: string;
  stock?: number;
  precio?: number | null;
  producto?: IProducto | null;
  imagen?: IImagen | null;
}

export const defaultValue: Readonly<IVariante> = {};
